package per.fei.myFind.core.search;

import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.Things;

import java.util.List;

public interface Search {

    /**
     * 按条件查找
     * @param condition 查找条件
     * @return 返回查找的结果集
     */
    List<Things> find(Condition condition);

}
