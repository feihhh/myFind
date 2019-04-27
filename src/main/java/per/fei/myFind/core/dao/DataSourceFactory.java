package per.fei.myFind.core.dao;

import com.alibaba.druid.pool.DruidDataSource;
import per.fei.myFind.config.DefaultConfig;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 数据源工厂，使用单例模式，外部必能创建对象
 */
public class DataSourceFactory {

        private static volatile DruidDataSource instence;
        /**
         * 构造方法私有化
         */
        private DataSourceFactory() {}

        public static DataSource getInstence()  {

            if (instence == null)
            {
                synchronized (DataSource.class)
                {
                    if (instence == null)
                    {
                        /**
                         * mysql的连接方式
                         */
//                        instence = new DruidDataSource();
//                        instence.setDriverClassName("com.mysql.jdbc.Driver");
//                        instence.setUrl("jdbc:mysql://localhost:3306/find");
//                        instence.setUsername("root");
//                        instence.setPassword("123456");
                        //h2数据库
                        instence = new DruidDataSource();
                        instence.setDriverClassName("org.h2.Driver");
                        String path = System.getProperty("user.dir")+ File.separator+"find_database";
                        instence.setUrl("jdbc:h2:"+path);
                        instence.setTestWhileIdle(false);
                        initDataSource(DefaultConfig.getConfig().getIfBuildIndex());
                    }
                }
            }
            return instence;
        }

        //初始化数据源
        private static void initDataSource (boolean flag)
        {
            StringBuffer sb = new StringBuffer();

            try(InputStream in = DataSourceFactory.class.getClassLoader().getResourceAsStream("my_find.sql"))
            {
                if (in != null)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line=reader.readLine()) != null)
                    {
                        sb.append(line);
                    }
                }
                else {
                    throw new RuntimeException("dataSource init error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String sql = sb.toString();

            try(Connection connection = getInstence().getConnection())
            {
                if (flag)
                {
                    PreparedStatement statement = connection.prepareStatement("drop table if exists files");
                    statement.executeUpdate();
                }

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
////    测试代码
//    public static void main(String[] args) throws SQLException {
//        String path = System.getProperty("user.dir")+ File.separator+"find_database";
//        System.out.println(path);
//    }

}
