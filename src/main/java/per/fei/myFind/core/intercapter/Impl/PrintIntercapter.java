package per.fei.myFind.core.intercapter.Impl;

import per.fei.myFind.core.intercapter.FileIntercapter;

import java.io.File;
import java.io.FileInputStream;

public class PrintIntercapter implements FileIntercapter {

    /**
     * 打印拦截器 将文件路径打印出来
     * @param file
     */
    @Override
    public void intercapter(File file) {
        System.out.println(file.getAbsolutePath());
    }
}
