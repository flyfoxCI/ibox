package cn.ibox.interceptor;


import ibox.util.constant.Constant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.ibox.model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * 简单的未登录拦截
 * 
 *
 */
public class LoginInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		// 获取shiro中的session
		Subject subject = SecurityUtils.getSubject();
		User loginUser = (User) subject.getSession().getAttribute(Constant.SHIRO_LOGIN_USER);
		// session为空
		if (null == loginUser) {
			controller.redirect("/");
		} else {
			ai.invoke();// 注意 一定要执行此方法
		}
	}

}
