package parse;

import com.myd.aop.AopUtils;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.proxy.CglibProxy;
import com.myd.aop.proxy.JDKProxy;
import com.myd.ioc.annotations.Autowired;
import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.jupiter.api.Test;
import parse.anno.component.DatabaseConfig;
import parse.anno.component.Model;
import parse.aop.T;
import parse.aop.T2;
import parse.aop.U2;
import parse.aop.UU;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

    @Test
    public void test3() throws NoSuchFieldException, IllegalAccessException {

        DatabaseConfig config = new DatabaseConfig();
        CglibProxy cglibProxy = new CglibProxy(config,null);
        DatabaseConfig instance = cglibProxy.createInstance();
//        Component t = (Component)findAnnotation(instance.getClass(),Component.class);
//        System.out.println(t.value());

        CglibProxy modelProxy = new CglibProxy(new Model(),null);
        Model model = modelProxy.createInstance();
        System.out.println(model.getClass().getAnnotation(Component.class));
//        model.name="fuck,you";

        Field name = model.getClass().getSuperclass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(model,"zhijieSetValue");

        System.out.println(model.setModelType("sdf"));
//        System.out.println(model.modelType);
        System.out.println(model.getClass().getName());
        System.out.println(model.getClass().getSuperclass().getName());
        Field[] fields = instance.getClass().getDeclaredFields();

        System.out.println(instance.getClass().getName());
        System.out.println(instance.getClass().getSuperclass().getName());

        System.out.println(model.toString());


    }

    @Test
    public void test12(){
        BeanGenerator b  = new BeanGenerator();
        b.setSuperclass(Model.class);
//        b.addProperty();

    }

    @Test
    public void testSaveClassFile(){
        //cglib下载生成类的class
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/ww");

        CglibProxy modelProxy = new CglibProxy(new Model(),null);



        Object instance = modelProxy.createInstance();


    }

    @Test
    public void testAnn(){

        CglibProxy modelProxy = new CglibProxy(new Model(),null);

        Object model = modelProxy.createInstance();


        Class modelClass = model.getClass();
        Component component = model.getClass().getAnnotation(Component.class);
        if(component!= null){
            System.out.println(component);
        }
        Field[] fields = modelClass.getDeclaredFields();

        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if(autowired!= null){
                System.out.println(field.getName()+";"+autowired);
            }
            Value value = field.getAnnotation(Value.class);
            if(value!= null){
                System.out.println(field.getName()+";"+value);
            }


        }


    }


    public Annotation findAnnotation(Field field,Class annotation){

        Annotation ann = field.getAnnotation(annotation);
        if(ann == null) {
            System.out.println(field.getName() + " not found annotation..");
            return null;
        }
        return ann;

    }

    @Test
    public void test() throws IllegalAccessException, InvocationTargetException {

        CglibProxy modelProxy = new CglibProxy(new Model(),null);
        Model model = modelProxy.createInstance();
        System.out.println(model.getClass().getName());
        System.out.println(model.getClass().getSuperclass().getName());
        Field[] fields = Model.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.getName().equals("name")){
                field.set(model,"sonValue");
//                field.
            }
            if(field.getName().equals("modelType")){
                field.set(model,"sonType");
            }
        }


        System.out.println(model.toString());


        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("************************************");
        Method[] methods = model.getClass().getSuperclass().getMethods();

        for (Method m : methods) {

            if(m.getParameters().equals("setName")){
                m.invoke(model,"sonSetName");
            }
            if(m.getParameters().equals("setModelType")){
                m.invoke(model,"sonSetModelType");
            }


        }

        System.out.println(model.toString());


    }




    public Annotation findAnnotation(Class beanClass, Class annotation){

        Annotation ann = beanClass.getAnnotation(annotation);
        System.out.println(ann);

        if(ann == null){
            System.out.println(beanClass.getName()+"not found annotation:"+annotation);
            if(beanClass.getSuperclass()!= null)
                return findAnnotation(beanClass.getSuperclass(),annotation);
            else
                return null;
        }

        return ann;

    }

    @Test
    public void test78(){

        String execution = AspectConfig.getExecution();
        System.out.println(execution);

        String exe  ="execution( * com.*myd.gg.name*(int[] ,inn, Boolean[] ) ) ";
        boolean isTure = exe.matches(execution);
        System.out.println(isTure);

    }
    @Test
    public void test34() throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("tt"));

        System.out.println(objectInputStream.readObject());

    }

    @Test
    public void testJDK()throws Exception{

        U2  u = new U2();
        //cglib save class
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/ww");
        //jdk save class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        byte[] proxy00s = ProxyGenerator.generateProxyClass("proxy1", U2.class.getInterfaces());
        FileOutputStream fileOutputStream = new FileOutputStream("./Proxy1.class");
        fileOutputStream.write(proxy00s);
        JDKProxy jdkProxy = new JDKProxy(u,null);
        T t = jdkProxy.newInstance();

        System.out.println(t.getClass().getName());

        t.t4("dfg");
    }

    @Test
    public void testJDPP() throws NoSuchFieldException, IllegalAccessException {
        U2  u = new U2();
        System.out.println("u"+u.toString());
        JDKProxy jdkProxy = new JDKProxy(u,null);
        T t = jdkProxy.newInstance();

        System.out.println(t.getClass().getName());
        //获取到jdkProxy
        Field h = t.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        JDKProxy jdk = (JDKProxy)h.get(t);
        Field target = jdk.getClass().getDeclaredField("target");
        target.setAccessible(true);
        System.out.println("target:"+target.get(jdk).toString());


        target.setAccessible(true);
        String s1 = u.toString();
        

        System.out.println(h.toString());

//        Class<? super JDKProxy> aClass = jdProxy.getType().asSubclass(JDKProxy.class);

//        System.out.println(to.getName());
//



    }

    @Test
    public void test344() throws IllegalAccessException {

        CglibProxy modelProxy = new CglibProxy(new Model(),null);

        Object instance = modelProxy.createInstance();

        List<Field> beanValue = AopUtils.getBeanValue(instance.getClass());

        for (final Field field : beanValue) {
            field.setAccessible(true);
            field.set(instance,"nn");

        }
        System.out.println(instance.toString());

    }

}
