package com.jl.common.aspectj.bean;

/**
 * aspectj本身并无“自调用”问题
 * Created by cdliujian1 on 2018/12/5.
 */
public class PrintService {

    /**
     * 方法编译后代码:
     *  private void print() {
     JoinPoint var1 = Factory.makeJP(ajc$tjp_0, this, this);

     try {
     AspectConf.aspectOf().doBefore();
     System.out.println("PrintService doPrint");
     } catch (Throwable var3) {
     AspectConf.aspectOf().after(var1);
     throw var3;
     }

     AspectConf.aspectOf().after(var1);
     }
     */
    private void print() {
        System.out.println("PrintService doPrint");
    }

    /**
     * 方法编译后代码:
     * public void testInnerInvoke() {
     JoinPoint var1 = Factory.makeJP(ajc$tjp_1, this, this);

     try {
     AspectConf.aspectOf().doBefore();
     this.print();
     } catch (Throwable var3) {
     AspectConf.aspectOf().after(var1);
     throw var3;
     }

     AspectConf.aspectOf().after(var1);
     }
     */
    public void testInnerInvoke() {
        print();
    }
}
