package com.univ.initializer.entity.postgres;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author univ
 * 2022/9/05
 */
@ApiModel(value = "Demo对象", description = "")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer age;

    private LocalDate birthday;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Demo{" +
        "id=" + id +
        ", name=" + name +
        ", age=" + age +
        ", birthday=" + birthday +
        "}";
    }
}
