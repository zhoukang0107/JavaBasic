package com.zack.basic.concurrent.base;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入输出流
 * 与普通流相比，管道输入输出流主要用于线程之间数据传递，而传递的媒介为内存
 * PipedOutputStream/PipedInputStream/PipedReader/PipedWriter
 *
 *
 */
public class ThreadPiped {

    public static void main(String[] args) throws IOException {
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();
        //将输出流和输入流进行连接，否则抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();

        int receive = 0;
        while ((receive = System.in.read())!=-1){
            out.write(receive);
        }
        out.write(receive);
        out.close();
        in.close();
    }


    private static class Print implements Runnable {
        PipedReader in;
        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive= in.read())!=-1){
                    System.out.print((char)receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
