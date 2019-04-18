package per.fei.myFind.core.minitor.Impl;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import per.fei.myFind.config.DefaultConfig;
import per.fei.myFind.config.HandlePath;
import per.fei.myFind.core.common.ConvertFileToThings;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.minitor.FileMinitor;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

public class FileMnitorImpl extends FileAlterationListenerAdaptor implements FileMinitor {

    private FileAlterationMonitor monitor;

    private FileDao fileDao;

    private ConvertFileToThings convert ;

    public FileMnitorImpl(FileDao fileDao) {
        /**
         * DefaultConfig.getConfig().getInterval() 表示默认间隔时间
         */
        this.monitor = new FileAlterationMonitor(DefaultConfig.getConfig().getInterval());
        this.fileDao = fileDao;
        convert = new ConvertFileToThings();
    }

    public FileMnitorImpl() {

    }

    /**
     * 启动
     */
    @Override
    public void start() {
        try {
            this.monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监控
     * @param handlePath 要监控的目录
     */
    @Override
    public void minitor(HandlePath handlePath) {
        Set<String> includePath = handlePath.getIncludePath();
        for (String path : includePath)
        {
            //监听文件
            /**
             * 其中的lambda表达式是覆写了FileFilter的accpet方法，表示文件过滤器
             */
            FileAlterationObserver observer = new FileAlterationObserver(new File(path),
                    pathname -> {
                        for (String exPath : handlePath.getExcludePath())
                        {
                            if (pathname.getAbsolutePath().startsWith(exPath))
                            {
                                return false;
                            }
                        }
                        return true;
                    });
            observer.addListener(this);
            this.monitor.addObserver(observer);
        }
    }


    //--------------------------------------------------------

    /**
     * 新增目录
     * @param directory
     */
    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("DirectoryCreate:"+directory.getAbsolutePath());
        this.fileDao.insert(convert.convertFileToThings(directory));
    }

    /**
     * 删除目录
     * @param directory
     */
    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("DirectoryDelete:"+directory.getAbsolutePath());
        this.fileDao.delete(this.convert.convertFileToThings(directory));
    }

    /**
     * 新增文件
     * @param file
     */
    @Override
    public void onFileCreate(File file) {
        System.out.println("FileCreate:"+file.getAbsolutePath());
        this.fileDao.insert(this.convert.convertFileToThings(file));
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("FileDelete:"+file.getAbsolutePath());
        this.fileDao.insert(this.convert.convertFileToThings(file));
    }
    //--------------------------------------------------------

    /**
     * 停止
     */
    @Override
    public void stop() {
        try {
            this.monitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
