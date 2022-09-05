package com.univ.initializer.util.response;

import java.io.Serializable;

/**
 * @author univ
 * 2022/9/05
 */
public interface IResultCode extends Serializable {

    /**
     *
     * @return
     */
    String getMessage();

    /**
     *
     * @return
     */
    int getCode();
}
