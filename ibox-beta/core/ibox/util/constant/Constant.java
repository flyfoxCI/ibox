package ibox.util.constant;

public class Constant {
	public static final int PAGESIZE = 20; // 每页默认显示数
	public static final String DWZ_PAGE_NUMBER = "pageNum"; // dwz page number
	public static final String DWZ_PAGE_SIZE = "numPerPage"; 
	public static final String VIDEO_FORMAT="mp4  avi  3gp  rmvb  wmv  mkv  mpg  vob  mov  flv  swf";
	public static final String MUSIC_FORMAT="au aiff vqf cda ape mp3 realaudio wma midi wav";
	public static final String PIC_FORMAT="bmp gif jpg pic png tif";
	public static final String DOC_FORMAT="";
	public static final String[] CATEGORY={"/视频","/音乐","/图片","/文档"};
	public static final String SHIRO_LOGIN_USER = "loginUser";
	public static final String DEFAULT_AUTHENTICATION_QUERY = "SELECT * FROM user WHERE is_active =1  AND username = ?";
	public static final String DEFAULT_USER_ROLES_QUERY = "SELECT role_id FROM u_role_user WHERE user_id = ?";
	public static final String DEFAULT_ADMIN_ROLES_QUERY = "SELECT role_id FROM u_role_user"; // 系统超级管理员默认获取全部角色
	public static final String DEFAULT_PERMISSIONS_QUERY = "SELECT resc_id FROM u_role_resc WHERE role_id = ?";
	public static final String DEFAULT_ADMIN_PERMISSIONS_QUERY = "SELECT id FROM u_resc"; // 系统超级管理员默认获取全部权限
	public static final boolean PERMISSIONS_LOOKUP_ENABLED = true; // 权限查看设置
	public static final String SYSTEM_ADMINISTRATOR = "admin";
	public static final String DEFAULT_RESC_QUERY = "select * from u_resc where level=1 ";
	
	
	public static final String INCOMPLETE_LOGIN_INFO = "登录信息填写不完整";
	public static final String TIMEOUT_CAPTCHA_EXCEPTION = "验证码超时!";
	public static final String INCORRECT_CAPTCHA_EXCEPTION = "验证码错误!";
	public static final String UNKNOWN_SESSION_EXCEPTION = "异常会话!";
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "账号错误!";
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "密码错误!";
	public static final String LOCKED_ACCOUNT_EXCEPTION = "账号已被锁定，请与系统管理员联系!";
	public static final String EXCESSIVE_ATTEMPTS_EXCEPTION = "用户登录超过限制数,请稍后再访问！";
	public static final String AUTHENTICATION_EXCEPTION = "您没有授权!";
	public static final String LOGIN_SUCCESS = "登录成功";
	public static final String LOGINOUT_AUTHENTICATION_EXCEPTION = "身份异常!";

}
