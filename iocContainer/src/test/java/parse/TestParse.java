package parse;

import com.myd.aop.filter.MethodFilter;
import com.myd.ioc.parse.ParseProperty;
import org.junit.jupiter.api.Test;
import parse.model.TestVolatile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myd
 * @date 2021/7/27  1:55
 */

public class TestParse {


//    private String ;

    @Test
    public void testVolatile(){
        new Thread(()->{
            System.out.println("thread start..");
            TestVolatile.getInstance().test();
            System.out.println("thread end..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        } ).start();

        Map<String ,Object> map  = new HashMap<>();


//        if()


        while(TestVolatile.getInstance().number==10){
            System.out.println("see the number ... not use volatile...");
        }
        System.out.println(" not see the number ...if  not use volatile...");

    }

    @Test
    public void test(){
        try {
            Properties properties = ParseProperty.parseProperty("rr.properties");
            Iterator<Object> iterator = properties.keySet().iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                Object value = properties.get(key);
                System.out.println("key:" +key+";value:"+value);
              }
         } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tesdtr(String t){
        System.out.println(t);

    }
    @Test
    public void testStr(){

        String y =" ${34qwer} ";
        y =y.trim();
        boolean t = y.matches("^(\\$\\{.+\\}$)");
        System.out.println(t);
        System.out.println(y.substring(2,y.length()-1)+";hh");
    }

    @Test
    public void testReflct() throws NoSuchMethodException {

        Method[] methods = TestParse.class.getMethods();
//        Method testStr = TestParse.class.getMethod("tesdtr");
//        System.out.println(testStr.getName());
        for (Method method : methods) {

            String name = method.getName();
            System.out.println(name);
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }



        }


    }


    @Test
    public void testRegex(){

//       String execution = "execution";

        String modifierRegex  = "(public|protected|private|\\*)";//匹配访问修饰符:public,protected,private,*(不限制访问修饰符)

        String packageRegex = "((\\w+\\.)*((\\*?\\w+\\*?|\\*|\\w+\\.?)\\.))?";//匹配包

        String methodName = "(\\*?\\w+\\*?|\\*)";//\\w*\\*? 可以匹配空,也就是没有写方法名也会被认为正确e.g. execution(* qwe.qwe.qew.());

        String param = "([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*";//匹配：Boolean[][],int[],float...
        String allType = "\\.\\.";//..
        String noParam="";//无参
        String parameters  ="\\(("+allType+"|"+param+"(,"+param+")*|"+noParam+")\\)";

        String execution = "\\s*execution\\s*\\(\\s*"+modifierRegex+"\\s+"+packageRegex+methodName+parameters+"\\s*\\)\\s*";
        System.out.println(execution);
        String regex = "^\\s*execution\\s*\\(\\s*(public|protected|private|\\*|)\\s+((\\w+\\.)*((\\w*\\*|\\w+\\.)\\.))?((\\w+(\\*)?|\\*)\\((\\.\\.|(([A-Z]\\w*|int|double|float|char|byte|long|short|boolean))(\\[\\])*(,([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*)*|)\\)\\s*\\)\\s*)$";
        //methodName: \\w*(\\*)? 可以匹配空===> (\\w+(\\*)?|\\*)
        boolean t = "execution(* yy.sdf.sdf.sdf.*kk.yy*(TT,YD[],Rf,int[][],float))".matches(execution);//获取括号内容取匹配方法。。。。
        boolean t1 = "*".matches(methodName);
        System.out.println(t);
//       String exe  ="execution(   *())";
       String hh =  "(\\((\\.\\.|(([A-Z]\\w*|int|double|float|char|byte|long|short|boolean))(\\[\\])*(,([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*)*|)\\)\\s*\\)\\s*)$";

//        Pattern pattern  = Matcher.




    }

    public String trim(String pointcut){
        String pointTrim = pointcut.replaceAll(" ","");
        Pattern pattern = Pattern.compile("(public|private|protected|\\*)");
        Matcher matcher = pattern.matcher(pointTrim);
        String regex=null ;
        if(matcher.find()){
            regex = matcher.group();
        }
        if(regex.equals("*")){
            regex="\\*";
        }
        String  replace = regex +"  ";
        return pointTrim.replaceFirst(regex,replace);

    }

    @Test
    public void testr(){

        System.out.println(getClass().getName());
        String  w= "  q   w  e   ( public   wewqe.qwe.qew.*(erw, wer , wer , wer ,) ) " ;
        String trim = trim(w);
        System.out.println(trim);



    }

    @Test
    public void testPattern(){

        String packageRegex = "((\\w+\\.)|((\\w+\\*?|\\*|\\*?\\w+|\\w*\\.?)\\.))+";//匹配包
        String exe = "execution(public  yy*(TT,YD[],Rf,int[][],float))";
        System.out.println(MethodFilter.modifier(exe));

        System.out.println(MethodFilter.methodNameRegex(exe));

//        System.out.println();
        String[] params = MethodFilter.parameterRegex(exe);
        System.out.print("\n");
        for (String param : params) {
            System.out.print(param+"\t");
        }

    }

}
