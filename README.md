# Assisant



##  介绍

  一个基于SpringBoot框架的后端开发工具。简便后端开发，提供多种高效率高性能的工具类，包装各种web开发中所需要的功能，作为你后端开发的小帮手。

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

- [ ] 根据开发需要，可以更换js生成模板
- [ ] 读取配置文件完成加载
- [ ] 可以选择controller来生成js方法
- [ ] 更加全面的js文件生成
- [ ] 可以为生成的js文件添加注释