package parse.aop;

/**
 * @author myd
 * @date 2021/8/6  17:11
 */

public class UU implements T{


    public void t1(){
        System.out.println(getClass().getSimpleName()+";method=t1");
    }

    public void t2(){
        System.out.println(getClass().getSimpleName()+";method=t2");
    }

    public void t3(){
        System.out.println(getClass().getSimpleName()+";method=t3");
    }

    @Override
    public void t4(String name) {

    }


}
