package com.zj.order.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * https://www.cnblogs.com/catkins/p/6021992.html  to CountDownLatch
 */
public class ConcurrentTest {
    final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args){
        //CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行
        //构造器中的计数值（count）实际上就是闭锁需要等待的线程数量。这个值只能被设置一次，
        // 而且CountDownLatch没有提供任何机制去重新设置这个计数值。
        CountDownLatch latch=new CountDownLatch(1);//模拟5人并发请求，用户钱包
        for(int i=0;i<1;i++){//模拟5个用户
            AnalogUser analogUser = new AnalogUser("user"+i,"58899dcd-46b0-4b16-82df-bdfd0d953bfb","1","20.024",latch);
            analogUser.start();
        }
        latch.countDown();//计数器減一  所有线程释放 并发访问。
        //每当一个线程完成了自己的任务后，计数器的值就会减1。
        System.out.println("所有模拟请求结束  at "+sdf.format(new Date()));

    }

    static class AnalogUser extends Thread{
        String workerName;//模拟用户姓名
        String openId;
        String openType;
        String amount;
        CountDownLatch latch;

        public AnalogUser(String workerName, String openId, String openType, String amount,
                          CountDownLatch latch) {
            super();
            this.workerName = workerName;
            this.openId = openId;
            this.openType = openType;
            this.amount = amount;
            this.latch = latch;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                latch.await(); //一直阻塞当前线程，直到计时器的值为0
                //这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            post();//发送post 请求

        }

        public void post(){
           String result = "";
            System.out.println("模拟用户： "+workerName+" 开始发送模拟请求  at "+sdf.format(new Date()));
            result = HttpRequest.sendPost("http://localhost:8080/wallet/walleroptimisticlock.do", "openId="+openId+"&openType="+openType+"&amount="+amount);
            System.out.println("操作结果："+result);
            System.out.println("模拟用户： "+workerName+" 模拟请求结束  at "+sdf.format(new Date()));

        }
    }

}
