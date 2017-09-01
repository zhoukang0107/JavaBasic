package com.zack.basic.jenum;

/**
 * 枚举（enum）
 *
 *
 *
 *
 */

public class JEnumClient {

    public static void main(String[] args){
        //普通类实现枚举
        TrafficLights light = TrafficLights.RED;
        System.out.println(light.netLight());
        /*
        亮30秒！
        亮30秒！
        亮5秒！
        红灯完了绿灯亮！
        GREEN
        */
        //测试
    }

    /**
     * 含参构造函数的枚举
     */
    public enum Week{
        SUN(1),MON(),TUE,WEB,THI,FRI,SAT;
        Week(){

        }
        Week(int time){

        }
    }

    public enum Month{

    }

}
