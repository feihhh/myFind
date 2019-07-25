# myFind
## 1、项目描述
基于Java语言，模仿Linux下的find命令，实现windows下命令行的查找文件的功能，由于是Java语言开发，所以也具有跨平台性
简单来说，就是
## 2、项目背景
在Linux下的find命令很好用，但是在windows下查找文件却不是那么方便，在windows界面查找速度非常慢而且还不能跨盘查找，因此开发这款命令行的查找工具，可以在windows下快速全盘查找。
## 3、使用到的技术

- JavaSE基础（文件操作）
- Database（嵌入式H2数据库或MySQL）
- JDBC
- Lombok库（IDEA工具安装Lombok插件）
- Java多线程技术
- 文件系统监控（Apache Commons IO）
- pinyin4j （支持拼音搜索）
- 设计模式（单例模式）

## 4、项目结构图

项目采用分层结构，如下图：

![image](https://github.com/feihhh/myFind/raw/master/structure.png)


## 5、各个模块简单说明

#### 5.1 模型

enum FileType    文件类型

class  Condition    检索文件的条件的模型类

class Thing 存入数据库的一行

#### 5.2 数据库操作

1） 数据源  通过单例模式，获取数据源

2）使用JDBC对数据库中的文件进行增、删、查

#### 5.3业务层操作

**1）创建索引**

- 通过递归的方式遍历一个文件夹下的所有文件
- 将一个电脑上的文件类型转化为数据库中的一个Thing（将文件信息转换为一个Thing的对象类型）
- 判断文件是否是要处理的目录（定义了默认要查找的目录和默认忽略的目录，像C:\windows 之类的系统文件就可以忽略）
- 通过FileSystems.getDefault().getRootDirectories(); 来获取次电脑上有哪些盘（C:\\\  D:\\\   E:\\\）
- 电脑有几个盘，就创建几个线程，每个线程遍历一个盘
- 用CountDownLatch来控制多线程，等几个线程都执行完之后提示用户，索引创建完毕

**2）查找文件**

- 通过数据库访问层的查询的方法



#### 5.2 控制层

