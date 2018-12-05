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

###原生的aspectj可以解决“自调用”问题
spring aop只是使用了aspectj的语法，它的实现是运行时动态增强，通过生成一个proxy类来代理原来的类，一旦调用进行原始类，原始类方法调用内部其他方法时，不会走增强逻辑
即使目前方法已经被表象上增加，也无济于事，因为只有在调用proxy类的方法时，才会被增强
aspectj原生则是在编译时增强，即修改原代码中方法的实现代码进行增强
这种类比于修改源代码的操作，javaagent也可以办到，它是通过载入java class文件时，实现ClassFileTransformer接口，修改字节码增强的，如使用javaassit来增强，输出增强后的代码。
