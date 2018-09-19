package com.jl.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @see this#queryPageWithRequiredNum(List, int, CallablePageFunctional, Integer)
 * 分页查询工具,将传入的list参数，按pageSize分隔，多次查询，最后返回List<R(esult)>，直到查询获取requiredNum个结果
 * <p>
 * Created by cdliujian1 on 2018/4/11.
 */
public class PageFetchUtil {


    private final static LoopHandler TRUE_LOOP_HANDLER = new LoopHandler();

    /**
     * 查询，直到获取直到个数的数据
     * 分页查询工具,将传入的list参数，按pageSize分隔，多次查询，最后返回List<R(esult)>，直到查询获取requiredNum个结果
     *
     * @param parameterList
     * @param pageSize
     * @param singlePageFun
     * @return
     */
    public static <T, R> List<R> queryPageWithRequiredNum(List<T> parameterList, int pageSize, CallablePageFunctional<T, R> singlePageFun){
        return queryPageWithRequiredNum(parameterList, pageSize, singlePageFun, null);
    }

    /**
     * 查询，直到获取直到个数的数据
     * 分页查询工具,将传入的list参数，按pageSize分隔，多次查询，最后返回List<R(esult)>，直到查询获取requiredNum个结果
     *
     * @param parameterList
     * @param pageSize
     * @param requiredNum
     * @return
     */
    public static <T, R> List<R> queryPageWithRequiredNum(List<T> parameterList, int pageSize, CallablePageFunctional<T, R> singlePageFun, Integer requiredNum) {

        List<R> resultList = new ArrayList<>();
        LoopHandler loopHandler = new LoopHandler();

        //分页查询，每页pageSize
        queryByPage(parameterList, pageSize, (list) -> {
            List<R> singleQueryList = singlePageFun.run(list);

            if (singleQueryList != null) {
                //仅返回oriShowNum个数的促销
                if (requiredNum != null) {
                    for (R item : singleQueryList) {
                        //已获取到需要的促销个数，break后续查询
                        if (resultList.size() >= requiredNum) {
                            loopHandler.setBreak();
                            break;
                        } else {
                            resultList.add(item);
                        }
                    }
                } else {
                    resultList.addAll(singleQueryList);
                }
            }
        }, loopHandler);

        return resultList;
    }

    /**
     * 根据mod进行分页查询数据，并进行业务逻辑处理
     *
     * @param itemList
     * @param pageSize 单次查询个数
     * @param fun
     * @param <T>
     */
    public static <T> void queryByPage(List<T> itemList, int pageSize, PageFunctional<T> fun) {
        queryByPage(itemList, pageSize, fun, TRUE_LOOP_HANDLER);
    }

    /**
     * 根据mod进行分页查询数据，并进行业务逻辑处理
     *
     * @param itemList
     * @param pageSize 单次查询个数
     * @param fun
     * @param <T>
     */
    public static <T> void queryByPage(List<T> itemList, int pageSize, PageFunctional<T> fun, LoopHandler loopHandler) {
        if (pageSize <= 0) {
            return;
        }

        //小于单页查询pageSize
        if (itemList.size() <= pageSize) {
            fun.run(itemList);
            return;
        }

        int fromIndex = 0;
        int end = 0;
        List<T> subList;
        while (fromIndex < itemList.size()) {
            end += pageSize;
            if (end > itemList.size()) {
                end = itemList.size();
            }
            subList = itemList.subList(fromIndex, end);
            fun.run(subList);
            if (loopHandler.isBreak()) {
                break;
            }
            fromIndex = end;
        }
    }

    @FunctionalInterface
    public interface PageFunctional<T> {

        /**
         * 执行单页查询的动作
         */
        void run(List<T> pageItems);

    }

    @FunctionalInterface
    public interface CallablePageFunctional<T, R> {

        /**
         * 执行单页查询的动作
         */
        List<R> run(List<T> pageItems);

    }

    public static class LoopHandler {

        private boolean isBreak = false;

        public boolean isBreak() {
            return isBreak;
        }

        public void setBreak() {
            this.isBreak = true;
        }
    }

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            params.add(i + "");
        }
        List<Integer> resultList = PageFetchUtil.queryPageWithRequiredNum(params, 3, (parameterList) -> {
            List<Integer> results = new ArrayList<>();
            for (String param : parameterList) {
                //could replaced by some rpc request
                results.add(Integer.valueOf(param));
            }
            return results;
        }, 9);

        //123456789
        resultList.forEach(System.out::print);


        resultList = PageFetchUtil.queryPageWithRequiredNum(params, 3, (parameterList) -> {
            List<Integer> results = new ArrayList<>();
            for (String param : parameterList) {
                results.add(Integer.valueOf(param));
            }
            return results;
        });
        System.out.println();

        //12345678910111213141516171819
        resultList.forEach(System.out::print);
    }
}
