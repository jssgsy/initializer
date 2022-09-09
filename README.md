# initializer


# 多数据源
在目录组织上，一般将mapper与对应的xml文件存放在不同目录，因为要让不同的【数据库连接】去分别扫描；而service之类的就不用再区分了。
* springboot自身数据源(如HikariDataSource)的多数据源方案；
* druid数据源的多数据源方案；


# 动态数据源
* 见分支：learning/dynamicSource;
* 注：此分支仅仅用来记录动态数据源，为了方便，删除了很多数据源方面的文件，所以不要在此基础上新增内容；