package per.fei.myFind.core;

import per.fei.myFind.config.DefaultConfig;
import per.fei.myFind.config.HandlePath;
import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.index.Impl.FileScanImpl;
import per.fei.myFind.core.minitor.FileMinitor;
import per.fei.myFind.core.minitor.Impl.FileMnitorImpl;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.FileType;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Impl.SearchImpl;
import per.fei.myFind.core.search.Search;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllManager {

    private FileDao fileDao;

    private FileMinitor fileMinitor;

    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().
                    availableProcessors()*2);

    private FileScan fileScan;

    private AllManager()
    {
        DataSource dataSource = DataSourceFactory.getInstence();
        this.fileDao = new FileDaoImpl(dataSource);
        fileScan = new FileScanImpl();
        fileMinitor = new FileMnitorImpl(this.fileDao);
        this.minitor();
        this.defaultConfigInit();
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
        System.out.println();
        System.out.println("帮助 ：help");
        System.out.println();
        System.out.println("索引 ：index");
        System.out.println();
        System.out.println("打开文件 ：open <文件名> [打开第几个该文件]");
        System.out.println();
        System.out.println("搜索 ：search <文件名> " +
                "[<文件类型>  doc | img | video | bin | archive| other]");
        System.out.println("注意：打开文件是在上一次查询的基础上打开指定文件名的文件，如果没有查询，无法打开...");
        System.out.println();
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

        new Thread(() -> {

            System.out.println("build index start ...");
            System.out.println("start time:"+new Date());
            //用来阻塞构建索引的线程，当所有现场都把索引剪完之后在继续朝后执行
            final CountDownLatch countDownLatch = new CountDownLatch(includePath.size());
            for (String path : includePath)
            {
                executorService.submit(() -> {
                    fileScan.index(path);
                    countDownLatch.countDown();
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
        }).start();
    }

    private LinkedHashSet<Things> list;

    public void search (String[] cons)
    {
        if (list != null)
        {
            list.clear();
        }
        Condition condition = new Condition();
        //"搜索 ：search <文件名> [<文件类型>  doc | img | video | bin | archive| other]");
        condition.setName(cons[1]);
        condition.setFileType((cons.length > 2)?FileType.lookUpByName(cons[2].toUpperCase()):null);
        Search search = new SearchImpl(this.fileDao);
        list = search.find(condition);
        Iterator iterator = list.iterator();
        int i = 1;
        while (iterator.hasNext())
        {
            System.out.println(i+++". "+iterator.next());
            System.out.println();
        }
    }

    public void open(String input)
    {
        if (list == null)
        {
            System.out.println("请先查询文件，在打开...");
            return;
        }
//        open aaa.txt 3
        String[] opens = input.split(" ");
        int len = opens.length;
        if (len == 1)
        {
//            input = open 默认打开第一个文件
            Things things = (Things)list.toArray()[0];
            openFile(things.getPath());
        }
        else if (len >= 2)
        {

            List<Things> openList = new ArrayList<>();
            String name = opens[1];
            Iterator<Things> iterator = list.iterator();
            while (iterator.hasNext())
            {
                Things things = new Things();
                things = iterator.next();
                if (things.getName().equals(name))
                {
                    openList.add(things);
                }
            }
            if (openList != null)
            {
                if (len == 3)
                {
                    if (opens[2].matches("[0-9]+")) {
                        int index = Integer.parseInt(opens[2]);
                        if(index>0 && index < openList.size())
                        {
                            String path = openList.get(index).getPath();
                            System.out.println("打开文件:"+path);
                            openFile(path);
                        }
                        else {
                            System.out.println("第三个参数输入过大...");
                        }
                    }
                    else {
                        System.out.println("第三个参数输入错误...");
                    }
                }
                else if (len == 2)
                {
                    for (Things t : openList)
                    {
                        System.out.println("打开文件："+t.getPath());
                        openFile(t.getPath());
                    }
                }
            }
        }
    }

    private void openFile(String path)
    {
        File file = new File(path);
        if (file.exists())
        {
            try {
                java.awt.Desktop.getDesktop().open(file);
                System.out.println(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("文件不存在...");
        }
    }

//    public void open(String name)
//    {
//        if (list != null)
//        {
//            Set<String> paths = new TreeSet<>();
//            for (Things things : list)
//            {
//                if (things.getName().equals(name))
//                {
//                    paths.add(things.getPath());
//                }
//            }
//            if (paths != null)
//            {
//                for (String path : paths)
//                {
//                    File file = new File(path);
//                    if (file.exists())
//                    {
//                        try {
//                            java.awt.Desktop.getDesktop().open(file);
//                            System.out.println(path);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        else
//        {
//            System.out.println("请先查询文件，在打开...");
//        }
//    }

//    public void open(String[] paths) {
//        for (int i=1; i<paths.length; i++)
//        {
//            String path = paths[i];
//            File file = new File(path);
//            if (file.exists())
//            {
//                try {
//                    java.awt.Desktop.getDesktop().open(file);
//                    System.out.println(path);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            else
//            {
//                System.out.println(path+":file not exist");
//            }
//        }
//    }

    /**
     * 文件监控
     * 其中lambdd表达式实现了Runnable接口覆写run方法
     */
    void minitor()
    {
        fileMinitor.minitor(config.getHandlePath());
        new Thread(() -> fileMinitor.start()).start();
    }

    public void defaultConfigInit()
    {
        Properties properties = new Properties();
        File file = new File(System.getProperty("user.dir")+File.separator+"target"+
                File.separator+"classes"+File.separator+"myFind_config.properties");

        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultConfig config = DefaultConfig.getConfig();
        String maxLimit = properties.getProperty("max_limit");
        if (maxLimit != null)
        {
            config.setMaxLimit(Integer.parseInt(maxLimit));
        }
        String ifbuildIndex = properties.getProperty("if_build_index");
        if (ifbuildIndex != null)
        {
            config.setIfBuildIndex(Boolean.parseBoolean(ifbuildIndex));
        }
        String orderByDepthDesc = properties.getProperty("order_by_desc");
        if (orderByDepthDesc != null)
        {
            config.setResultDefaultSortDesc(Boolean.parseBoolean(orderByDepthDesc));
        }
        String intv = properties.getProperty("interval");
        if (intv != null)
        {
            config.setInterval(Integer.parseInt(intv));
        }
        String excludePath = properties.getProperty("hand_path_exclude_path");
        if (excludePath != null)
        {
            String[] paths = excludePath.split(";");
            if (paths.length>0)
            {
                //清理掉已经有的默认值
                config.getHandlePath().getExcludePath().clear();
                for (String path : paths)
                {
                    config.getHandlePath().addExcludePath(path);
                }
            }
        }
    }

//    public static void main(String[] args) {
//       String str = "3";
//        System.out.println(str);
//    }
}
