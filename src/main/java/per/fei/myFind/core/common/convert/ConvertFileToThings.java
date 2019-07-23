package per.fei.myFind.core.common.convert;


import per.fei.myFind.core.model.FileType;
import per.fei.myFind.core.model.Things;

import java.io.File;

public class ConvertFileToThings {

    private int getGepth(File file)
    {
//        D:\我的\课件\java课件\3-JavaSE之程序逻辑控制和方法的定义与使用.pdf
        char[] filePath = file.getAbsolutePath().toCharArray();
        int len = filePath.length;
        int depth = 0;
        for (int i=0; i<len; i++)
        {
            if (File.separatorChar == filePath[i])
            {
                depth ++;
            }
        }
        return depth;
    }

    private FileType getFileType(File file)
    {
        String name = file.getName();
        String extend = "*";

        int len  = name.length();
        int index = name.lastIndexOf(".");

        if ((index > 0) && (len > (index+1)))
        {
            extend = new String(name.substring(index + 1));
        }
        return FileType.lookUpByExtends(extend);
    }

    /**
     * 将File对象转换为Things对象
     * @param file
     * @return
     */
    public Things convertFileToThings(File file)
    {
        Things things = new Things();
        things.setName(file.getName());
        things.setDepth(getGepth(file));
        things.setPath(file.getAbsolutePath());
        things.setFileType(getFileType(file));
        return things;
    }

//    public static void main(String[] args) {
//        ConvertFileToThings con = new ConvertFileToThings();
//        System.out.println(con.convertFileToThings(new File("D:\\我的\\笔记\\html\\js_day07\\Jquery和计算器小项目\\avi")));
//    }

}
