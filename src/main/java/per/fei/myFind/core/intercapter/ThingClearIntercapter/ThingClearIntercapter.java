package per.fei.myFind.core.intercapter.ThingClearIntercapter;

import org.omg.PortableInterceptor.Interceptor;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.intercapter.FileIntercapter;
import per.fei.myFind.core.intercapter.ThingIntercapter;
import per.fei.myFind.core.model.Things;

import java.io.File;

public class ThingClearIntercapter implements ThingIntercapter {

    private FileDao fileDao;

    public ThingClearIntercapter(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    /**
     * 删除数据库中的Things对象
     * @param things 要删除的对象
     */
    @Override
    public void intercapter(Things things) {
        this.fileDao.delete(things);
    }
}
