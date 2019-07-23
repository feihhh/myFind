package per.fei.myFind.core.dao.FileDaoImpl;

import per.fei.myFind.config.DefaultConfig;
import per.fei.myFind.core.HanYuPingYing.PingYing;
import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.model.Condition;
import per.fei.myFind.core.model.FileType;
import per.fei.myFind.core.model.Things;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FileDaoImpl implements FileDao {

    private DataSource dataSource ;

    public FileDaoImpl(DataSource dataSource) {
        this.dataSource = DataSourceFactory.getInstence();
    }

    /**
     * 插入
     * @param things 插入的对象
     */
    @Override
    public void insert(Things things) {
        Connection connection = null;
        PreparedStatement statement = null;
        PingYing pingYing = new PingYing();
        try {
            connection = this.dataSource.getConnection();
            String sql = "insert into files (name, name_length, depth, file_type, path, name_pinyin) values (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            String name = things.getName();
            statement.setString(1, name);
            statement.setInt(2, name.contains(".")?name.split("\\.")[0].length():name.length());
            statement.setInt(3, things.getDepth());
            statement.setString(4, things.getFileType().toString());
            statement.setString(5, things.getPath());
            statement.setString(6, pingYing.wordsToPinYin(name));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResource(connection, statement, null);
        }
    }

    private DefaultConfig config = DefaultConfig.getConfig();

    /**
     * 查找
     * @param condition 按条件查询
     * @return
     */
    @Override
    public LinkedHashSet<Things> find(Condition condition) {

        LinkedHashSet<Things> set= new LinkedHashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //prep1: select name, name_length, depth, file_type, path from files where name like 'a%' or name_pinyin like 'a%' and file_type = 'VIDEO' order by name_length asc limit 30
        try {
            connection = this.dataSource.getConnection();

            StringBuffer sql = new StringBuffer();
//            select * from files where name like 'word%' and file_type = 'doc'
//            order by depth asc limit 30;
            sql.append("select name, name_length, depth, file_type, path from files where ");

            sql.append("(name like '").append(condition.getName()).append("%'");

            sql.append(" or name_pinyin like '").append(condition.getName()).append("%')");

            if (condition.getFileType() != null)
            {
                sql.append(" and file_type = '").append(condition.getFileType()).append("'");
            }

            sql.append(" order by name_length ").append(config.getResultDefaultSortDesc()?"asc":"desc")
                    .append(" limit ").append(config.getMaxLimit());

            statement = connection.prepareStatement(sql.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Things things = new Things();
                things.setName(resultSet.getString("name"));
                things.setDepth(resultSet.getInt("depth"));
                things.setPath(resultSet.getString("path"));
                things.setFileType(FileType.lookUpByName(resultSet.getString("file_type")));
                set.add(things);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResource(connection, statement, resultSet);
        }
        return set;
    }

    /**
     * 删除
     * @param things 删除的对象
     */
    @Override
    public void delete(Things things) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            String sql = "delete from files where path = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, things.getPath());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResource(connection, statement, null);
        }

    }

    /**
     * 统计文件数量
     * @return 返回数量
     */
    @Override
    public int countFileNum() {

        int fileNums = 0;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dataSource.getConnection();

            StringBuffer sql = new StringBuffer();
//           select count(name) from files;
            sql.append("select count(*) from files");

            statement = connection.prepareStatement(sql.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                fileNums = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResource(connection, statement, resultSet);
        }
        return fileNums;
    }

    /**
     * 关闭资源
     */
    private static void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet)
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null)
        {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    测试代码
//    public static void main(String[] args) {
//        DataSource ds = DataSourceFactory.getInstence();
//        FileDaoImpl fileDao = new FileDaoImpl(ds);
//        Things thing = new Things();
//        thing.setName("xixi");
//        thing.setDepth(2);
//        thing.setPath("D://lala//xixi");
//        thing.setFileType(FileType.lookUpByName("IMG"));
//        fileDao.insert(thing);
//        Condition condition = new Condition();
//        condition.setName("haha");
//        condition.setFileType(FileType.lookUpByName("DOC"));
//        List<Things> list= fileDao.find(condition);
//        for (Things things : list)
//        {
//            System.out.println(things);
//        }
//        fileDao.delete(thing);

//
//        ConvertFileToThings con = new ConvertFileToThings();
//
//        File file = new File("C:\\Users\\ASUS\\Desktop\\新建文件夹 (2)\\aix\\aaaa");
//        if (file.exists())
//        {
//            Things things = con.convertFileToThings(file);
//            fileDao.delete(things);
//        }
//
//    public static void main(String[] args) {
//
//        FileDao dao = new FileDaoImpl(DataSourceFactory.getInstence());
//        System.out.println(dao.countFileNum());
//    }
}
