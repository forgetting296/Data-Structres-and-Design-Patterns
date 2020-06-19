package com.shusaku.study.test.morecondition.sort;

import com.google.common.collect.Lists;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class SortByChar {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", 18, 9999.99),
                //new Employee("李四", 37, 5555.99),
                //new Employee("王五", 50, 6666.66),
                new Employee("赵六", 16, 3333.33),
                new Employee("田七", 8, 7777.77)
        );
        employees.forEach(System.out::println);
        // 获取中文环境
        Comparator comparator = Collator.getInstance(Locale.CHINA);
        // 排序实现
        Collections.sort(employees, (e1, e2) -> {
            return comparator.compare(e1.getName(), e2.getName());
        });
        System.out.println("=============================================");
        employees.forEach(System.out::println);
        System.out.println("=============================================");
        employees.sort(Comparator.comparing(Employee::getAge));
        employees.forEach(System.out::println);
        List<List<Employee>> partition = Lists.partition(employees, 4);
        //List<Employee> subList = employees.subList(0, 4);
        List<Employee> subList = partition.get(0);
        System.out.println("=============================================");
        subList.forEach(System.out::println);
        System.out.println("=============================================");

        List<Person> persons = Arrays.asList(
                new Person("张三", 20, Lists.newArrayList("A", "B", "C")),
                new Person("李四", 20, Lists.newArrayList("B", "C", "D"))
        );
        List<String> collect = persons.stream().map(Person::getFriendNames).collect(Collectors.toList()).stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    static class Person {
        private String name;
        private int age;
        private List<String> friendNames;

        public Person(String name, int age, List<String> friendNames) {
            this.name = name;
            this.age = age;
            this.friendNames = friendNames;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getFriendNames() {
            return friendNames;
        }

        public void setFriendNames(List<String> friendNames) {
            this.friendNames = friendNames;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", friendNames=" + friendNames +
                    '}';
        }
    }

    static class Employee {

        private String name;
        private int age;
        private double salary;

        public Employee(String name, int age, double salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
        }
    }
}
