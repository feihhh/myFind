package per.fei.myFind.core.intercapter.Impl;

import per.fei.myFind.core.common.ConvertFileToThings;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.intercapter.FileIntercapter;
import per.fei.myFind.core.model.Things;
import java.io.File;

public class ScannerIntercapter implements FileIntercapter {

    private FileDao fileDao ;

    public ScannerIntercapter(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    /**
     * 将Things对象插入数据库
     * @param file
     */
    @Override
    public void intercapter(File file) {
        //将一个File对象变为一个Things对象
        ConvertFileToThings convert = new ConvertFileToThings();
        Things thing = convert.convertFileToThings(file);
        //将Things对象存入数据库
        this.fileDao.insert(thing);
    }
}
