package parse;

import com.myd.ioc.context.ApplicationContext;
import com.myd.ioc.context.XmlApplicationContext;
import com.myd.ioc.parse.ParseXml;
import com.myd.ioc.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import parse.anno.component.DatabaseConfig;
import parse.anno.component.Model;
import parse.model.Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/29  5:32
 */

public class TestIOC {

    @Test
    public void test() throws Exception {
        ApplicationContext context = new XmlApplicationContext("bean.xml");
        Teacher t = context.getBean("llt");
        Model model = context.getBean("mmm");
        System.out.println(model.toString());
        System.out.println(model.getUser().toString());
        System.out.println(model.getUser().getTeacher().toString());
//        context.
        System.out.println(t.toString());
        DatabaseConfig databaseConfig = context.getBean(DatabaseConfig.class);

        System.out.println(databaseConfig.toString());


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
    public void testrEGEX(){
        List<String> names = RegexUtils.scanClassNames("com.myd.ioc");

        System.out.println(names.size());
        int i=1;
        for (String name : names) {
            if(i%3==0){
                System.out.println("\n");
            }
                System.out.print(name+"\t");
        }
    }

    @Test
    public void testPath(){
        //获取项目根路径
        System.out.println(TestIOC.class.getResource("/").getPath());
        // path: /E:/Project/IDEA/2021/07/aop-ioc/iocContainer/target/test-classes/
    }
}
