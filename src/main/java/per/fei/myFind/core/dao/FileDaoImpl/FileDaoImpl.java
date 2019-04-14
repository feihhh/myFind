package per.fei.myFind.core.dao.FileDaoImpl;

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
import java.util.LinkedList;
import java.util.List;

public class FileDaoImpl implements FileDao {

    private DataSource dataSource ;

    public FileDaoImpl(DataSource dataSource) {
        this.dataSource = DataSourceFactory.getInstence();
    }

    @Override
    public void insert(Things things) {

        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = this.dataSource.getConnection();
            String sql = "insert into files (name, depth, file_type, path) values (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, things.getName());
            statement.setInt(2, things.getDepth());
            statement.setString(3, things.getFileType().toString());
            statement.setString(4, things.getPath());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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
        }
    }

    @Override
    public List<Things> find(Condition condition) {

        List<Things> list= new LinkedList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.dataSource.getConnection();

            StringBuffer sql = new StringBuffer();
            //"select name, depth, file_type, path from files
            // where name like 'name%' and file_type = 'ft'";
            sql.append("select name, depth, file_type, path from files where ");

            sql.append("name like '").append(condition.getName()).append("%'");

            if (condition.getFileType() != null)
            {
                sql.append(" and file_type = '").append(condition.getFileType()).append("'");
            }

            statement = connection.prepareStatement(sql.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Things things = new Things();
                things.setName(resultSet.getString("name"));
                things.setDepth(resultSet.getInt("depth"));
                things.setPath(resultSet.getString("path"));
                things.setFileType(FileType.lookUpByName(resultSet.getString("file_type")));
                list.add(things);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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
        return list;
    }

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
//    }

}
