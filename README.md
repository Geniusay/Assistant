# Assisant



##  介绍

  一个基于SpringBoot框架的后端开发工具。简便后端开发，提供多种高效率高性能的工具类，包装各种web开发中所需要的功能，作为你后端开发的小帮手。

##  安装使用
```xml
<dependency>
    <groupId>io.github.969025903</groupId>
    <artifactId>assistant</artifactId>
    <version>1.0.2</version>
</dependency>
<!--> 该版本支持Java11及以上 <-->
```
如果出现无法导入的情况，请打开您的maven配置文件(setting.xml文件)，添加一个官方镜像
```xml
<mirror>
    <id>nexus</id>
    <mirrorOf>internal nexus repository</mirrorOf>
    <url>https://repo1.maven.org/maven2</url>
    <mirrorOf>central</mirrorOf>
</mirror>

```
## 目前拥有的工具类
//使用方法参考test文件夹中的各个工具对应的test类，有详细说明
### 1，StringUtils

> 集成了更多的操作字符串的方法

### 2，TokenUtil token工具
具备：生成Token，解析Token，刷新Token，验证Token，预定Token生效时间功能

### 3，PathUtil工具
路径生成相关，拥有一个smartFilePath方法，可以自动拼接路径，并且根据电脑系统来更改路径，具有一定的纠错功能，方法可以继续优化

### 4，FileUtil工具
功能不是很齐全，待完善

### 5，JdbcUtil工具
获取建表语句，表的元信息，表结构等等，主要是用来辅助 数据库表To类的自动生成的，但是写到一半才想起来mybatisplus有这功能了，而且他的模板引擎更完善，于是今后打算把我自己的于mybatisplus的整合在一起使用

### 5，AutoApiJsGenerateHelper

> 根据项目所拥有的controller类，一键生成前端接口js文件，大大减少了前端后端 Api.js文件的编写和比对，目前适配RestFul风格请求，get请求，post请求，目前可以自定义生成模板，满足你的生成需求

**使用方法：**

```java
autoApiJsGenerate.
                setIsJsMoodleGenerated(true).               //是否使用传统Js模板
                filter(List.of(TestController.class)).      //过滤掉不需要生成js文件的controller
                setFileSavePath("E:\\Project\\Assistant\\src\\main\\resources\\js"). //文件保存区域
                generate(true); //是否异步生成
                
autoApiJsGenerate
                .filter(List.of(TestController.class))
                .setTemplatePath("src/main/resources/templates/")//选择模板加载路径
                .setTemplateName("apiJs.java.vm")   //选择模板名字
                .setFileSavePath("E:\\Project\\Assistant\\src\\main\\resources\\js")
                .generate(true); //是否异步生成
                
```

```java
//项目入口加上
@ComponentScan(basePackages = {"com.genius.assistant"});
```

**之后更新:**
- [x] 根据开发需要，可以更换js生成模板
- [ ] 读取配置文件完成加载
- [x] 可以选择controller来生成js方法
- [x] 更加全面的js文件生成
- [ ] 可以为生成的js文件添加注释
- [x] 异步


