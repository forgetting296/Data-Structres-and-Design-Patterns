package com.shusaku.study.patterns.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liuzi
 */
public class ProxyTest {

    /**
     * JDK代理的原理：
     *  1.获取真实角色对象的引用并获取其接口
     *  2.Proxy生成一个代理类，并实现接口的方法
     *  3.获取被代理对象的引用
     *  4.动态生成代理类的class字节码
     *  5.编译、加载
     */
    public void JDKProxyTest(){
        Car car = (Car) new Agent().getInstance(new QQCar());
        //此时的car是getInstance()返回的代理对象了  调用sale()的时候  会触发invoke()方法？  应该是这样吧
        car.sale();
        /**
         * 代理之前的对象类型：com.elensdata.proxy.QQCar
         * 代理之后对象类型：com.elensdata.proxy.$Proxy4
         * 代理人寻找供货商
         * 出售一辆QQ
         * 代理商出售QQ
         */
    }

    public void MyProxyTest() throws Exception {
        Car car = (Car) new MyAgent().getInstance(new QQCar());
        car.sale();
    }

    public void CGProxyTest() throws Exception {
        Car2 car = (Car2) new CGAgent().getInstance(new QQCar2().getClass());
        car.sale();
        //java.lang.NoClassDefFoundError: org/objectweb/asm/Type   难受啊
    }
}

class CGAgent implements MethodInterceptor {

    public Object getInstance(Class clazz)throws Exception{

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理开始了。。。");
        methodProxy.invokeSuper(0,objects);
        System.out.println("代理结束了。。。");
        return null;
    }
}

/**
 * 参考JDK的实现  自己模拟定义一个代理接口  代理类需要实现这个接口
 */
interface MyInvocationHandler{
    Object invock(Object proxy, Method method, Object[] args)throws Throwable;
}

/**
 * 代理类
 */
class MyAgent implements MyInvocationHandler{

    private Car car;

    @Override
    public Object invock(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我定义的代理商寻找供货商");
        method.invoke(this.car,args);
        System.out.println("我定义的代理人出售QQ");
        return null;
    }

    public Object getInstance(Car car)throws Exception{
        this.car = car;
        Class clazz = car.getClass();
        Object obj = MyProxy.newProxyInstance(new MyClassLoader(),clazz.getInterfaces(),this);
        return obj;
    }
}

/**
 * 生成代理对象的代码  proxy的具体原理在这里实现
 * 实现MyProxy中生成代理对象的方法newProxyInstance()
 *  1.定义动态代理类的源码
 *  2.保存源码文件到磁盘
 *  3.编译源码为.class文件
 *  4.加载.class字节码到内存
 *  5.返回代理对象
 */
class MyProxy {
    private static String ln = "\r\n";
    public static Object newProxyInstance(MyClassLoader loader,Class<?>[] interfaces,MyInvocationHandler h){
        File f = null;
        try {
            //第一步：生成源代码  拼接成的源代码
            String src = generateSrc(interfaces[0]);

            //保存生成的源代码
            String filePath = MyProxy.class.getResource("").getPath();
            f = new File(filePath + "/$Proxy0.java");
            FileWriter writer = new FileWriter(f);
            writer.write(src);
            writer.flush();
            writer.close();

            //第三步  编译生成的.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = standardFileManager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null,standardFileManager,null,null,null,iterable);
            ((JavaCompiler.CompilationTask)task).call();
            standardFileManager.close();

            //第四步  加载class字节码到内存(MyClassLoader类实现)
            Class proxyClass = loader.findClass("$Proxy0");

            //第五步  返回代理对象
            Constructor constructor = proxyClass.getConstructor(MyInvocationHandler.class);
            return constructor.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成源码的方法
     *
     * @param interfaces 为了演示,按一个接口处理
     * @return
     */
    private static String generateSrc(Class<?> interfaces){
        StringBuffer src = new StringBuffer();
        //一个类
        src.append("package com.elensdata.proxy.my;" + ln);
        src.append("import java.lang.reflect.Method;" + ln);
        src.append("public class $Proxy0 extends MyProxy implements " + interfaces.getName() + "{" + ln);

        //构造方法
        src.append("public $Proxy0(MyInvocationHandler h){" + ln);
        src.append("this.h = h;" + ln);
        src.append("}" + ln);

        //循环定义方法，与被代理类的方法同名
        for(Method method : interfaces.getMethods()){
            src.append("public" + method.getReturnType().getName() + " " + method.getName() + "(){" + ln);
            src.append("try{" + ln);
            src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{});" + ln);
            src.append("this.h.invoke(this,m,null);" + ln);
            src.append("}catch(Throwable e){e.printStackTrance();}" + ln);
            src.append("}" + ln);
        }

        src.append("}" + ln);
        return src.toString();
    }
}

/**
 * 代码生成，编译，重新加载到内存
 * 类加载器使用classLoader
 */
class MyClassLoader extends ClassLoader{

    File basePath;

    public MyClassLoader(){
        String basePath = MyClassLoader.class.getResource("").getPath();
        this.basePath = new File(basePath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = MyClassLoader.class.getPackage().getName() + "." + name;

        if(null != basePath){
            File classFile = new File(basePath,name.replaceAll("\\.","/") + ".class");
            if(classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = in.read(buffer)) != -1){
                        out.write(buffer,0,len);
                    }
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    classFile.delete();
                    if(null != in){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != out){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}

interface Car{
    void sale();
}

class QQCar implements Car{

    @Override
    public void sale() {
        System.out.println("出售一辆QQ");
    }
}

class Car2{
    void sale(){
        System.out.println("出售一辆汽车");
    };
}

class QQCar2 extends Car2{
    @Override
    void sale() {
        System.out.println("出售一辆QQ");
    }
}

class Agent implements InvocationHandler{

    private Car car;

    /**
     * 返回代理对象，接收被代理对象
     * @param car
     * @return
     */
    public Object getInstance(Car car){
        this.car = car;
        Class clazz = car.getClass();
        System.out.println("代理之前的对象类型：" + car.getClass().getName());
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        System.out.println("代理之后对象类型：" + obj.getClass().getName());
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理人寻找供货商");//方法执行前
        method.invoke(this.car,args);          //执行方法
        System.out.println("代理商出售QQ");    //方法执行完成后
        return null;
    }
}
