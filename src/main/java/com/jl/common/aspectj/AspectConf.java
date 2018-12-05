package com.jl.common.aspectj;

/**
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect
 * Created by cdliujian1 on 2018/4/14.
 */
@Aspect
public class AspectConf {

    /**
     * 切入点，pcakge下面的类的方法
     */
    @Pointcut(value = "execution(* com.jl.common.aspectj.bean..*.*(..))")
    @SuppressWarnings("all")
    public void pointCutTransaction() {
    }

    /**
     * 操作前
     */
    @Before("pointCutTransaction()")
    public void doBefore() {
//        String methodName = jp.getSignature().getName();
        System.out.println("即将执行一项操作");
    }

    /**
     * 操作后
     * 引入jp参数时，编译后的代码才会生成jp对象，有点按需操作的意思
     *
     * @param jp
     */
    @After("pointCutTransaction()")
    public void after(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("操作已结束：" + methodName);
    }
}
