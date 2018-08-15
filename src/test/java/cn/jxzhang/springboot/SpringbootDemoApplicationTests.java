package cn.jxzhang.springboot;

import cn.jxzhang.springboot.domain.ResponseMessage;
import cn.jxzhang.springboot.domain.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String URL_TEST_REQUEST_PARAM = URL_PREFIX + "/testRequestParam";

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

    @Test
    public void testResponseEntity() {
//        ResponseEntity<String> forEntity = template.getForEntity("http://10.33.194.1:8088/Api/apifordimension/getDimension?dimTableName=DIM_PUBLIC_CITY_SINGLE&code=city_code", String.class);
//        String body = forEntity.getBody();
//        System.out.println(body);
//
//        String response = "{\"code\":-1,\"msg\":\"参数错误\",\"data\":\"\"}";

        String response = "{\"code\": 10, msg:\"\", data: [{\"city_code\": 19999, \"city_name\": \"廊坊\"}, {\"city_code\": 100001, \"city_name\": \"北京\"}]}";

        JSONObject jsonObject1 = JSONObject.parseObject(response);

        Integer code = jsonObject1.getInteger("code");
        System.out.println(code);
        JSONArray jsonArray = jsonObject1.getJSONArray("data");

        System.out.println(jsonArray.isEmpty());

        System.out.println(jsonArray);

        for (Object o : jsonArray) {
            System.out.println(o.getClass());
            if (o instanceof JSONObject) {
                JSONObject x = (JSONObject) o;
                String city_code = x.getString("city_code");
                System.out.println(city_code);
            }
        }
    }

    @Test
    public void testR() {
        Map<String, Object> req = new HashMap<>();

        req.put("name", "zhangSan");
        req.put("age", 12);

        String forObject = template.getForObject(URL_TEST_REQUEST_PARAM, String.class, req);

        System.out.println(forObject);
    }

    @Test
    public void testRR() {
        Map<String, Object> req = new HashMap<>();

        req.put("name", "zhangSan");
        req.put("age", 12);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(req);

        ResponseEntity<String> exchange = template.exchange(URL_TEST_REQUEST_PARAM, HttpMethod.GET, httpEntity, String.class);
    }

    @Test
    public void testResponseMessage() {

        String forObject = template.getForObject("http://10.33.194.1:8888/Api/apifordimension/getDimension?dimTableName={name}&code={code}", String.class, "DIM_PUBLIC_CITY_SINGLES", "city_code");
//
        ResponseEntity<String> exchange = template.exchange("http://10.313.194.12:8088/Api/apifordimension/getDimension?dimTableName={name}&code={code}", HttpMethod.GET, null, String.class, "DIM_PUBLIC_CITY_SINGLES", "city_code");
//
        HttpStatus statusCode = exchange.getStatusCode();
        System.out.println(statusCode);

        System.out.println(forObject);


    }
}
