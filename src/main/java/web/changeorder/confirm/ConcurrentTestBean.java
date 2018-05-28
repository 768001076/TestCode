package web.changeorder.confirm;

/**
 * @Description: 并发测试实体类
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/3/1
 */
public class ConcurrentTestBean {

    private int id;

    private String name;

    private int age;

    private long time;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
