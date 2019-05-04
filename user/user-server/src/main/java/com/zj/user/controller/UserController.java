package com.zj.user.controller;


import com.zj.user.common.*;
import com.zj.user.pojo.User;
import com.zj.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpServletRequest request,
                                      HttpServletResponse httpServletResponse,
                                      HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) { //验证成功
            //说明：面试必考！！！
            //todo①将在服务端生成的cookie对象响应给浏览器保存
            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
            //todo②用seesion的id作为key,user对象序列化的json字符串作为值，过期时间为30分钟，保存到redis数据库
            RedisShardedPoolUtil.setEx(session.getId(), Const.RedisCacheExtime.REDIS_SESSION_EXTIME,
                    JsonUtil.obj2String(response.getData()));
        }
        return response;
    }



    /**
     *
     * 退出登录
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String loginToken = CookieUtil.readLoginToken(request);
        RedisShardedPoolUtil.del(loginToken);
        CookieUtil.delLoginToken(request,response);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 检查用户名和邮箱的合理性
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户的信息
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session, HttpServletRequest request) {

        String loginToken = CookieUtil.readLoginToken(request);
        //从redis中获取user的序列
        String userJson = RedisShardedPoolUtil.get(loginToken);
        //反序列化,生成user对象
        User user = JsonUtil.string2Obj(userJson, User.class);
        return   ServerResponse.createBySuccess(user);

    }

    /**
     * 忘记密码根据用户名获取问题的答案
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 检查问题的答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 知道密码重置密码
     * @param request
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        //从redis中获取user的序列
        String userJson = RedisShardedPoolUtil.get(loginToken);
        //反序列化,生成user对象
        User user = JsonUtil.string2Obj(userJson, User.class);
        return iUserService.resetPassword(passwordOld, passwordNew, user);

    }

    /**
     * 更新用户信息
     * @param httpServletResponse
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpServletResponse httpServletResponse, HttpServletRequest request, User user) {

        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        //从redis中获取user的序列
        String userJson = RedisShardedPoolUtil.get(loginToken);
        //反序列化,生成user对象
        User currentUser = JsonUtil.string2Obj(userJson, User.class);
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
           // session.setAttribute(Const.CURRENT_USER, response.getData());
            CookieUtil.writeLoginToken(httpServletResponse,loginToken);
            RedisShardedPoolUtil.setEx(loginToken,Const.RedisCacheExtime.REDIS_SESSION_EXTIME,JsonUtil.obj2String(response.getData()));
        }
        return response;
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> get_information(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        //从redis中获取user的序列
        String userJson = RedisShardedPoolUtil.get(loginToken);
        //反序列化,生成user对象
        User currentUser = JsonUtil.string2Obj(userJson, User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }


}
