# 底层是Ubuntu
FROM adoptopenjdk/openjdk8
RUN mkdir /app
WORKDIR /app

# 每个指令都会创建一层，所以能合并多个指令就合并
COPY .mvn/ mvnw pom.xml ./
#COPY mvnw pom.xml ./

# 安装常用工具vim与tree，这里使用shell模式(命令将在子shell中执行)
RUN apt update && \
    apt install -y vim && \
    apt install -y tree

# 把源码放到image中可能不是一个好方法，可在docker-compose中利用volume等实现
#COPY src ./src

# 这里用compose来管理了，所以注释掉
#CMD ["./mvnw", "spring-boot:run"]