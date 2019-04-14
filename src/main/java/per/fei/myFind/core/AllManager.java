package per.fei.myFind.core;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.index.Impl.FileScanImpl;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.FileType;

import java.util.Scanner;

public class AllManager {

    public void help ()
    {
        System.out.println("退出 ：quit");
        System.out.println("帮助 ：help");
        System.out.println("索引 ：index");
        System.out.println("搜索 ：search <文件名> " +
                "[<文件类型>  doc | img | video | bin | archive| other]");
    }

    public void quit ()
    {
        System.exit(-1);
    }

    public void index()
    {
        // TODO 创建线程池，建立索引
    }

    public void search (Condition condition)
    {
        // TODO 查询文件信息
    }
}
