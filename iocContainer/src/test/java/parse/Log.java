package parse;

/**
 * @author myd
 * @date 2021/8/6  17:08
 */

public class Log {


    public void beforeTest(){
        System.out.println("aspect's method....");
    }


    public void test1(){

        System.out.println("not aop method...");
    }

}
