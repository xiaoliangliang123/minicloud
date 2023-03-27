## 我们目前开源版本是1.0.0.0-beta版

> ***这个版本我只进行了基本测试，改了一些重大bug,大家如果遇见bug 记得提出issues,我会即时修复***
-----------------------
> ####  模块毕竟清晰，代码也很轻，很适合学习，也可以直接添加业务代码进行商务级别项目开发，也欢迎大家提交request,记得fork 后提交到develop分支

## 1.0.0.0-beta版 主要功能
### 当前版本涉及的组件和模块架构图
![在这里插入图片描述](https://img-blog.csdnimg.cn/748f8a678cfa48cba283f175a7ce955b.jpeg)

### 整体功能模块运行流程图
![在这里插入图片描述](https://img-blog.csdnimg.cn/22e5f8af05fa4d1fab8c68a1fa23bf0c.jpeg)


>  ***第一个版本主要是为了应用于大家的微服务学习，也可以适用微服务项目的基本使用
> 所有的源码思路以及解读在专栏[《带大家从0~1搭建saas微服务框架》](https://blog.csdn.net/madness1010/category_11701564.html)都有说明与源码讲解***
------------------
### 1.0.0.0-beta 主要包含服务和模块

|功能|说明  |
|--|--|
|  support 组件支持服务 |  |
|  分布式文件服务 |  |
|  认证中心服务 |  |
| 分布式事物服务 |  |
| 网关服务 |  |
|  注册中心服务 |  |
|  用户权限管理服务  |功能模块如下：  |
|  用户管理模块 |  |
|  部门管理|  |
|  角色管理 |  |
|  部门tag管理 |  |
| 租户管理 |  |
| 内嵌swagger  |  |

| 权限管理等 |等等  |
| simulate 模拟一些功能场景的demo |目前有商品服务，订单服务  |


> ***虽然功能少，但是基本上，有了这些基本功能，微服务项目已经可以进行正常业务开发了***
------------------
## 源码启动前准备部分

> ***需要jdk配置，导入数据库以及启动redis,如下***

### jdk

> ***jdk需要1.8以及以上***

## 数据库sql部分介绍
### mysql 或mariadb 版本要求

>  ***mysql:5.7+
>   mariadb10.8.4+***
### 数据库文件
>  ***/根目录/readme-center/sql下  
>  首先执行schema.sql中的语句
>   执行完后，将其他的sql分别导入进去***

## redis 启动部分

>  ***/根目录/readme-center/redis下  解压后进入redis 目录
>  cmd下执行命令：redis-server redis.windows.conf***
----------------------
### 项目地址，目录结构以及启动介绍
#### 源码分为四个项目，
#### 分别为：
#####  后端 minicloud 共通项目
> ***地址：[https://github.com/xiaoliangliang123/miniclou](https://github.com/xiaoliangliang123/minicloud)
>  注意使用tag 1.0.0.0-beta版本***

##### 后端 minicloud-tenant 多租户项目
> ***地址：[https://github.com/xiaoliangliang123/minicloud-tenant.git](https://github.com/xiaoliangliang123/minicloud-tenant.git)
>  注意使用tag 1.0.0.0-beta版本******


##### 后端 minicloud-seata 分布式事物项目

> ***地址：[https://github.com/xiaoliangliang123/minicloud-seata.git](https://github.com/xiaoliangliang123/minicloud-seata.git)***
***注意使用tag 1.0.0.0-beta版本***




##### 前端 minicloud-manager-vue3  vue3+element plus 前端项目
>  ***地址：[https://github.com/xiaoliangliang123/minicloud-manager-vue3.git](https://github.com/xiaoliangliang123/minicloud-manager-vue3.git) ***
>  注意使用tag 1.0.0.0-beta版本***

> ***注意1: 几个后端本地repo都要配置在同一个maven repo目录下 注意2：全部都在本地本机开发时几个后端项目启动jvm参数最好设置为 -Xms256M
> -Xmx256M,当然了，如果本机内存很高当我没说~***
---------------

## minicloud 共通项目启动以及目录介绍
#### 目录结构如下：

> ├─mini-cloud-authentication-center 认证中心服务
├─mini-cloud-common 共通模块
│  ├─mini-cloud-common-auth 认证共通
│  ├─mini-cloud-common-balancer 负载均衡共通
│  ├─mini-cloud-common-cache 缓存共通
│  ├─mini-cloud-common-constant 常量共通
│  ├─mini-cloud-common-core 核心源码共通
│  ├─mini-cloud-common-data 常用类共通
│  ├─mini-cloud-common-dependencies 独立依赖共通
│  ├─mini-cloud-common-feign feign 共通
│  ├─mini-cloud-common-log 日志入参出参共通
│  ├─mini-cloud-common-log4j2 集成log4j2共通
│  ├─mini-cloud-common-rocketmq 集成rocket共通
│  └─mini-cloud-common-swagger 集成swagger共通
├─mini-cloud-component 组件
│  └─sentinel sentinel 启动包
├─mini-cloud-gateaway-center 独立网关服务
├─mini-cloud-register-center 注册中心服务
├─mini-cloud-simulate 业务场景模拟服务
│  ├─mini-cloud-simulate-goods 商品服务
│  └─mini-cloud-simulate-order 订单服务
├─mini-cloud-support  支撑服务目录，负责放入一些支撑服务
│  ├─mini-cloud-files 分布式文件服务，启动即可
│  └─mini-cloud-log-consumer 日志消费者服务
├─mini-cloud-upms-center 用户权限服务
└─readme-center 资源目录
└─resources
├─imgs 图片
├─nacos-jars jar版本nacos
├─redis redis 缓存
├─sentinel sentinel.jar
└─sql sql 文件
---------------
### minicloud项目启动
#### 项目需要启动的服务如下：
> ##### 1.mini-cloud-register-center 注册中心服务
> ##### 2.mini-cloud-authentication-center 认证中心服务
> ##### 注：需要minicloud-seata分布式服务启动后再启动
> ##### 3.mini-cloud-gateaway-center 独立网关服务
> ##### 注：需要minicloud-seata分布式服务启动后再启动
> ##### 4.mini-cloud-upms-center 用户权限服务
> ##### 注：需要minicloud-seata分布式服务启动后再启动
-------------
## mini-cloud-tenant saas多租户项目启动介绍以及目录介绍
### 目录结构
> ├─mini-cloud-busi-tenant-1-login 租户1 登录模块
├─mini-cloud-busi-tenant-2-login 租户2 登录模块
└─mini-cloud-core-processer 模拟业务端rocketmq消费服务模块

> ***目前仅建立了2个租户登录模块，如果项目中随着租户增加，慢慢创建即可***
> #### 可参考[《从0到1带大家搭建spring cloud alibaba 微服务大型应用框架（十七） saas多租户实现-后台集成篇：如何升级为saas 平台, 后台租户层集层讲解和完整源码》](https://minicloud.blog.csdn.net/article/details/129520670)
> ***[《从0到1带大家搭建spring cloud alibaba 微服务大型应用框架（十七） saas租户集成-前端篇：vue3 + element
> plus前端集成多租户登录功能》](https://minicloud.blog.csdn.net/article/details/129547184)***

-------------------------
## mini-cloud-seata 分布式事物源码启动介绍

> ***seata 我们用的是at 模式，既无侵入模式，大家也可以选择别的模式***

### mini-cloud-seata 启动

> ***打开server 模块,启动io.seata.server 类即可
> 注意：需要minicloud的共通中的register服务启动后再启动***
---------------
## minicloud-manager-vue3 分布式事物源码启动介绍
### 基于vue3+element plus
> ***nodejs版本需要v14版本以上，我这里用的是v16.14.0  npm源可以配置为taobao
> 命令:npm config set registry https://registry.npm.taobao.org***