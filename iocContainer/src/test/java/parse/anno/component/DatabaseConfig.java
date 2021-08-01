package parse.anno.component;

import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;

/**
 * @author myd
 * @date 2021/8/1  16:52
 */
@Component
public class DatabaseConfig {

   @Value("${username}")
   private  String username;
   @Value("${url}")
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
