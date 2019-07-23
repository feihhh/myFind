package per.fei.myFind.core.minitor.Impl;

import per.fei.myFind.core.minitor.MonitorToFIle;

import java.io.*;

public class MonitorToFileImpl implements MonitorToFIle {
    @Override
    public void write(String msg) {
        //获取当前工作路径
        String path = System.getProperty("user.dir")+File.separator+"monitor.txt";
        File file = new File(path);
        //如果父目录不存在，就创建父目录
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        OutputStream out = null;
        try {
             out = new FileOutputStream(file, true);
             out.write(msg.getBytes());
             //获取当前系统下的换行符，输入一行数据换一个行
             out.write(System.getProperty("line.separator").getBytes());
            //System.out.println("File Change...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null)
            {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        String path = System.getProperty("user.dir")+File.separator+"monitor.txt";
//        System.out.println(path);
//        MonitorToFileImpl w = new MonitorToFileImpl();
//        w.write("lalaxiaxlia");
//    }
}
