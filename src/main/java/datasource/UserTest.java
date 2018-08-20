package datasource;

/**
 * @Description: 数据库测试 - usertest
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/17
 */
public class UserTest {

    private int id;

    private String name;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override public String toString() {
        return "UserTest{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + '}';
    }
}
