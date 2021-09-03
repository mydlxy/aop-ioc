package parse.anno.component;

import com.myd.ioc.annotations.Autowired;
import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;
import parse.aop.T;
import parse.aop.T2;
import parse.model.User;

import java.io.Serializable;

/**
 * @author myd
 * @date 2021/8/1  16:52
 */
@Component("qw")
public class DatabaseConfig implements T2 {

   @Autowired
   private User user;//xml配置
   @Autowired
   private Model2 model2;//注解配置
   @Value("${username}")//@Value("${username}")
   private  String username;
   @Value("${url}")
   private String url;
   @Value("${password}")
   private String password;
    @Value("{driverClass}")
   private String driverClass;

    @Override
    public String toString() {
        int i = 1/0;
        return "DatabaseConfig{" +
                "username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                ", driverClass='" + driverClass + '\'' +
                '}';
    }

    @Override
    public void print() {
        int i = 1/0;
        System.out.println("beanName:"+getClass().getName()+";"+toString());
    }
}
