package com.jl.common.aspectj;

import com.jl.common.aspectj.bean.PrintService;

/**
 */
public class Main {

    public static void main(String[] args) {
        PrintService printService = new PrintService();
        printService.testInnerInvoke();
    }

}
