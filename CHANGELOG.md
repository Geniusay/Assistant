# CHANGELOG
```markdown
   格式：
    ## [版本号] - 日期
    ### 模板名称 (可选 console-ui, console, FileModule, common, ...)
    - 🎈新增: {模块名称} {功能介绍}
    - 🐞Bug: #{issue号} {bug描述}
    - ⛏修复: #{issue号} {修复描述}
    - 📝文档: {文件名} 添加注释
    - 🚀性能: {类} {方法} {描述}
    - 🎨样式: 
    - 🧹重构:
    - 🧪测试: {类|方法} {测试结果}
    - 🛑更名: {旧名} ➡ {新名}
    - ❌移除: {模块|方法}

    ------

```
------
## [v1.0.9] - 2023.4.28
- 🧹重构:重构整个框架，目前分为了util模块,service模块,generator模块,common模块
- 🎈新增:`JsonFileUtil,FileUtil`,用于进行文件操作和Json文件读取与写入
------
## [v1.0.7] - 2023.4.4
- 🎈新增`AutoChecker`自动检查器，用于检查参数是否符合要求
- 定义了`BaseServiceChain` 与 `ServiceNode`,为之后的业务流以及业务链提供基础
------
## [v1.0.6] - 2023.4.4
- ⛏bug:修复`TokenUtils`某些函数未进行空值处理判断
- 完善`TokenUtils`工具类，`Token`类现在包含`Header`了
- 加密工厂类`EncryptFactory`新增`add`方法，可以添加用户自定义的加密解密工具类
------
## [v1.0.5] - 2023.4.1
- 📌bug:修复`AutoApiJsGenerate`在SpringBoot2.6.4版本下路径映射改变问题，添加了自适应版本路径获取方法
------
## [v1.0.4] - 2023.3.31
- 🎈新增`RegexUtils`工具类，用于进行正则表达式匹配功能
- 🎈新增`RegexPattern`正则表达式常量类
------
## [v1.0.3] - 2023.3.24
- 🎈新增`PathUtils`工具类，用于进行路径解析功能
- 🎈新增`FileUtils`工具类，用于进行文件操作功能
- 🎈新增`EncryptUtils`工具类，用于进行加密解密功能，目前具有AES,RSA,BASE64,MD5等加密方法
- 🎈新增加密工厂类`EncryptFactory`，用于获取加密解密工具类
------
## [v1.0.2] - 2023.3.23
- 热身模块ApiJs生成工具大更新，支持多种模板生成方式，更名为 `AutoApiJsGenerate`
- 🎈新增文件信息类
- 🎈新增 `AutoGenerate` 接口
- 🎈新增 `AutoApiJsGenerate` 自定义模板生成，异步生成，过滤器
------
## [v1.0.1] - 2023.3.22
- 📌bug:修复 `AutoApiJsGenerateHelper` 模块的对于RestMapping多个请求方式会出现解析出错的问题
- 🎈新增 `TokenUtil工具类`,用于生成token和解析token
------
## [v1.0.0] - 2023.3.20
- 项目发布到Maven仓库
- 热身模块新增 `AutoApiJsGenerateHelper` 模块
------
