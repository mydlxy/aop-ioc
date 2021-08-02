package com.myd.ioc.beans;

import com.myd.ioc.exception.DuplicateException;
import com.myd.ioc.exception.RefNotFoundError;

import java.util.*;

/**
 * @author myd
 * @date 2021/7/30  20:31
 */

public class IocContainer {

    private Map<String,Object> beans = new HashMap<>(64);
    private static volatile IocContainer container;
    private IocContainer(){}
    public static IocContainer container(){
        if(container == null){
            synchronized (IocContainer.class){
                if(container == null){
                    container = new IocContainer();
                }
            }
        }
        return container;
    }


    public void registerBean(String id ,Object bean){
        if(beans.containsKey(id))
            throw new DuplicateException("id:"+id+" already exists");
        beans.put(id,bean);
    }

    public Object getBean(String id){
        Object bean = beans.get(id);
        if(bean == null)
            throw new NullPointerException("id: '"+id+"' is not defined in the container.");
        return beans.get(id);
    }


    /**
     *
     *
     * 根据class类型返回对象
     * 如果有该class的对象有多个，抛异常
     * @param beanClass
     * @param <T>
     * @return
     */
    public <T>T getBean(Class beanClass){

        Collection<Object> values = beans.values();

        List<Object> findClass  = new ArrayList<>();
        for (Object value : values) {
            if(value.getClass().equals(beanClass))findClass.add(value);
        }
        if(findClass.size()==0)
            throw new NullPointerException("class type: "+beanClass.getName()+" is not defined in the container.");

        if(findClass.size()==1)
            return (T)findClass.get(0);

        throw new RuntimeException("this classType has more than one ");

    }


    /**
     *
     *判断id是否注册过,提供给外部类使用
     * @param id
     * @return
     */
    public boolean isRegister(String id){
        return beans.containsKey(id);
    }


}
