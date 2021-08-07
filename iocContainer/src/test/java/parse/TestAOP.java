package parse;

import com.myd.aop.proxy.CglibProxy;
import org.junit.jupiter.api.Test;
import parse.aop.U2;
import parse.aop.UU;
/**
 * @author myd
 * @date 2021/8/6  17:06
 */

public class TestAOP {


    @Test
    public void test1() throws NoSuchMethodException {

        UU  uu = new  UU();

        CglibProxy cglibProxy = new CglibProxy(uu,null);

        UU instance = cglibProxy.createInstance();

        instance.t1();

        System.out.println(UU.class.isInstance(instance));

        System.out.println(instance.getClass().getSuperclass().equals(uu.getClass()));


    }

    @Test
    public void test4(){
        String name="after";
        String setName = "set"+name.toUpperCase().charAt(0)+name.substring(1);
        System.out.println(setName);
    }

    @Test
    public void test2(){

        Class<?>[] uu = UU.class.getInterfaces();
        Class<?>[] u = U2.class.getInterfaces();


        for (int i = 0; i < uu.length; i++) {
            if(uu[i] == u[i]){
                System.out.println("fuck...");
            }
        }
        for (Class<?> u1 : uu) {
            System.out.println(u1.getSimpleName());
        }

        for (Class<?> u1 : u) {
            System.out.println(u1.getSimpleName());
        }



    }
}
