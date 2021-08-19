# aop-ioc
自定义一个简单的ioc容器

## xml解析
在ParseXml中解析xml文件<br>
调用方法：ParseXml.parseXml(path);<br>
返回XmlConfiguration对象；<br>
### 定义关键对象：
- 1.NodeName ：定义 xml支持的标签<br>
- 2.XmlConfiguration：存放整个xml的信息<br>
- 3.BeanDefinition：存放bean标签信息<br>
- 4.DefaultBeanFactory：根据BeanDefinition生成bean<br>
- 5.AnnotationBeanFactory：根据xml中ComponentScan标签定义的扫描包，扫描@Component注解，将带有@Component的class注入到容器<br>
- 6.@Autowired，类型自动注入；<br>
- 7.@Value ：8种基础类型，包装类，String的值注入；<br>
- 8.IocContainer：存放生成的bean<br>
### 主要逻辑

解析xml 生成 XmlConfiguration对象
--> 从XmlConfiguration中获取到BeanDefinition信息生成bean（先将所有BeanDefinition生成bean，最后将ref值注入到bean）
-->判断是否有 ComponentScan标签？有，则去扫描对应路径下的@Component，将其注入到容器

## 测试
测试类：TestIOC</br>
<hr>

##aop

有几个比较重要的类：
- AspectConfig 存储aspect信息:切面 + 各种通知advice；在设置pointcut值时首先检测pointcut是否正确；
- ClassFilter：匹配需要代理的对象
- MethodFilter：匹配需要代理的方法
- CglibProxy，JDKProxy:生成代理对象；
<hr>

## 相关博客
[pointcut正则表达式](https://blog.csdn.net/m0_37550986/article/details/119384659?spm=1001.2014.3001.5501)</br>
[cglib注解丢失问题](https://blog.csdn.net/m0_37550986/article/details/119585988)

