package parse.anno.component;

import com.myd.ioc.annotations.Autowired;
import com.myd.ioc.annotations.Component;
import com.myd.ioc.annotations.Value;
import parse.model.User;

/**
 * @author myd
 * @date 2021/8/1  14:45
 */

@Component("mmm")
public class Model {

    @Autowired
    private User user;

    @Value("modelName")
    private String name;

    @Value("MODEL-FUCK")
    private String modelType;


    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", modelType='" + modelType + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getModelType() {
        return modelType;
    }
}
