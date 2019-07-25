# everything
## <font color=green>1、项目描述</font>

基于Java语言，模仿Linux下的find命令，实现windows下命令行的查找文件的功能，由于是Java语言开发，所以也具有跨平台性

## <font color=green>2、项目背景</font>
在Linux下的find命令很好用，但是在windows下查找文件却不是那么方便，在windows界面查找速度非常慢而且还不能跨盘查找，因此开发这款命令行的查找工具，可以在windows下快速全盘查找。
## <font color=green>3、使用到的技术</font>

- JavaSE基础（文件操作）
- Database（嵌入式H2数据库或MySQL）
- JDBC
- Lombok库（IDEA工具安装Lombok插件）
- Java多线程技术
- 文件系统监控（Apache Commons IO）
- pinyin4j （支持拼音搜索）
- 设计模式（单例模式）

## <font color=green>4、项目结构图</font>

项目采用分层结构，如下图：

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/structure.png)


## <font color=green>5、各个模块简单说明</font>

#### <font color = "orange">5.1 模型</font>

enum FileType    文件类型

class  Condition    检索文件的条件的模型类

class Thing 存入数据库的一行

#### <font color = orange>5.2 数据库操作</font>

1） 数据源  通过单例模式，获取数据源

2）使用JDBC对数据库中的文件进行增、删、查

#### <font color = orange>5.3业务层操作 </font>

**1）创建索引**

- 通过递归的方式遍历一个文件夹下的所有文件
- 将一个电脑上的文件类型转化为数据库中的一个Thing（将文件信息转换为一个Thing的对象类型）
- 判断文件是否是要处理的目录（定义了默认要查找的目录和默认忽略的目录，像C:\windows 之类的系统文件就可以忽略）
- 通过FileSystems.getDefault().getRootDirectories(); 来获取次电脑上有哪些盘（C:\\\  D:\\\   E:\\\）
- 电脑有几个盘，就创建几个线程，每个线程遍历一个盘
- 用CountDownLatch来控制多线程，等几个线程都执行完之后提示用户，索引创建完毕

**2）查找文件**

- 通过查询条件，调用数据库的查询方法，获取查询结果
- 判断查询结果中的文件是否还存在，如果出查询出来的文件在电脑上已经被删除了，就把该文件从查询结果集中删除，同时，把它放在一个后台清理的队列中，在后台删除数据库中不存在的文件（因为从结果集中删除一个元素是CPU操作，而从数据库中删除是IO操作，CPU操作比IO操作快的多，所以这里把它放在后台删除，就像生产者，消费者模型）

**3）文件监听**

- JDK7中提供的WatchService也可以实现系统文件的监控，但是由于这个使用方法只能对一个目录下的文件进行监控，而不能对目录下的子目录继续监控，所以没有使用这个方法。
- 通过使用第三方jar包commons-io 中的类和方法进行文件监控。
- 自己写一个类 继承**FileAlterationListenerAdaptor**类，覆写里面的部分方法（文件创建、文件删除、目录创建、目录删除）,用来对系统文件进行监听（其中FileAlterationMonitor实现了Runnable接口，因此是自己在后台进行监听），在程序启动的时候就调用文件监听的方法在后台监听
- 将监听到的文件变化（增加、删除）信息写入到一个txt文件中，同时，如果有文件新增，就将新增的文件写入数据库，如果文件删除，就删除数据库中对应的文件

**4）拦截器**

因为在遍历系统文件的时候，我们还会对文件进行一些操作，比如：打印文件信息，将文件信息插入到数据库中，删除文件从数据库中删除等操作，这些操作又相互不影响，所以可以将它们单独提取出来，作为一个独立的方法，叫做拦截器，在需要对应的操作的时候只需要调用对应的拦截器即可。

#### <font color = orange>5.4 控制层</font>

1）对之前的业务方法进行统一的调度；

2）通过配置文件的方式，读取一些参数信息（比如：查询的结果最多返回的数量，对查询结果的排序方式（按深度/按查找名称的相似度）, 是否程序一运行就创建索引（默认不创建），进行文件系统监控的时间间隔，默认要查找的目录，默认要排除的目录（有些系统目录可以不用查找））

 ####  <font color = orange>5.5 命令行交互</font>

通过对用户输入的字符串进行处理，判断用户具体想干什么，最后去控制层调用对应的业务方法.

## <font color=green>6 使用介绍</font>

方式1：项目写完之后，使用maven工具打包，可以直接在命令行运行 .jar程序

maven打包：

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/maven_jar1.PNG)

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/maven_jar2.PNG)

运行结果：

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/maven_jar_run.png)

方式2 ：后来，我又对运行方式进行了优化，内置jre运行环境

将lib目录（里面放的是项目中需要导入的依赖jar包） 和 自己打的项目jar包， classes目录拷到一个文件中，然后将自己装的jre的所有目录拷贝到同一个目录下，然后写一个简单的.bat文件（run.bat）,run.bat文件内容如下：

start  jre/bin/java -jar find_in_windows-1.0.0.jar

最后目录结构如下：

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/run1.png)

最后将这个文件压缩，使用的时候只需要将文件解压，双击run.bat文件即可（windows环境下的）,运行结果：

![image](https://github.com/feihhh/myFind/raw/master/readmeIMG/run2.png)

## <font color=green> 7、运行结果展示</font>

