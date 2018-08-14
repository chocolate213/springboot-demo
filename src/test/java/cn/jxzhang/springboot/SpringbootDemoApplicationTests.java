package cn.jxzhang.springboot;

import cn.jxzhang.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    private static final String URL_PREFIX = "http://localhost:8082";
    private static final String URL_GET = URL_PREFIX + "/get";
    private static final String URL_POST = URL_PREFIX + "/post";
    private static final String URL_PUT = URL_PREFIX + "/put";
    private static final String URL_DELETE = URL_PREFIX + "/delete";
    private static final String URL_LIST = URL_PREFIX + "/list";
    private static final String URL_USER = URL_PREFIX + "/user";
    private static final String URL_ADD_USER = URL_PREFIX + "/addUser";
    private static final String URL_ADD_USER2 = URL_PREFIX + "/addUser2";

    @Autowired
    private RestTemplate template;

    @Test
    public void testGet() {
        String forObject = template.getForObject(URL_GET.concat("/").concat("123"), String.class);
        System.out.println(forObject);
    }

    @Test
    public void testPost() {
        String forObject = template.postForObject(URL_POST, null, String.class);
        System.out.println(forObject);
    }

    @Test
    public void testPut() {
        template.put(URL_PUT, null);
    }

    @Test
    public void testDelete() {
        template.delete(URL_DELETE);
    }

    @Test
    public void testUser() {
        User forObject = template.getForObject(URL_USER, User.class);
        System.out.println(forObject);
    }

    @Test
    public void testAddUser() {
        //这种方式将会传递JSON类型的参数，所以请求参数需要加上@RequestBody注解
        template.put(URL_ADD_USER, new User("zhangSan", "123"));
    }

    @Test
    public void testAddUser2() {
        String forObject = template.getForObject(URL_ADD_USER2 + "?username=liSi&password=456", String.class);
        System.out.println(forObject);
    }

    @Test
    public void testUserList() {
        List<User> body = template
                .exchange(
                        "http://localhost:8082/list?username=zhangSan&password=123456",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {}).getBody();
        System.out.println(body);
    }
}
