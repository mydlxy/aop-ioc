package com.myd.ioc.beans;

import com.myd.ioc.exception.DuplicateException;
import com.myd.ioc.exception.RefNotFoundError;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.*;

/**
 * @author myd
 * @date 2021/7/30  20:31
 */

public class IocContainer {

    private static Map<String,Object> beans = new HashMap<>(64);
    private static volatile IocContainer container;
    protected IocContainer(){}
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

    /**
     *
     * 这个方法提供给BeanPostAfterInitProcessor使用，用来处理iocContainer中需要aop的类；
     * @return
     */
    protected Map<String,Object> getBeans()
    {
        return beans;
    }
    public Object getBean(String id){
        Object bean = beans.get(id);
//        if(bean == null)//ref引用判断
//            throw new NullPointerException("id: '"+id+"' is not defined in the container.");
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

        //如果是cglib生成的类，那么该类的superClass是原生类;
        for (Object value : values) {
            if(beanClass.isInstance(value))
                findClass.add(value);
        }
        if(findClass.size()==0)
            throw new NullPointerException(beanClass.getName()+" :is not defined in the container.");

        if(findClass.size()==1)
            return (T)findClass.get(0);

        throw new RuntimeException(beanClass.getName()+" :this classType has more than one ");

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
