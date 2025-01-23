package com.univ.initializer.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 1. @JsonFormat是jackson中的注解；
 * 2. 所以@JsonFormat在序列化过程中会介入，可用来覆写默认的pattern
 * 3. @JsonFormat也可以作用于java.util.Date；
 * 4. @JsonFormat不仅可作用于时间类型，还有诸如Number类型；
 */
@Data
public class JacksonObj {
    private String name = "univ";

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date date = new Date();

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private LocalDate localDate = LocalDate.now();

    @JsonFormat(pattern = "yyyy/MM/dd HH/mm/ss", timezone = "GMT+8")
    private LocalDateTime localDateTime = LocalDateTime.now();
}
