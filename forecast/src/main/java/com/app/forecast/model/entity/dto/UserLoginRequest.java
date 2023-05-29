package com.app.forecast.model.entity.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户登录请求体
 *
 * @author
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String useraccount;

    private String password;
}