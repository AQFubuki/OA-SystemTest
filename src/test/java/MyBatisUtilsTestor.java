import com.fubuki.fubukioa.entity.User;
import com.fubuki.fubukioa.service.UserService;
import com.fubuki.fubukioa.utils.MyBatisUtils;
import org.junit.Test;

public class MyBatisUtilsTestor {
    @Test
    public void Sample1() {
        String result1 = (String) MyBatisUtils.executeQuery(sqlSession -> {
            String out = (String) sqlSession.selectOne("test.sample");
            return out;
        });
        String result2 = (String) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("test.sample"));
        System.out.println(result2 + result1);
    }

    @Test
    public void SelectUserId() {
        User result = (User) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("user.selectByUserId", 11));
        System.out.println(result);
    }

    @Test
    public void SelectUserName() {
        UserService userService = new UserService();
        User user1 = userService.checkLogin("t6", "tt");
        System.out.println(user1);
    }
}
