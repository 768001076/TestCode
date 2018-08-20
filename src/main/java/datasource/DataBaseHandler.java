package datasource;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DataBaseHandler implements InvocationHandler {

    private Object mapper;

    private SqlSession sqlSession;

    private DataBaseHandler(Object mapper, SqlSession sqlSession) {
        this.mapper = mapper;
        this.sqlSession = sqlSession;
    }

    public static Object bind(Object mapper, SqlSession sqlSession) {
        return Proxy.newProxyInstance(mapper.getClass().getClassLoader(), mapper.getClass().getInterfaces(), new
                DataBaseHandler(mapper, sqlSession));
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        try {
            object = method.invoke(mapper, args);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.clearCache();
            sqlSession.close();
        }
        return object;
    }
}
