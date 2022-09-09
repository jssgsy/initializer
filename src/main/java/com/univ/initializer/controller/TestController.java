package com.univ.initializer.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.univ.initializer.config.db.dynamic.DataSourceConfig;
import com.univ.initializer.config.db.dynamic.DataSourceContext;
import com.univ.initializer.config.db.dynamic.DataSourceUtil;
import com.univ.initializer.config.db.dynamic.DbKeyConstant;
import com.univ.initializer.entity.dynamic.Test;
import com.univ.initializer.service.DbTestService;
import com.univ.initializer.service.DynamicDataSourceTestService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author univ
 * 2022/9/05
 */
@RestController
@RequestMapping("/test")
public class TestController {

//    @Resource
//    private DbTestService dbTestService;

    @Resource
    private DynamicDataSourceTestService dynamicDataSourceTestService;

    @Resource
    private DataSourceUtil dataSourceUtil;

    @ResponseBody
    @RequestMapping("/home")
    public String test() {
        System.out.println("ok");
        return "ok";
    }

    @ResponseBody
    @GetMapping("/db/dynamic")
    public Object dynamicDataSource(Long id, String dbKey) {
        return dynamicDataSourceTestService.queryDB(id);
    }

    /**
     * 测试先新增数据源，然后接着查询
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/db/dynamic/addAndGet")
    public Object dynamicAddDataSource(Long id) {
        String url = "jdbc:mysql://localhost:3306/ssm?useAffectedRows=true";
        String username = "test";
        String password = "123";
        String driverClassName = "com.mysql.jdbc.Driver";
        dataSourceUtil.addDataSource(DbKeyConstant.THIRD, url, username, password, driverClassName);
        return dynamicDataSourceTestService.queryDB(id);
    }
}
