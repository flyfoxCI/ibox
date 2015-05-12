package cn.ibox.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName="u_login_log")
public class LoginLog extends Model<LoginLog>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8806728205046202798L;
	public static final String USER_ID = "id";
	public static final String LOGIN_TIME = "login_time";
	public static final String LOGIN_IP = "login_ip";
	public static final String USER_NAME = "username";
	public static final LoginLog dao=new LoginLog();
	public List<LoginLog> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from u_login_log";
		return find(sql);
		
	}

}
