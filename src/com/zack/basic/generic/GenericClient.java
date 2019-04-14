package com.zack.basic.generic;

public class GenericClient {

    public static void main(String[] args){
        Box<Number> box = new Box<>();
        //T==Number  Float和Integer是Number的子类
        box.setData(new Float(2f));
        box.setData(new Integer(1));

        //错误 box是Box<Number>类型，Box<Integer> 并不是 Box<Number> 的子类
        //box = new Box<Integer>();   //error

        Box<? extends Number> box1 = new Box<>();
        //box1 = new Box<Integer>();   //success
        Number number = box1.getData();
        //box1.setData(new Integer(1));    //error extends 不能往里存，只能往外取
        //box1.setData(new Object());      //error extends 不能往里存，只能往外取
        Number number1 = box1.getData();
        Object o = box1.getData();
        //Integer son = box1.getData();   //error

        box1 = new Box<Integer>();
        //box1.setData(new Integer(1));//error extends 不能往里存，只能往外取
        //box1.setData(new Float(1)); //error extends 不能往里存，只能往外取
        getUpperNumberData(box1);

        //getExtendsNumberData(box1);   //error
        getExtendsNumberData(new Box<Object>());

        //Java强制在创建对象时，必须给类型参数制定具体的类型，不能使用通配符
        //Box<? super Number> box2 = new Box<? super Number>();  //类型未确定

        Box<? super Number> box3 = new Box<>();
        box3.setData(new Integer(1));  //? super Number限定了形参类型的下限为Number
        //box3.setData(new Object());  //error
        //? super T  参数类型的限制
    }

    public static void getUpperNumberData(Box<? extends Number> data){
        System.out.println("data :" + data.getData());
    }

    public static void getExtendsNumberData(Box<? super Number> data){
        System.out.println("data :" + data.getData());
    }
}
