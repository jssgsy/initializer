package com.univ.initializer.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author univ
 */
@Data
public class JacksonObj {

    private String name = "univ";

    private Date date = new Date();

    private LocalDate localDate = LocalDate.now();

    private LocalDateTime localDateTime = LocalDateTime.now();
}
