package com.jl.common.aspectj;

import com.jl.common.aspectj.bean.PrintService;

/**
 * Created by cdliujian1 on 2018/12/5.
 */
public class Main {

    public static void main(String[] args) {
        PrintService printService = new PrintService();
        printService.testInnerInvoke();
    }

}
