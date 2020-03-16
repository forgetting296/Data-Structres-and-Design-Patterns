package com.shusaku.study.stream;

import java.util.function.Function;

public class TransformFunction {

    //这个方法的本质就是Function<Function<I,O>,O>
    static O consume(Function<I,O> f){
        return f.apply(new I());
    }

    static Function<I,O> f2 = f -> new O();

    public void testConsumeFunction(){
        O o1 = consume(i -> new O());
        System.out.println(o1);
        Function<Function<I, O>, O> consume1 = TransformFunction::consume;
        O o2 = consume1.apply(f -> consume(i -> new O()));
        System.out.println(o2);
        O apply = consume1.apply(f -> f2.apply(new I()));
        System.out.println("- " + apply);
    }

    static Function<I,O> transformForm(Function<I,O> in){
        return in.andThen(o -> {
            System.out.println(o);
            return o;
        });
    }

    static Function<I,O> transform(Function<I,O> in) {
        return in.compose(i -> {
            System.out.println("before");
            return i;
        });
    }


    public void testTransformFunction(){
        Function<I, O> function = transformForm(i -> {
            System.out.println(i);
            return new O();
        });
        function.apply(new I());

    }

    public void testTransformFunction2(){
        Function<I, O> function = transform(i -> {
            System.out.println(i);
            return new O();
        });
        function.apply(new I());
    }

    public void testIdentity(){
        Function<Object, Object> identity = Function.identity();
        Object apply = identity.apply(new I());
        System.out.println(apply);
    }
}

class I{
    @Override
    public String toString() {
        return "I{}";
    }
}

class O{
    @Override
    public String toString() {
        return "O{}";
    }
}


