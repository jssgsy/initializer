package com.univ.initializer.service.impl;


import com.univ.initializer.entity.Demo;
import com.univ.initializer.mapper.DemoMapper;
import com.univ.initializer.service.DBTestService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author univ
 * 2022/9/05
 */
@Service
public class DBTestServiceImpl implements DBTestService {

    @Resource
    private DemoMapper demoMapper;

    @Override
    public Demo getById(Long id) {
        System.out.println("-------");
        return demoMapper.selectById(id);
    }
}
