package datasource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @Description: 多数据源配置
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/17
 */
public class DataBaseFactory {

    // 配置信息文件
    private static String configInfoFile = "datasource/mybatis-config.xml";

    // 最大数据源数量
    private static int maxConnectionSum = 10;

    // 数据源配置
    private static HashMap<Integer, SqlSessionFactory> dataSourceFactory = new HashMap<Integer, SqlSessionFactory>(maxConnectionSum);

    private static SqlSessionFactory getSqlSessionFactory(DataSourceTypeEnum dataSourceTypeEnum) {
        SqlSessionFactory sqlSessionFactory = null;
        if (dataSourceFactory.isEmpty() || !dataSourceFactory.containsKey(dataSourceTypeEnum.getDataSourceNo())) {
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(configInfoFile);
                sqlSessionFactory = new SqlSessionFactoryBuilder()
                        .build(inputStream, dataSourceTypeEnum.getPrefix());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException e) {
                    }
                }
            }
            dataSourceFactory.put(dataSourceTypeEnum.getDataSourceNo(), sqlSessionFactory);
            return sqlSessionFactory;
        } else {
            return dataSourceFactory.get(dataSourceTypeEnum.getDataSourceNo());
        }
    }

    public static <T> T getMapper(DataSourceTypeEnum dataSourceTypeEnum, Class<T> mapperClass) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(dataSourceTypeEnum);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        T mapper = sqlSession.getMapper(mapperClass);
        return (T)DataBaseHandler.bind(mapper, sqlSession);
    }

}
