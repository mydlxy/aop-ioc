package parse.anno.component;

import com.myd.ioc.annotations.Autowired;
import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;
import parse.model.User;

/**
 * @author myd
 * @date 2021/8/1  16:52
 */
@Component
public class DatabaseConfig {

   @Autowired
   private User user;//xml配置
   @Autowired
   private Model2 model2;//注解配置
   @Value("${username}")
   private  String username;
   @Value("url0000tooorrr")
   private String url;
   @Value("${password}")
   private String password;
    @Value("${driverClass}")
   private String driverClass;

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                ", driverClass='" + driverClass + '\'' +
                '}';
    }
}
