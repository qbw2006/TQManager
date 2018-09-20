# TQManager:基于Web的Redis管理工具
### 主要功能
1. 监控redis-server是否在线
2. 启动redis-server
3. 关闭redis-server
4. 查看redis info命令返回的信息


主界面如下：
![image](https://github.com/qbw2006/Resources/blob/master/TQManager/web-ui.png)

### 使用技术
#### 前端
  vue-cli </br>
  webpack </br>
  element-ui </br>
  axios </br>
  tomcat

#### 后端
  springboot </br>
  redis </br>
  logback 
  gradle

### 部署
**要求** </br>
  JDK 1.8+ </br>
  tomcat 8.0 +

#### 前端
1. 下载tq-web代码后，修改main.js
```
const host = process.env.NODE_ENV === "development" ? "" : "后端服务器部署的地址，例如：http://10.1.6.37:8088";
```

2. 在tq-web目录下运行npm run build或者cnpm run build打包，生成dist目录

3. 将dist目录拷贝到tomcat下(假设已经成功地部署了tomcat)

#### 后端
1. 下载tq-server代码，在tq-server目录下运行gradle distTar

2. 复制打好的tar包到服务器

3. 修改conf目录下的配置文件
tq-server使用redis保存数据，所以需要在manage.properties配置redis信息，配置参考spring-boot中redis配置方法。

4. 运行./bin/tq-server start


### 功能简介

#### 添加服务器
![image](https://github.com/qbw2006/Resources/blob/master/TQManager/add-server.png)

**Redis配置**：包括redis相关信息。

1. 名称：此服务器的名字
2. RedisIP：此redis的ip地址
3. Redis端口：此redis的端口
4. Redis密码：此redis的密码
5. Redis部署模式：此redis的部署方式,可以填写cluster，sentinel，standalone。目前没有对该字段限制，所以可以随便填写。

**服务器配置**：包括部署redis主机的信息，这部分信息主要用来实现 *启动*和*停止*功能。
1. 服务器用户名：部署redis主机的登录用户名，例如root。
2. 服务器密码：服务器用户名对应的密码。
3. Redis程序路径：redis-server在此服务器的绝对路径，例如/usr/local/bin/redis/bin/redis-server，一定要是redis-server的**绝对路径**。
4. Redis配置路径：redis配置文件在此服务器的绝对路径，例如/usr/local/bin/redis/bin/redis.conf，一定要是配置文件的**绝对路径**。

完成配置后，点击**创建**按钮。

**注意：刚创建的配置信息不会立即显示，需要等待10秒钟**
