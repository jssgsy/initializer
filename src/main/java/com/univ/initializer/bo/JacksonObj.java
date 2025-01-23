package com.univ.initializer.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 1. @JsonFormat是jackson中的注解；
 * 2. 所以@JsonFormat在序列化过程中会介入，可用来覆写默认的pattern
 * 3. @JsonFormat可同时作用于java.util.Date、LocalDate、LocalDateTime等；
 * 4. @JsonFormat不仅可作用于时间类型，还有诸如Number类型；
 *
 * 关于@DateTimeFormat：
 * 1. @DateTimeFormat是spring格式化中提供的注解；
 *  所以介入的时机是类型转换，而不是序列化；
 * 2. @DateTimeFormat可同时作用于java.util.Date、LocalDate、LocalDateTime等；
 */
@Data
public class JacksonObj {
    private String name = "univ";

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date = new Date();

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate localDate = LocalDate.now();

    @JsonFormat(pattern = "yyyy/MM/dd HH/mm/ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH/mm/ss")
    private LocalDateTime localDateTime = LocalDateTime.now();
}
