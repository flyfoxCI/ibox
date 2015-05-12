package cn.ibox.ctrl;

import ibox.util.constant.Constant;
import ibox.util.email.MailUtil;
import ibox.util.safe.CipherUtil;
import ibox.util.safe.MD5;
import ibox.util.valid.LoginValid;
import ibox.util.valid.registValid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.interceptor.LoginInterceptor;
import cn.ibox.model.LoginLog;
import cn.ibox.model.Mail;
import cn.ibox.model.Role;
import cn.ibox.model.RoleUser;
import cn.ibox.model.User;
import cn.ibox.service.FileService;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;

@ControllerBind(controllerKey = "/")
@ClearInterceptor(ClearLayer.ALL)
public class IndexCtrl extends BaseController<User> {
	private static final Logger logger = Logger.getLogger(IndexCtrl.class);
	private FileService fileService;

	public IndexCtrl() throws IOException {
		fileService = new FileService();
	}

	public void index() {
		Subject subject=SecurityUtils.getSubject();;
		if(subject.isAuthenticated()){
			User loginUser = (User) subject.getPrincipal();
			int roleId=RoleUser.dao.findRoleId(loginUser.getInt("id")).getInt("role_id");
			String type=Role.dao.findById(roleId).getStr("type");
			if (type.equals("1")) {
				this.forwardAction("/admin/");
				
			} else {
				this.forwardAction("/disk");
				
			}
		}else{
			this.renderHTML("login.html");
		}
			
	}
	
	@Before({SessionInViewInterceptor.class,LoginValid.class })
	public void login() {
		String username = getPara("username");
		String password = MD5.getMD5ofStr(getPara("password"));
		LoginLog loginLog = new LoginLog();
		String currentDate =new SimpleDateFormat("yyyy-MM-dd hh.mm").format(new Date()); 
		loginLog.set(LoginLog.USER_NAME, username)
					.set(LoginLog.LOGIN_TIME, currentDate)
					.set(LoginLog.LOGIN_IP, getRequest().getRemoteAddr())
					.save();
		User m = User.dao.login(username, password);
		if (m != null) {
			Integer uid = m.getInt("id");
			if (m.getInt("is_active") == 0) {
				this.rendJson(false, null, "用户已被禁用，请联系管理员");
			}
			String ip = this.getRequest().getHeader("X-Real-IP");
			if (StringUtils.isEmpty(ip)) {
				ip = this.getRequest().getRemoteAddr();
			}
			Timestamp nowStr = new Timestamp(new Date().getTime());
			User.dao.update(nowStr, ip, uid);
			setSessionAttr("uid", uid.toString());
			setSessionAttr("file_id",m.get("file_id"));
			setSessionAttr("dirId",m.get("file_id"));
			setSessionAttr("ip", ip);
			setSessionAttr("dir", "/");
			setSessionAttr("username",username);
			setSessionAttr("email", m.get("email"));
			setSessionAttr("is_active",  m.getInt("is_active").toString());
			setSessionAttr("is_admin", m.getInt("is_admin").toString());
			UsernamePasswordToken token=new UsernamePasswordToken(username, password, true, ip);
			Subject subject=SecurityUtils.getSubject();
			try {
				if (!subject.isAuthenticated()) {
					subject.login(token);
					
				}
				this.rendJson(true, null, "登录成功");
				return;
			} catch (UnknownSessionException use) {
				subject = new Subject.Builder().buildSubject();
				subject.login(token);
				logger.error(Constant.UNKNOWN_SESSION_EXCEPTION);
				this.rendJson(false, 300, Constant.UNKNOWN_SESSION_EXCEPTION);
			} catch (UnknownAccountException e) {
				logger.error(Constant.UNKNOWN_ACCOUNT_EXCEPTION);
				this.rendJson(false, 300, Constant.UNKNOWN_ACCOUNT_EXCEPTION);
			} catch (IncorrectCredentialsException e) {
				this.rendJson(false, 300, Constant.INCORRECT_CREDENTIALS_EXCEPTION);
			} catch (LockedAccountException e) {
				this.rendJson(false, 300, Constant.LOCKED_ACCOUNT_EXCEPTION);
			} catch (ExcessiveAttemptsException e) {
				this.rendJson(false, 300, Constant.EXCESSIVE_ATTEMPTS_EXCEPTION);
			} catch (AuthenticationException e) {
				this.rendJson(false, 300, Constant.AUTHENTICATION_EXCEPTION);
			}
			
		} else {
			this.rendJson(false, null, "用户名和密码不正确");
			
		}

	}
	public void regist() {
		this.renderHTML("regist");
	}
	@Before(registValid.class)
	public void register() {
		String username = getPara("username");
		String email = getPara("email");
		String password = MD5.getMD5ofStr(getPara("password"));
		UUID uuid = UUID.randomUUID();
		User m = User.dao.findByEmail(email, username);
		if (m != null) {
			this.rendJson(false, null, "用户名已经存在");
		} else {
			User.dao.save(username, email, password, uuid);
			fileService.mkdir(email, uuid);
			this.rendJson(true, null, "注册成功");
			this.rendJson("login");
		}
	}

	public void admin() {
		this.renderHTML("/admin/index.html");
	}
	@Before(LoginInterceptor.class)
	public void logout() {
		Subject subject = SecurityUtils.getSubject();	
		if(subject.isAuthenticated()){
			subject.logout();
			this.redirect("/");
		}
		
	}
	public void reset(){
		String password=getPara("password");
		String uid=getSessionAttr("uid");
		String sql="update user set password=? where id=?";
		Db.update(sql,MD5.getMD5ofStr(password),uid);
		this.redirect("/");
	}
	@Before(LoginInterceptor.class)
	public void resetPassword(){
		this.renderHTML("reset.html");
	}
	public void forget(){
		this.renderHTML("forget.html");
	}
	public void sendEmail() throws UnknownHostException{
		String email=getPara("email");
		Mail mail = new Mail();  
        mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
        mail.setSender("cherryzxh007@126.com");  
        mail.setReceiver(email); // 接收人  
        mail.setUsername("cherryzxh007@126.com"); // 登录账号,一般都是和邮箱名一样吧  
        mail.setPassword("zxh428193"); // 发件人邮箱的登录密码  
        mail.setSubject("重置密码链接");  
      
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("email", email);
        map.put("time", new Date());
        String ip=InetAddress.getLocalHost().getHostAddress();
        String rootPath=getRequest().getContextPath();
        String url="http://"+ip+":8080"+rootPath+"/forgetPassword?key="+CipherUtil.encryptData(JSON.toJSONString(map));
        mail.setMessage("请在30分钟之内重置密码，否则链接将失效"+"<a href="+url+">"+url+"</a>");
        new MailUtil().send(mail);
        this.renderHTML("/common/sendsuccess.html");
	}
	public void check(){
		User user=User.dao.findByEmail(getPara("email"));
		if(user!=null){
			this.rendJson(true, 200,"");
		}else{
			this.rendJson(false, 200,"");
		}
	}
	@SuppressWarnings("unchecked")
	public void forgetPassword(){
		String key=getPara("key");
		String str=CipherUtil.decryptData(key);
		Map<String, Object> map =new HashMap<String,Object>();
		map=(Map<String, Object>) JSON.parse(str);
		String email=(String) map.get("email");
		long time=(long) map.get("time");
		long now=new Date().getTime();
		if(time+1800000<now){
			this.renderHTML("/common/timeout.html");
		}else{
			setAttr("email", email);
			this.renderHTML("/ureset.html");
		}
		
	}
	public void ureset(){
		String email=getPara("email");
		String password=getPara("password");
		String sql="update user set password=? where email=?";
		Db.update(sql, MD5.getMD5ofStr(password),email);
		this.rendJson(true, 200, "重置密码成功");
	}
	

}
