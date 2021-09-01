package parse;

import com.myd.aop.filter.Pointcut;
import com.myd.ioc.context.ApplicationContext;
import com.myd.ioc.context.XmlApplicationContext;
import com.myd.ioc.parse.ParseXml;
import com.myd.ioc.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import parse.anno.component.DatabaseConfig;
import parse.anno.component.ModeImpl;
import parse.anno.component.Model;
import parse.aop.T2;
import parse.model.Teacher;
import parse.model.User;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/29  5:32
 */

public class TestIOC {



    class Human{
        public void sayHello(){
            System.out.println("human say hello.");
        }

    }

    class Man extends Human{
        public void sayHello(){
            System.out.println("man say hello.");
        }


    }
    class Women extends Human{
        public void sayHello(){
            System.out.println("Women say hello.");
        }

    }




    @Test
    public void test66(){

        Human h  = new Man();

        Human w = new Women();

       h.sayHello();
       w.sayHello();
    }


    @Test
    public void test() throws Exception {
        System.out.println(Teacher.class.getName());
        ApplicationContext context = new XmlApplicationContext("bean.xml");
//        Teacher t = context.getBean(Teacher.class);
//        User user = context.getBean(User.class);
//        System.out.println(user.toString());
//        System.out.println(user.getTeacher().toString());
//        System.out.println(t.toString());
//        System.out.println("===============================");

//        Serializable config =context.getBean(Serializable.class);

        System.out.println("**********aop****************");

        Model model = context.getBean(Model.class);

//        Model bean = context.getBean(Model.class);

        System.out.println(context.getBean(Model.class).toString());
        System.out.println(model.getClass().getName());

//        model.getUser();


    }
    @Test
    public void test45() throws Exception {
        ApplicationContext context = new XmlApplicationContext("bean.xml");
        T2 t = context.getBean(T2.class);
//        System.out.println(t.getClass().getName());
        Model model  = context.getBean("modeImpl");
        System.out.println(model.toString());
    }


    public void test44(){

       new Thread(new Runnable() {
           @Override
           public void run() {

           }
       }).start();
    }
    @Test
    public void pathTest(){
        String path = ParseXml.class.getPackage().getName();
        System.out.println(ParseXml.class.getPackage().getName());
        String configLocation = path.replaceAll("\\.","/");
        System.out.println(configLocation);
        String abstractPath = Thread.currentThread().getContextClassLoader().getResource(configLocation).getPath();
        System.out.println(abstractPath);

        File ff = new File(abstractPath);
        boolean directory = ff.isDirectory();
        System.out.println(directory);


        String r = Thread.currentThread().getContextClassLoader().getResource( "com/myd/ioc").getPath();

        File f = new File(r);
        String[] list = f.list();
        System.out.println("**************************");
        for (String s : list) {
            System.out.println(s);
        }



//        System.out.println(list);
    }

    @Test
    public void testPath(){
        //获取项目根路径
        System.out.println(TestIOC.class.getResource("/").getPath());
    }

    public void test22(String q,int r){

    }

    public void test22(int g,String d){

    }

}
