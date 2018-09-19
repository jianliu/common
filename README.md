# Common
some common utils that not always use,but useful in some where

## util
###page rpc query util分页工具
```java
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
```
###class scan and simple filter 类扫描工具
```java
List<Class<?>> classes = ClassUtil.getClasses("com.jl.common", clazz -> true);
```

