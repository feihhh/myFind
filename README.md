# myFind
## 1、项目描述
基于Java语言，模仿Linux下的find命令，实现windows下命令行的查找文件的功能，由于是Java语言开发，所以也具有跨平台性
简单来说，就是
## 2、项目背景
在Linux下的find命令很好用，但是在windows下查找文件却不是那么方便，在windows界面查找速度非常慢而且还不能跨盘查找，因此开发这款命令行的查找工具，可以在windows下快速全盘查找。
## 3、做这个项目需要准备知识
### （1）枚举类里面添加方法
&nbsp;&nbsp;&nbsp;&nbsp;首先，在描述文件类型时，使用枚举列出文件类型，然后通过构造方法传入对应文件类型的常见扩展名。

```
public enum FileType {
    //文档类型文件
    DOC("doc", "docx", "ppt", "pptx", "txt", "pdf", "wps", "html", "rtf"),
    //图片类型文件
    IMG("bmp", "gif", "jpg", "pic", "png", "tif"),
    //音频文件
    VIDEO("avi", "mpg", "mov", "swf", "mp3", "mp4", "wma","rm", "flv", "mkv"),
    //二进制文件
    BIN("exe", "java"),
    //压缩文件
    ARCHIVE("rar", "zip", "arj"),
    //其他类型文件
    OTHER("*");

    /**
     * 这里使用Set集合用来存储文件的扩展名，可以自动去重
     */
    private Set<String> extend = new HashSet<>();
    /**
     * @param extend 文件扩展名，由于文件扩展名的个数不确定，所以使用可变参数
     */
    FileType(String ... extend) {
        /**
         * boolean addAll(Collection<? extends E> c); 将所有元素加入集合中，其中所需要的参数是一个集合
         * Arrays.asList(extend); 可变参数实际上是数组实现的，此方法可以将一个数组转为一个集合
         */
        this.extend.addAll(Arrays.asList(extend));
    }
}
```
[关于枚举类的其他用法，可以看看这个...](https://blog.csdn.net/A__B__C__/article/details/89223512)

### （2）maven工具
通过使用maven工具，添加相关的依赖信息。

### （3）数据库操作以及JDBC编程
因为要查找电脑上的所有文件在哪，不能直接遍历电脑上的所有文件然后在输出，因为一个电脑上的文件数量非常多，如果用一个线程遍历一遍的话会非常慢，但是如果用多个线程同时遍历一个磁盘下的文件，感觉又比较麻烦，因为需要标记已经遍历过的文件什么的。还有就是如果要连续查找的话，每一次查找都要遍历电脑上的文件，所以这时候我们就要考虑要把电脑上的所有文件取出来，存在某一个地方，如果电脑上的文件有变化的话，只需要更新这个存起来的文件就可以了。这时候就用到了数据库。（因为对于我一个新手来说，我所知道的存储数据的方式就只有文件和数据库，但是两个相比，数据库自然是更适合了）。
在这个项目中，首先使用了MySQL数据库，通过使用由阿里巴巴开源的数据库连接池druid获取数据源，然后获取connection对象链接数据库。通过maven工具向pom文件中添加相关依赖。
### （4）文件的操作
文件操作至少需要会递归的遍历文件，因为我们这个是查询电脑上的文件，就需要将电脑生的所有文件全部存放在数据库中，就一定会用到遍历每个盘下的所有文件：

```
// 递归遍历一个文件夹里面的所有文件
public static void listFiles(File file)
    {
        System.out.println(file.getAbsolutePath());
        if (file.exists() && file.isDirectory())
        {
            File[] files = file.listFiles();
            for (File file1 : files)
            {
                if (file1 != null)
                {
                    listFiles(file1);
                }
            }
        }
    }
```
