package com.univ.initializer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univ.initializer.bo.JacksonObj;
import com.univ.initializer.util.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author univ
 * date 2023/8/29
 */
@RestController
@RequestMapping("/jackson")
@Slf4j
public class JacksonController {

    /**
     * autoconfigure已经提供了，可直接用
     */
    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/serialize")
    public R<String> basicSerializeAndDeserialize() throws JsonProcessingException {
        JacksonObj jacksonObj = new JacksonObj();
        return R.data(objectMapper.writeValueAsString(jacksonObj));
    }

    /**
     * 看看框架内部使用的ObjectMapper行为是否符合预期
     * 注：必须有@RequestBody，因为此时才涉及到反序列化，{@link com.univ.initializer.config.JacksonConfig}才会起作用
     */
    @PostMapping("/requestBody")
    public R<JacksonObj> requestBody(@RequestBody JacksonObj jacksonObj) {
        return R.data(jacksonObj);
    }


}

