package com.univ.initializer.entity.dynamic;

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
 * @since 2022-09-09
 */
@Getter
@Setter
@ApiModel(value = "Test对象", description = "")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer age;


}
