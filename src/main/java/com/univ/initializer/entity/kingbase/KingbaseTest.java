package com.univ.initializer.entity.kingbase;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuminglu
 * @since 2022-10-09
 */
@Getter
@Setter
@TableName("kingbase_test")
@ApiModel(value = "KingbaseTest对象", description = "")
public class KingbaseTest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;


}
