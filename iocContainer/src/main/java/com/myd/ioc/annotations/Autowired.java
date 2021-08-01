package com.myd.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author myd
 * @date 2021/7/31  2:21
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

    /**
     *
     *自动注入
     * 如果不写id，则在IocContainer中找到该类型对象，并自动注入;<br>
     *   如果该类型在容器中有多个则报错。<br>
     *因此，该类型在容器中被注册了多个，就需要写id了,确定要注入哪一个对象；<br>
     * e.g.
     * container.registerBean(a1,new A())<br>
     * container.registerBean(a2,new A())<br>
     * container.registerBean(b,new B())<br>
     * @Autowired
     * private A;
     *
     * @Autowired
     * private B;
     *
     *
     * 在自动注入 A 时，会报错；
     * 正确写法：
     * @Autowired("a1")
     *
     * 在自动注入 B 时，不会报错；
     *
     * @return
     */
    String id() default "";
}
