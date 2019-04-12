package per.fei.myFind.core.model;

import java.util.*;

/**
 * 将文件类型抽象成为一个枚举类
 */
public enum FileType {
    //文档类型文件
    DOC("doc", "docx", "ppt", "pptx", "txt", "pdf", "wps", "html", "rtf"),
    //图片类型文件
    IMG("bmp", "gif", "jpg", "pic", "png", "tif"),
    //音频文件
    VIDEO("avi", "mpg", "mov", "swf", "mp3", "mp4", "wma","rm", "flv", "mkv"),
    //二进制文件
    BIN("exe", "java"),
    //归档类型文件
    ARCHIVE("rar", "zip", "arj"),
    //其他类型文件
    OTHER("*");

    /**
     * 此处使用Set集合用来存储文件的扩展名，可以自动去重
     */
    private Set<String> extend = new HashSet<>();

    /**
     * 由于文件扩展名的个数不确定，所以使用可变参数
     * @param extend 文件扩展名
     */
    FileType(String ... extend) {
        /**
         * boolean addAll(Collection<? extends E> c); 将所有元素加入集合中，其中所需要的参数是一个集合
         * Arrays.asList(extend); 可变参数实际上是数组实现的，此方法可以将一个数组转为一个集合
         */
        this.extend.addAll(Arrays.asList(extend));
    }

    /**
     * 通过扩展名获取文件类型
     * @param extend 文件扩展名
     * @return 返回文件类型
     */
    public static FileType lookUpByExtends(String extend)
    {
        for (FileType fileType : FileType.values())
        {
            if (fileType.extend.contains(extend))
            {
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    /**
     * 通过类型名获取文件类型
     * @param name 类型名
     * @return 返回文件类型
     */
    public static FileType lookUpByName(String name)
    {
        for (FileType fileType : FileType.values())
        {
            if (fileType.name().equals(name))
            {
                return fileType;
            }
        }
        return FileType.OTHER;
    }
}
