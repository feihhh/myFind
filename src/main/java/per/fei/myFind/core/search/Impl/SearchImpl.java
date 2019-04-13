package per.fei.myFind.core.search.Impl;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Search;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;

public class SearchImpl implements Search {

    private DataSource dataSource;

    public SearchImpl(DataSource dataSource) {
        this.dataSource = DataSourceFactory.getInstence();
    }

    @Override
    public List<Things> find(Condition condition) {
        FileDao fileDao = new FileDaoImpl(this.dataSource);
        List<Things> things = fileDao.find(condition);

        // TODO
        return things;
    }
}
