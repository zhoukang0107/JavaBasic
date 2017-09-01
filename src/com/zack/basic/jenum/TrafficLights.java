package com.zack.basic.jenum;

/**
 * 使用普通类实现枚举
 */

public abstract class TrafficLights {
    /**
     * 私有化构造函数，防止外部创建该类的实例对象
     */
    private TrafficLights(int time){
        System.out.println("亮"+time+"秒！");
        this.time = time;
    }

    public static TrafficLights RED = new TrafficLights(30) {
        @Override
        public TrafficLights netLight() {
            System.out.println("红灯完了绿灯亮！");
            return GREEN;
        }

        @Override
        public String toString() {
            return "RED";
        }
    };

    public static TrafficLights GREEN = new TrafficLights(30) {
        @Override
        public TrafficLights netLight() {
            System.out.println("绿灯完了黄灯亮！");
            return YELLOW;
        }

        @Override
        public String toString() {
            return "GREEN";
        }
    };

    public static TrafficLights YELLOW = new TrafficLights(5) {
        @Override
        public TrafficLights netLight() {
            System.out.println("黄灯完了红灯亮！");
            return RED;
        }

        @Override
        public String toString() {
            return "YELLOW";
        }
    };

    private int time;   //亮灯时间
    public abstract TrafficLights netLight();

    @Override
    public abstract String toString();
}
