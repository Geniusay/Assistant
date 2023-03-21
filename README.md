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

### 1，StringUtils

> 集成了更多的操作字符串的方法

### 2，AutoApiJsGenerateHelper

> 根据项目所拥有的controller类，一键生成前端接口js文件，大大减少了前端后端 Api.js文件的编写和比对，目前适配RestFul风格请求，get请求，post请求

**使用方法：**

```java
autoApiJsGenerateHelper.setAxiosPath("axios");	//前端请求发送文件路径
autoApiJsGenerateHelper.setFileSavePath("*****");	//js生成文件保存路径
autoApiJsGenerateHelper.generate();
```

```java
//项目入口加上
@ComponentScan(basePackages = {"com.genius.assistant"});
```

**之后更新:**
- [ ] 根据开发需要，可以更换js生成模板
- [ ] 读取配置文件完成加载
- [x] 可以选择controller来生成js方法
- [ ] 更加全面的js文件生成
- [ ] 可以为生成的js文件添加注释
- [x] 异步

### 3，TokenUtil token工具
具备：生成Token，解析Token，刷新Token，验证Token，预定Token生效时间功能
