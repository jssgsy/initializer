# initializer


# 多数据源
在目录组织上，一般将mapper与对应的xml文件存放在不同目录，因为要让不同的【数据库连接】去分别扫描；而service之类的就不用再区分了。
* springboot自身数据源(如HikariDataSource)的多数据源方案；
* druid数据源的多数据源方案；

# logback调用链
* LogTraceInterceptor.java
* ThreadPoolTaskExecutor及@Async等异步任务均支持调用链；

# 事件
* EventConfig.java ;
* 默认异步监听处理了；
* @TransactionalEventListener：只有在发布事件的方法中没有db的事务回滚才会监听到消息；
    * 而@EventListener则只要事件发布就会监听到
  
# docker启动
除了常规启动外，也可使用docker方式启动
```
docker compose up
```
注：需新新建容器间通信的网络：
```
# dbnet:容器间通信的网络名
docker network create dbnet
```