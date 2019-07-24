# myFind
## 1、项目描述
基于Java语言，模仿Linux下的find命令，实现windows下命令行的查找文件的功能，由于是Java语言开发，所以也具有跨平台性
简单来说，就是
## 2、项目背景
在Linux下的find命令很好用，但是在windows下查找文件却不是那么方便，在windows界面查找速度非常慢而且还不能跨盘查找，因此开发这款命令行的查找工具，可以在windows下快速全盘查找。
## 3、做这个项目需要准备知识

- JavaSE基础（文件操作）
- Database（嵌入式H2数据库或MySQL）
- JDBC
- Lombok库（IDEA工具安装Lombok插件）
- Java多线程技术
- 文件系统监控（Apache Commons IO）
- pinyin4j （支持拼音搜索）

## 4、项目结构图

项目采用分层结构，如下图：

![1563950162789](C:\Users\ASUS\AppData\Roaming\Typora\typora-user-images\1563950162789.png)

## 5、各个模块简单说明

#### 5.1 命令行交互

命令行交互就是进行简单的字符串处理，通过用户输入的字符串，来判断用户具体想干什么事，然后调用对应的控制层方法

#### 5.2 控制层

