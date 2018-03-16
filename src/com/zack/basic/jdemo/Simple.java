package com.zack.basic.jdemo;

import java.util.List;

public class Simple {
    static class SuperClass{
        public String name = "super";
        public SuperClass(){
            System.out.println(name);
        }

        public void medth(List<Integer> list){
            System.out.println("super medth");
        }


    }

    static class SubClass extends SuperClass{
        public String name = "sub";
        public SubClass(){
            System.out.println(name);
        }

        /*public void medth(List<String> list){
            System.out.println("sub medth");
            return 0;
        }*/
    }

    public static void main(String[] args){
        SubClass sub = new SubClass();
        //sub.medth();
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 4;
        Integer e = 4;
        System.out.println(d==e);
        System.out.println(d.equals(e));
        System.out.println(d==a+c);
        System.out.println(d.equals(a+c));
       // System.out.println(name);


    }
}
