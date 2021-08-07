package parse.anno.configurationTest;

import com.myd.ioc.annotations.*;
import parse.model.User;

/**
 * @author myd
 * @date 2021/7/30  12:22
 */


public class ConTest {


    @Autowired(id = "getUser")
    User user;
    @Bean
    public void getUser(){

    }



}
