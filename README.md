# elsfs

#### 介绍
Enterprise level safety function system
企业级安全功能系统

#### 系统说明
- 基于 Spring Cloud 2022、Spring Boot 3.0、 spring-oauth2-authorization-server的 RBAC 权限管理系统
. 基于数据驱动视图的理念封装 ant-design，即使没有 vue 的使用经验也能快速上手
. 提供对常见容器化支持 Docker、Kubernetes、Rancher2 支持（待实现）
. 

#### 核心依赖
| 依赖                         | 版本      |
|-----------------------------|----------|
| Spring Cloud                | 2022.0.x |
| Spring Cloud Alibaba        | 2022.0.x |
| Spring Authorization Server | 1.1.x    |
| Mybatis Plus	               | 3.5.3.1  |
| Spring Boot                 | 3.0.x    |



#### 模块说明
```lua
elsfs
├── .workflow gitee -- 工作流
└── base -- 系统公共基础模块
     ├── elsfs-base-annotation -- 系统公共注解
     ├── elsfs-base-lang -- 系统公共接口
└── core  -- 系统核心
     ├── elsfs-core-events -- 事件接口
     ├── elsfs-core-util -- 系统工具包
└── core-api  -- 系统核心
     ├── elsfs-core-dto-api -- 事件驱动基础接口
└── example  -- 例子
     └── oauth2 -- oauth2相关例子
         ├── custom-consent-authorizationserver -- oauth自定义授权的授权服务器实例
         ├── default-authorizationserver -- oauth默认的授权服务器实例
         ├── messages-client -- oauth 客户端实例
         ├── messages-resource -- oauth 资源服务器实例
├── framework-bom -- 项目bom
├── framework-doc -- 项目文档
├── framework-platform -- 项目依赖
├── gradle -- gradle脚本和gradlew
└── security  -- security相关
     ├── elsfs-security-core -- security相关核心
     ├── elsfs-security-core-api -- security相关核心扩展
     ├── elsfs-security-util-- security 工具
└── webapp  -- 启动相关
     └── admin -- 后台启动相关
         ├── elsfs-admin-webapp -- 后台项目管理
├── .gitignore  -- git忽略配置
├── .springjavaformatconfig  -- spring format配置
├── build.gradle  -- gradle 项目配置
├── gradle.properties  -- gradle 运行参数配置
├── gradlew[.bat]  -- gradlew 运行命令
├── lombok.config  -- lombok 配置
├── settings.gradle  -- gradle 配置

```

#### 使用说明
1.  clone 项目

| 平台     | 地址                             |
|--------|--------------------------------|
| gitee  | https://gitee.com/elsfs/elsfs  |
| github | https://github.com/elsfs/elsfs |
2. 构建项目

```shell
cd elsfs
./gradlew # or gradle
 # 代码格式化
./gradlew format # or gradle format 
   # 编译跳过 测试 跳过测试是因为 例子项目测试有有相互依赖
./gradlew build -x test # or gradle build -x test

```


#### 参与贡献
1. 欢迎提交 [PR](https://gitee.com/elsfs/elsfs/pulls)，
   代码规范 [spring-javaformat](https://github.com/spring-io/spring-javaformat)
   <details>
    <summary>代码规范说明</summary>

    1. 由于 <a href="https://github.com/spring-io/spring-javaformat" target="_blank">spring-javaformat</a>
       强制所有代码按照指定格式排版，未按此要求提交的代码将不能通过合并（打包）
    2. 如果使用 IntelliJ IDEA
       开发，请安装自动格式化软件 <a href="https://repo1.maven.org/maven2/io/spring/javaformat/spring-javaformat-intellij-idea-plugin/" target="_blank">
       spring-javaformat-intellij-idea-plugin</a>
    3. 其他开发工具，请参考 <a href="https://github.com/spring-io/spring-javaformat" target="_blank">spring-javaformat</a>
       说明，或`提交代码前`在项目根目录运行下列命令（需要开发者电脑支持`gradle(w)`命令）进行代码格式化
       ```shell
       ./gradlew format 
       # or
       gradle format
       ```
   </details>

2. 欢迎提交 [issue](https://gitee.com/elsfs/elsfs/issues)，请写清楚遇到问题的原因、开发环境、复显步骤。

3. 联系作者 <a href="mailto:maicaii@vip.qq.com">mailto:maicaii@vip.qq.com</a>

