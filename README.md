# tqManager:基于Web的Redis管理工具
### 主要功能
1. 监控redis-server是否在线
2. 启动redis-server
3. 关闭redis-server
4. 查看redis info命令返回的信息


主界面如下：
![image](https://github.com/qbw2006/Resources/blob/master/TQManager/web-ui.png)


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
3. Redis程序路径：redis-server在此服务器的绝对路径，例如/usr/local/bin/redis/bin/redis-server，一定要是redis-server的*绝对路径*。
4. Redis配置路径：redis配置文件在此服务器的绝对路径，例如/usr/local/bin/redis/bin/redis.conf，一定要是配置文件的*绝对路径*。

完成配置后，点击**创建**按钮。

*注意：刚创建的配置信息不会立即显示，需要等待10秒钟*
