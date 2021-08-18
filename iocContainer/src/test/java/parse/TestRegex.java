package parse;

import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.proxy.JDKProxy;
import com.myd.ioc.beans.IocContainer;
import org.junit.jupiter.api.Test;
import parse.aop.T;
import parse.aop.U2;
import parse.aop.UU;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/5  17:11
 */

public class TestRegex {


    public String UpperFirst(String str){
        return str.toUpperCase().substring(0,1)+str.substring(1);
    }

    @Test
    public void test34(){

        String t = "wrwe";
        System.out.println(UpperFirst(t));
    }
    @Test
    public void test1(){



        String regex0 = "we.wer.wer.ee";//==>we.wer.wer.ee(\\.\\w+)? 可能是类名前缀
        String regex0re = "we.wer.wer.ee(\\.\\w+)?";
        String re0 = regex(regex0);
        System.out.println(re0);
        System.out.println(re0.equals(regex0re));

        String regex1 = "we.wer.wer.ee*";// ===>we.wer.wer.ee\\w*(\\.\\w+)?

        System.out.println(regex(regex1));

        String regex2 = "we.wer.wer.*ee*";//===>we.wer.wer.\\w*ee\\w*(\\.\\w+)?
        String regex2re ="we.wer.wer.\\w*ee\\w*(\\.\\w+)?";
        System.out.println(regex(regex2));

        String regex3 = "we.wer.wer.ee.*";//==>we.wer.wer.ee\\.\\w+
        String regex3re ="we.wer.wer.ee\\.\\w+";
        System.out.println(regex(regex3));


        String regex4 = "we.wer.wer.ee.";//==>we.wer.wer.ee.*
        String regex4re = "we.wer.wer.ee.*";
        System.out.println(regex(regex4));
//        System.out.println(regex.contains("*"));



        String beanName = "we.wer.wer.ee.weq.TTT";
        System.out.println(beanName.matches(regex4re));




    }

    private void m1(){}
    protected void m2(){}
    public void m3(Map<String,String> map){}
    void m4(){}
    public void m5(){}
    @Test
    public void testModifier() throws NoSuchMethodException {
        Method[] declaredMethods = TestRegex.class.getDeclaredMethods();

        Method m = TestRegex.class.getMethod("m3",Map.class);

        System.out.println(m.getName());
        for (Method method : declaredMethods) {
           if( method.getName().matches("m[1-5]")){
               System.out.println(method.getName()+":"+method.getModifiers());

//               int modifiers = method.getModifiers();

           }
        }




    }
    @Test
    public void test3(){
        String pointcut = "execution(* ter*.*sdf.*d8*.*..test(int,Sdf[] , byte[] ) )  ";
        AspectConfig aspectConfig = AspectConfig.getAspectConfig();
//        pointcut = aspectConfig.trim(pointcut);
        System.out.println(pointcut);
        String s = ClassFilter.packageRegex(pointcut);
        System.out.println(ClassFilter.regexClass(s));
        System.out.println(s);

    }

    public String regex(String regex){

        if(regex.endsWith("."))return regex+"*";
        if(regex.endsWith(".*"))return regex.replace(".*","\\.\\w+");

       if(regex.contains("*")){
           regex  =regex.replaceAll("\\*","\\\\w*");
           System.out.println(regex);
       }
        return regex+"(\\.\\w+)?";
    }

    @Test
    public void test2(){
        String q = "we.wer.wer.ee.we*";
        regex(q);
    }

    @Test
    public void test6(){

        Class<?>[] interfaces = getClass().getInterfaces();
        if(interfaces!=null)
            System.out.println(interfaces.length);

    }

    @Test
    public void test() throws NoSuchMethodException {
        AspectConfig config = AspectConfig.getAspectConfig();
        String exe = "execution(* parse.aop.t*())";
        config.setAspect(new Log());
        String methodName = "beforeTest";
        HashMap<String, String> before = new HashMap<>();
        before.put(exe,methodName);
//        config.setBefore(before);
        String u2Name = U2.class.getName();
        U2 u2 = new U2();

        if(ClassFilter.matchClass(u2,exe)) {
            JDKProxy jdkProxy = new JDKProxy(u2, null);
            T t = jdkProxy.newInstance();
            t.t1();
            t.t2();
            t.t3();
            t.t4("dgf");

            System.out.println("u2:"+u2.getClass().getSimpleName());
            System.out.println("t.superClass="+t.getClass().getSuperclass().getName());
            Class[] uu = u2.getClass().getInterfaces();

            for (Class u : uu) {
                System.out.println(u.getName());
            }
            System.out.println("+++++++++++++++++++++++++++");
            Class[] cc = T.class.getInterfaces();
            for (Class a : cc) {
                System.out.println(a.getName());
            }
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println(uu);
            System.out.println(cc);

//            u2.getClass().getComponentType()

            System.out.println(T.class.isInstance(u2));

        }



    }

    public void test56(){
        AspectConfig config = AspectConfig.getAspectConfig();
        String beforePointcu = "execution()";
        config.setAspect(new Log());
        String methodName = "beforeTest";
        HashMap<String, String> before = new HashMap<>();
        before.put(beforePointcu,methodName);
//        config.setBefore(before);

        IocContainer container = IocContainer.container();
        container.registerBean("uu",new UU());
        container.registerBean("u2",new U2());


        String u2Name = U2.class.getName();
        if(u2Name.matches(beforePointcu)){
//            JDK
        }




    }

}
