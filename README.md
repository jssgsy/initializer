# initializer


# 多数据源
在目录组织上，一般将mapper与对应的xml文件存放在不同目录，因为要让不同的【数据库连接】去分别扫描；而service之类的就不用再区分了。
* springboot自身数据源(如HikariDataSource)的多数据源方案；
* druid数据源的多数据源方案；

# logback调用链
* LogTraceInterceptor.java
* ThreadPoolTaskExecutor及@Async等异步任务均支持调用链；