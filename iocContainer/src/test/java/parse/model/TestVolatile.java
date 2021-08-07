package parse.model;

/**
 * @author myd
 * @date 2021/8/3  12:36
 */

public class TestVolatile {

//   private User user;
   public volatile Integer number;

   private static TestVolatile testVolatile;

   public TestVolatile(Integer number){
       this.number = number;
   }
    public TestVolatile(){
        this.number = 20;
    }
   public  static TestVolatile getInstance(){
       if(testVolatile == null){
           testVolatile = new TestVolatile();
       }
       return testVolatile;
   }



   public void test(){
       number  =10;
       System.out.println(number.toString());
   }
}
