package cn.amoxil.zeroblog.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 登录相关控制器
 * @Author: Am0xil
 * @date: 2021/1/14 20:35
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login() {
        return "首页";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 创建一个新的subject对象
        Subject subject = SecurityUtils.getSubject();
        // 生成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 登录认证

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return "未知账号";
        } catch (LockedAccountException e){
            return "账号已被锁定";
        }catch (ExcessiveAttemptsException e){
            return "认证失败次数过多";
        } catch (AuthenticationException e){
            return "账号或密码不正确";
        }
        if (subject.isAuthenticated()){
            return "登录成功";
        }else {
            token.clear();
            return "登录失败，请稍后重试";
        }
    }
}
