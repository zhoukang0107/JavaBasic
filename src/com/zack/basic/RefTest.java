package com.zack.basic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Properties;

/**
 * 字节码  Class类
 *
 * 得到一个类的字节码：
 * Class cls1 = Data.class;
 * Class cls2 = Class.forName("java.lang.String");
 * Class cls3 = data.getClass();
 *
 * 九个预定义的Class示例对象
 * 8个基本数据类型+void.class
 *
 * 在源程序中出现的类型都有各自的Class示例对象，例如：int[].class;void.class
 *
 *
 * 反射：
 * 反射就是把java类中的各种成分映射成相应的java类
 * Medthod,Contructor，Package,Field
 *
 * Contructor:代表某个类的一个构造方法
 *
 *
 * 框架与工具类
 * 框架调用用户的代码，工具类是被用户调用的
 *
 *
 *
 */

public class RefTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
        // TODO Auto-generated method stub
        loadFrame();

    }

    private static void loadFrame() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated method stub
        //InputStream is = new FileInputStream("config.properties");
        //InputStream is = RefTest.class.getClassLoader().getResourceAsStream("com/studyx/ref/config.properties")
        InputStream is = RefTest.class.getResourceAsStream("config.properties");
        //InputStream is = RefTest.class.getResourceAsStream("res/config.properties");
        //InputStream is = RefTest.class.getResourceAsStream("com/zack/basic/config.properties");

        Properties config = new Properties();
        config.load(is);
        is.close();
        String className = config.getProperty("className");
        Class cls = Class.forName(className);
        Collection collection = (Collection) cls.newInstance();
        System.out.println(cls.getName());
    }

    //没有办法得到数组中元素的类型
    private static void printObject(Object object) {
        // TODO Auto-generated method stub
        Class cls = object.getClass();
        if (cls.isArray()){

        }else{

        }
    }


}


class MyMain{
    public static void main(String[] args) {
        for (String str : args){
            System.out.println(str);

        }
    }
}