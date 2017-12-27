
# SpringBoot项目的DEMO

## 使用Minio进行文件上传和下载
![Minio](http://oo5aasoph.bkt.clouddn.com/minio%E4%B8%BB%E7%95%8C%E9%9D%A2.png)

**Minio是一款跨平台的可「自建」轻量级对象存储服务，只需要一个命令，就能实现可以通过浏览器访问的简易网盘功能。支持本地储存和 AWS S3，最大存储对象 5TB。提供 API 与 SDK，可以和很多服务整合，甚至可以整合到 Android 与 iOS 应用中，使用 Apache License 2.0 协议发布。**

[Github地址](https://github.com/minio)

[Minio文件系统安装与服务化](https://vip-augus.github.io/2017/12/25/Minio%E6%96%87%E4%BB%B6%E7%B3%BB%E7%BB%9F%E5%AE%89%E8%A3%85%E4%B8%8E%E6%9C%8D%E5%8A%A1%E5%8C%96/)

实现对象池GenericObjectPool管理MiniClient对象，具体配置在spring-file-minio.xml中

```xml
    <!-- 创建MinioClient工厂 -->
    <bean id="minioClientPooledFactory" class="com.example.demo.util.file.MinioClientPooledFactory">
        <property name="ip" value="${minio.ip}"/>
        <property name="accessKeyId" value="${minio.accessKey}"/>
        <property name="accessKeySecret" value="${minio.secretKey}"/>
    </bean>

    <!-- 对象池参数设置 -->
    <bean id="minioGenericObjectPoolConfig" class="org.apache.commons.pool2.impl.GenericObjectPoolConfig">
        <property name="maxTotal" value="8"/>
        <property name="maxIdle" value="8"/>
        <property name="minIdle" value="0"/>
        <!-- 取消JMX的激活 -->
        <property name="jmxEnabled" value="false"/>
    </bean>

    <!-- 对象池 -->
    <bean id="minioGenericObjectPool" class="org.apache.commons.pool2.impl.GenericObjectPool">
        <constructor-arg index="0" ref="minioClientPooledFactory"/>
        <constructor-arg index="1" ref="minioGenericObjectPoolConfig"/>
    </bean>
```

具体使用可以进行注入
```java
@Resource(name = "minioGenericObjectPool")
private GenericObjectPool<MinioClient> genericObjectPool;
```
## 使用SLF4J实现日志服务

SpringBoot中内部自带的日志框架**LogBack**，Logback是log4j框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时支持SLF4J。

具体配置在 **/resources/logging-config.xml**

同时要在application.properties中加入
```
#日志输出配置
logging.config=classpath:logging-config.xml
```

**1、配置日志持久化输出路径**

```xml
<property name="log.path" value="/logs/class" />
```

**2、配置ROOT节点，指定最基础的日志输出级别**
```xml
<root level="info">
    <appender-ref ref="console" />
    <appender-ref ref="file" />
</root>
```

