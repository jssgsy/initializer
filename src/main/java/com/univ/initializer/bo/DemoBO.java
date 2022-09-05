package com.univ.initializer.bo;

import java.time.LocalDate;
import lombok.Data;

/**
 * @author univ
 * 2022/9/05
 */
@Data
public class DemoBO {

    private Long id;

    private String name;

    private Integer age;

    private LocalDate birthday;
}
