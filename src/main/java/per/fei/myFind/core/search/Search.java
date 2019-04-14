package per.fei.myFind.core.search;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.intercapter.ThingClearIntercapter.ThingClearIntercapter;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.Things;
import per.fei.myFind.core.search.Impl.SearchImpl;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public interface Search {

    /**
     * 按条件查找
     * @param condition 查找条件
     * @return 返回查找的结果集
     */
    List<Things> find(Condition condition);


//    public static void main(String[] args) {
//        Search search = new SearchImpl(DataSourceFactory.getInstence(),
//                new ThingClearIntercapter(new FileDaoImpl(DataSourceFactory.getInstence()),
//                        new ArrayBlockingQueue<>(1024)));
//        Condition condition = new Condition();
//        condition.setName("aaa");
//        List<Things> list = search.find(condition);
//        for (Things t : list)
//        {
//            System.out.println(t);
//        }
//    }

}
