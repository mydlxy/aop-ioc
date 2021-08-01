package parse.anno;

import com.myd.ioc.annotations.Bean;
import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Configuration;
import com.myd.ioc.annotations.Service;
import org.junit.jupiter.api.Test;
import parse.anno.configurationTest.ConTest;
import parse.model.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/30  12:22
 */

public class Ano {




    @Test
    public void testAA(){
        Class<ConTest> bean = ConTest.class;

        System.out.println(bean.getSimpleName());
        Service service =(Service) bean.getAnnotation(Service.class);
        System.out.println(service.annotationType().getSuperclass());





    }



    @Test
    public void testAyy(){

        List<String> s = new ArrayList<>();
        List<String> ss  =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            s.add(i+"");
            ss.add((i+10)+"");
        }
        hb(s,ss);
        System.out.println(s.size());

    }


    public void hb(List<String> s1,List<String>s2){
        List<String> s = new ArrayList<>();
        s.addAll(s1);
        s.addAll(s2);

//        s1.addAll(s2);
    }
    @Test
     public void test(){

//        System.out.println(Bean.class.getTypeName());//com.myd.ioc.annotations.Bean

        Class u = User.class;

        Configuration annotation = ConTest.class.getAnnotation(Configuration.class);
        String[] load = annotation.loadProperties();
        if(load == null || load.length==0){
            System.out.println("path is null");
            return;
        }
       int tt =  (load[0].trim().length()==0) ? 0: 1;
        System.out.println(load[0]);
        if(tt ==0)
            return;


        Method[] methods = u.getMethods();


         for (Method method : methods) {


             String type = method.getReturnType().getSimpleName();
             String name = method.getName();

             System.out.println("method:"+name+";returnType:"+type);


         }
    }
}
