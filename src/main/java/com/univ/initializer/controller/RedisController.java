package com.univ.initializer.controller;

import com.univ.initializer.config.RedisConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author univ
 * date 2023/7/31
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    /**
     * 注入的时候就要指定泛型实参，不然各种类型提示
     * 当然，要和配置中{@link RedisConfig}的保持一致
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * autoConfig也提供了此对象，可直接用在string-string场景下；
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 因为{@link RedisConfig}配置为GenericJackson2JsonRedisSerializer，因此必须为json串
     * @param key
     * @param json
     * @return
     */
    @GetMapping("/value/set")
    public String valueSet(@RequestParam("key") String key, @RequestParam("json") String json) {
        redisTemplate.opsForValue().set(key, json, 1, TimeUnit.MINUTES);
        return "value/set, will expired in one minute";
    }

    @GetMapping("/value/get")
    public String valueGet(@RequestParam("key") String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/value/string/set")
    public String valueStringSet(@RequestParam("key") String key, @RequestParam("value") String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return "value/string/set";
    }

    @GetMapping("/value/string/get")
    public String valueStringGet(@RequestParam("key") String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @GetMapping("/value/obj/set")
    public String valueObjSet(@RequestParam("key") String key) {
        redisTemplate.opsForValue().set(key, new ValueObj("univ", 30), 1, TimeUnit.MINUTES);
        return "set ok, expired in one minute";
    }

    @GetMapping("/value/obj/get")
    public ValueObj valueObjGet(@RequestParam("key") String key) {
        // 这里需要强转，有点不方便
        return (ValueObj) redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/hash/set")
    public String hashSet(@RequestParam("key") String key) {
        redisTemplate.opsForHash().put(key, "name", "zhangsan");
        redisTemplate.opsForHash().put(key, "age", 18);
        redisTemplate.opsForHash().put(key, "married", true);
        return "hash/set ok";
    }

    @GetMapping("/hash/get")
    public List hashGet(@RequestParam("key") String key) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList("name", "age", "married"));
    }

    @Data
    @AllArgsConstructor
    // 必须有无参构造函数，否则序列化失败
    @NoArgsConstructor
    static class ValueObj {
        String name;
        Integer age;
    }

    @GetMapping("/list/set")
    public String listSet(@RequestParam("key") String key) {
        redisTemplate.opsForList().leftPush(key, "hello");
        redisTemplate.opsForList().leftPush(key, "world");
        return "list/set";
    }

    @GetMapping("/list/get")
    public List listGet(@RequestParam("key") String key) {
        Object o = redisTemplate.opsForList().leftPop(key);
        Object o1 = redisTemplate.opsForList().leftPop(key);
        return Arrays.asList(o, o1);
    }

    public static void main(String[] args) {
        /*JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer(null);
        byte[] names = serializer.serialize("name");
        System.out.println(names);*/
    }
}
