package per.fei.myFind.core.dao;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;


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
                        instence = new DruidDataSource();
                        instence.setDriverClassName("com.mysql.jdbc.Driver");
                        instence.setUrl("jdbc:mysql://localhost:3306/find");
                        instence.setUsername("root");
                        instence.setPassword("123456");
                    }
                }
            }
            return instence;
        }


//    测试代码
//    public static void main(String[] args) throws SQLException {
//        Connection connection = DataSourceFactory.getInstence().getConnection();
//        String sql = "insert into files (name, depth, file_type, path) values ('haha', 15, 'DOC', 'D://haha')";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.executeUpdate();
//    }

}
