package com.app.forecast.controller;


import com.app.forecast.common.BaseResponse;
import com.app.forecast.common.ErrorCode;
import com.app.forecast.common.ResultUtils;
import com.app.forecast.exception.BusinessException;
import com.app.forecast.model.entity.User;
import com.app.forecast.model.entity.dto.UserLoginRequest;
import com.app.forecast.service.UserService;
import com.app.forecast.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户接口
 *
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<Map<String, String>> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println(userLoginRequest);
        String userAccount = userLoginRequest.getUseraccount();
        String userPassword = userLoginRequest.getPassword();
        System.out.println(userAccount + userPassword);
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        String Token = JwtUtil.createJwtToken(user.getUseraccount());
        Map<String, String> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("useraccount",user.getUseraccount());
        map.put("useravatar",user.getUseravatar());
        map.put("useravatar",user.getUseravatar());
        map.put("gender",String.valueOf(user.getGender()));
        map.put("token",Token);
        return ResultUtils.success(map);
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
}
