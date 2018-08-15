package cn.jxzhang.springboot.controller;

import cn.jxzhang.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * MainController
 *
 * @author zhangjiaxing
 * @version 1.0
 */
@RestController
public class MainController {

    private static final String SUCCESS = "success";

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/get/{id}")
    public String get(@PathVariable String id) {
        logger.error("get: {}", id);
        return SUCCESS;
    }

    @PostMapping("/post")
    public String post() {
        logger.error("post");
        return SUCCESS;
    }

    @DeleteMapping("/delete")
    public String deleteMapping() {
        logger.error("delete");
        return SUCCESS;
    }

    @PutMapping("/put")
    public String putMapping(){
        logger.error("put");
        return SUCCESS;
    }

    @GetMapping("/list")
    public List<User> testComplexResult(User user) {
        System.out.println("user: " + user);
        return Arrays.asList(new User("zhangSan", "123"), new User("liSi", "123"));
    }

    @GetMapping("/user")
    public User testBeanResult() {
        logger.error("user");
        return new User("wangWu", "123");
    }

    /**
     * 传递POJO需要添加@RequestBody注解
     *
     * @param user user
     * @return status
     */
    @PutMapping("/addUser")
    public String testBeanParam(@RequestBody User user) {
        logger.error("add user: " + user);
        return SUCCESS;
    }

    /**
     * 参数在请求URL中，可以直接注入Pojo入参
     *
     * @param user User
     * @return status
     */
    @GetMapping("/addUser2")
    public String testBeanParam2(User user) {
        logger.error("add user2: " + user);
        return SUCCESS;
    }

    @GetMapping("/testRequestParam")
    public String testRequestParam(String name, Integer age, HttpServletRequest req) {

        String contextPath = req.getContextPath();

        System.out.println(contextPath);

        System.out.println(req.getQueryString());
        System.out.println("name: " + name);
        System.out.println("age: " + age);
        return SUCCESS;
    }
}
