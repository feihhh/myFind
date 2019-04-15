package per.fei.myFind.core;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.index.Impl.FileScanImpl;
import per.fei.myFind.core.intercapter.ThingClearIntercapter.ThingClearIntercapter;
import per.fei.myFind.core.intercapter.ThingIntercapter;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.FileType;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Impl.SearchImpl;
import per.fei.myFind.core.search.Search;

import javax.sql.DataSource;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AllManager {

    private FileDao fileDao;

    public AllManager(FileDao fileDao)
    {
        this.fileDao = fileDao;
    }

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

    }

    public void search (Condition condition)
    {

        // TODO 设置排序，最大限制等初始值
        Search search = new SearchImpl(this.fileDao);
        List<Things> list = search.find(condition);
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }
}
