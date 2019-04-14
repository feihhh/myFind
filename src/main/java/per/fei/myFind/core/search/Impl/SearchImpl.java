package per.fei.myFind.core.search.Impl;

import org.omg.PortableInterceptor.Interceptor;
import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.intercapter.ThingClearIntercapter.ThingClearIntercapter;
import per.fei.myFind.core.intercapter.ThingIntercapter;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Search;
import javax.sql.DataSource;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class SearchImpl implements Search {

    private DataSource dataSource;
    //定义一个队列，用来存放要清除的数据库中的Things
    private Queue<Things> queue = new ArrayBlockingQueue<>(1024);

    private ThingClearIntercapter intercapter ;

    private FileDao fileDao;

    public SearchImpl(DataSource dataSource, ThingIntercapter intercapter) {
        this.dataSource = DataSourceFactory.getInstence();
        this.fileDao = new FileDaoImpl(this.dataSource);
        this.intercapter = new ThingClearIntercapter(this.fileDao, this.queue);
        //调用后台清理线程
        this.BackGroundCleanThread();
    }

    @Override
    public List<Things> find(Condition condition) {
        FileDao fileDao = new FileDaoImpl(this.dataSource);
        List<Things> things = fileDao.find(condition);

        Iterator<Things> iterator = things.iterator();
        while (iterator.hasNext())
        {
            Things thing = iterator.next();
            File file = new File(thing.getPath());
            if (!file.exists())
            {
                //删除查找的结果集中的不存在的元素
                iterator.remove();
                //删除数据库中的元素，但是这么弄效率较低，因为上
                // 一步是在迭代器中删除元素，速度快，但是这一步是在数据库
                // 操作// ，速度慢，两者速度不匹配，因此应该换一种方式，
                // 类似与生产者，消费者模型
//                fileDao.delete(thing);
                //使用一个队列，在后台一直清理
                this.queue.add(thing);
            }
        }

        return things;
    }

    private void BackGroundCleanThread()
    {
//        ThingClearIntercapter实现了Runnable接口
        Thread thread = new Thread(this.intercapter);

        //设置为守护线程
        thread.setDaemon(true);

        //设置线程名字
        thread.setName("BackGroundCleanThingsThread");

        thread.start();
        //由于这是一个后台清理线程，所以应该让它从这个对象一旦构造好就要开始执行
    }
}
