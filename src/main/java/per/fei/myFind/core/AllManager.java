package per.fei.myFind.core;

import per.fei.myFind.config.DefaultConfig;
import per.fei.myFind.config.HandlePath;
import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.index.Impl.FileScanImpl;
import per.fei.myFind.core.intercapter.ThingClearIntercapter.ThingClearIntercapter;
import per.fei.myFind.core.intercapter.ThingIntercapter;
import per.fei.myFind.core.minitor.FileMinitor;
import per.fei.myFind.core.minitor.Impl.FileMnitorImpl;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.FileType;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Impl.SearchImpl;
import per.fei.myFind.core.search.Search;

import javax.sql.DataSource;
import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllManager {

    private FileDao fileDao;

    private DataSource dataSource;

    private FileMinitor fileMinitor;

    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().
                    availableProcessors()*2);

    private FileScan fileScan;

    private AllManager()
    {
        dataSource = DataSourceFactory.getInstence();
        this.fileDao = new FileDaoImpl(this.dataSource);
        fileScan = new FileScanImpl();
        fileMinitor = new FileMnitorImpl(this.fileDao);
        this.minitor();
    }

    private static volatile AllManager manager ;

    public static AllManager getManager()
    {
        if (manager == null)
        {
            synchronized (AllManager.class)
            {
                if (manager == null)
                {
                    manager = new AllManager();
                }
            }
        }
        return manager;
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
        System.out.println("感谢使用...");
        System.exit(0);
    }


    private DefaultConfig config = DefaultConfig.getConfig();
    public void index()
    {
        HandlePath handlePath = config.getHandlePath();
        Set<String> includePath = handlePath.getIncludePath();

        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("build index start ...");
                        System.out.println("start time:"+new Date());
                        //用来阻塞构建索引的线程，当所有现场都把索引剪完之后在继续朝后执行
                        final CountDownLatch countDownLatch = new CountDownLatch(includePath.size());
                        for (String path : includePath)
                        {
                            executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    fileScan.index(path);
                                    countDownLatch.countDown();
                                }
                            });
                        }
                        try{
                            countDownLatch.await();
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("Build Index Finish...");
                        System.out.println("finish time:"+new Date());
                    }
                }).start();
    }

    public void search (String[] cons)
    {

        Condition condition = new Condition();
        //"搜索 ：search <文件名> [<文件类型>  doc | img | video | bin | archive| other]");
        condition.setName(cons[1]);

        condition.setFileType((cons.length > 2)?FileType.lookUpByExtends(cons[2]):null);

        // TODO 设置排序，最大限制等初始值

        Search search = new SearchImpl(this.fileDao);
        List<Things> list = search.find(condition);
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }

    /**
     * 文件监控
     * 其中lambdd表达式实现了Runnable接口覆写run方法
     */
    void minitor()
    {
        fileMinitor.minitor(config.getHandlePath());
        new Thread(() -> fileMinitor.start()).start();
    }
}
