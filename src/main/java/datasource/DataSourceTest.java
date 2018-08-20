package datasource;

public class DataSourceTest {

    public static void main(String[] args) {
        UserTestMapper mapper = DataBaseFactory.getMapper(DataSourceTypeEnum.LOCAL_TEST, UserTestMapper.class);
        UserTest userTestById = mapper.getUserTestById(1);
        System.out.println(userTestById.toString());
    }

}
