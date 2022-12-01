FROM adoptopenjdk/openjdk8
RUN mkdir /app
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#RUN ./mvnw dependency:resolve

# 把源码放到image中可能不是一个好方法，可利用volume等实现
#COPY src ./src

# 这里用compose来管理了，所以注释掉
#CMD ["./mvnw", "spring-boot:run"]