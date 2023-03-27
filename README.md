# 一.整体结构介绍（目前为止，待更新）
* mini-cloud
* --mini-cloud-authentication-center 认证中心
* --mini-cloud-gateaway-center 网关中心
* --readme-center 所需资源
* --mini-cloud-register-center nacos 注册中心(源码启动)
* --mini-cloud-upms-center 用户统一权限管理中心
* --mini-cloud-simulate 模拟业务中心
*   --mini-cloud-simulate-goods 模拟商品服务
*   --mini-cloud-simulate-order 模拟订单服务

#　二.数据库部署
## 2.1 安装 mariadb数据库 https://mariadb.org/mariadb/all-releases/
## 2.2 执行readme-center/sql/schema.sql 安装数据库

# 三.redis单机/集群部署
## 3.1 linux 集群部署请参考我之前文章 [docker-compose 搭建单机版/多机版 redis sentinel 哨兵集群](https://blog.csdn.net/madness1010/article/details/122560596)
## 3.2 单机windows 部署可直接使用readne-center/resources/redus/Redis-x64-3.2.100.zip ,解压后进入目录 启动命令：redis-server redis.windows.conf  密码123456




# 三.启动流程
## 3.1 先启动register-center
## 3.2 再启动gateaway-center
## 3.3 其他项目无顺序


# 四.seata-server 源码版 （如果需要开启seata 分布式事务，关于seata at模式配置请自行查考文档）
## 4.1 启动seata 
### 4.1.1 下载并导入 seata-server 源码，我重构后的版本是1.4.0 ，地址:https://github.com/xiaoliangliang123/mini-cloud-seata-1.4.0
### 4.1.2 启动 /src/main/java/io/seata/server/Server.java 
## 4.1 开始分布式事务 项目对应的src/resources/seata.conf transaction.seata.at.enable 设置为true