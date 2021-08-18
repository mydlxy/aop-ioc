package parse.aop;

/**
 * @author myd
 * @date 2021/8/6  17:12
 */

public class U2 implements T{
    private String name;
//    public String getName(){
//        return name;
//    }
//
    public void t1(){
        System.out.println(getClass().getSimpleName()+";method=t1");
    }

    public void t2()
    {
        t3();
        System.out.println(getClass().getSimpleName()+";method=t2");
    }

    public void t3(){
        t1();
        System.out.println(getClass().getSimpleName()+";method=t3");
    }

    public void t4(String arg){
        System.out.println("*******************************");
        System.out.println("this method not aop...."+name);
        System.out.println("*******************************");
    }


}
