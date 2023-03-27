##1.创建authentication-center 服务 ✔
##2.讲authentication-center 服务集成入spring cloud nacos注册中心 ✔
##3.选择oauth2 认证模式，分离认证服务器以及资源服务器，设计下沉式资源端认证模式 ✔
##4.按照spring 官方内存式认证demo 搭建oauth2 client 认证 ✔
##5.添加测试服务module 并集成如spring cloud nacos 中 ✔
##6.测试服务 作为resource server 进行认证测试 ✔
##7.将配置文件参数纳入nacos 配置中心进行管理 ✔
##8.将内存式认证改为实际redis 以及clientdetail ,userdetail 为数据库中获取 ✔
##9.将demo 权限认证改为根据当前登陆用户角色动态校验权限 ✔
##10.抽取认证服务器与资源服务器共通部分变为common module 
##11.应用common 模块重构代码再次校验认证
###11.1 为什么需要重构抽取出common 模块
###11.2 重构名字，统一变为mini-cloud-xxx
###11.3 重构redis 添加依赖 common-cache
###11.4 重构oauth2.0权限auth-server 与 resources-server 添加依赖 common-oauth2
###11.5 重构常量 添加依赖 common-contanst
###11.6 重构数据库相关依赖 添加common-database
###11.7 重构fegin相关依赖 添加common-database


