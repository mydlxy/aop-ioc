package com.myd.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author myd
 * @date 2021/7/31  13:46
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Component {

    /**
     *
     * 默认将class名第一个字母小写,作为id；
     * e.g.
     * @Component
     * public class Model{}
     *
     * container.put(model,new Model())
     *
     *@Component("model1")
     * public class Model{}
     * container.put(model1,new Model())
     *
     * @return
     */
    String value() default "";
}
