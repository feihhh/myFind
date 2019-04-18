package per.fei.myFind.core.index.Impl;

import per.fei.myFind.config.HandlePath;
import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.intercapter.FileIntercapter;
import per.fei.myFind.core.intercapter.Impl.PrintIntercapter;
import per.fei.myFind.core.intercapter.Impl.ScannerIntercapter;
import java.io.File;

public class FileScanImpl implements FileScan {

    HandlePath handlePath = HandlePath.getDefaultPath();

    @Override
    public void index(String path) {

        //遍历的每一个文件都会经过这个地方，在这里处理文件
        //添加文件拦截器
        //打印拦截
        //写入数据库拦截
        File file = new File(path);
        // 排除 要排除的目录
        for (String excludePath : handlePath.getExcludePath())
        {
            if (path.startsWith(excludePath))
            {
                return;
            }
        }

//        FileIntercapter f = new PrintIntercapter();
//        f.intercapter(file);
        FileIntercapter f1 = new ScannerIntercapter(new FileDaoImpl(DataSourceFactory.getInstence()));
        f1.intercapter(file);

        //递归遍历文件夹
        if (file.exists() && file.isDirectory())
        {
            File[] files = file.listFiles();
            if (files != null)
            {
                for (File file1 : files)
                {
                    index(file1.getAbsolutePath());
                }
            }
        }
    }



//    public static void main(String[] args) {
//        FileScan fileScan = new FileScanImpl();
//        fileScan.index(new File("C:\\Users\\ASUS\\Desktop\\新建文件夹 (2)"));
//    }
}
