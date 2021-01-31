package cn.amoxil.zeroblog.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
  *@Description: Shiro自定义Realm
  *@Author: Am0xil
  *@date: 2021/1/14 19:56
*/
@Configuration
public class CustomRealm extends AuthorizingRealm {
    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("*************开始身份认证*************");
        String userName = (String)authenticationToken.getPrincipal();
        String userPwd = new String((char[])authenticationToken.getCredentials());
        String password = "123";
        if (userName == null){
            throw new AccountException("用户名不正确");
        }else if (!StringUtils.equals(userPwd,password)){
            throw new AccountException("用户名或密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
