package per.fei.myFind.core.intercapter.ThingClearIntercapter;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.intercapter.ThingIntercapter;
import per.fei.myFind.core.model.Things;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThingClearIntercapter implements ThingIntercapter, Runnable {

    private FileDao fileDao;
    private Queue<Things> queue;

    public ThingClearIntercapter(FileDao fileDao, Queue<Things> queue) {
        this.fileDao = fileDao;
        this.queue = queue;
    }

    /**
     * 删除数据库中的Things对象
     * @param things 要删除的对象
     */
    @Override
    public void intercapter(Things things) {
        this.fileDao.delete(things);
    }

    @Override
    public void run() {
        while (true)
        {
            Things things = queue.poll();
            if (things != null)
            {
                File file = new File(things.getPath());
                if (!file.exists())
                {
                    this.intercapter(things);
                }
            }
        }
    }

    public static void main(String[] args) {

        FileDao fileDao = new FileDaoImpl(DataSourceFactory.getInstence());
        Queue<Things> queue = new ArrayBlockingQueue<>(1024);

//        Thread thread = new Thread(new (ThingClearIntercapter(fileDao, queue)));
    }
}
