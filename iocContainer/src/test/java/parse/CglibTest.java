package parse;

import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import parse.anno.component.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2021/8/11  1:13
 */

public class CglibTest {



    public <T>T  create(Object bean){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj,args);
            }
        });

       return  (T)enhancer.create();
    }
    @Test
    public void test1() throws NoSuchFieldException {

        Model model = new Model();
        model.setName("model-cglib").setModelType("type--1");
        Model t = create(model);
        System.out.println(t.getName());
        Value value = getClass().getSuperclass().getDeclaredField("").getAnnotation(Value.class);





//        m.getSuperclass().get



    }

    @Test
    public void test2(){

        boolean f = "w ees".matches("^(w|2)\\s+\\w\1.*");
        System.out.println(f);
    }
}
