package per.fei.myFind.core.dao;

import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.Things;

import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public interface FileDao {

    /**
     * 朝数据库中插入数据
     * @param things 插入的对象
     */
    void insert(Things things);

    /**
     * 查询数据
     * @param condition 按条件查询
     * @return 返回查询结果
     */
    LinkedHashSet<Things> find(Condition condition);

    /**
     * 删除数据库中的数据
     * @param things 删除的对象
     */
    void delete(Things things);

    /**
     * 统计电脑中有多少文件
     */
    int countFileNum ();

}
