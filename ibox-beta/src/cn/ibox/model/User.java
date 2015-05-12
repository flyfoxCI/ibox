package cn.ibox.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@TableBind(tableName="user")
public class User extends Model<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2747115136037764922L;
	public final String tablesname="user";
	public static final String  USER_ID="id";
	public static final String USERNAME = "username"; // 登录账号
	public static final String PASSWORD = "password"; // 登录密码
	public static final User dao=new User();
	
	public User login(String username,String password){
		return findFirst("select * from user where username=? and password=?",username,password);
	}

	public void update(Timestamp nowStr, String ip, int uid) {
		// TODO Auto-generated method stub
		Db.update("update "+ tablesname+" set last_time=?,last_ip=? where id=?",nowStr,ip,uid);
		
	}

	public User findByEmail(String email,String username) {
		// TODO Auto-generated method stub
		return dao.findFirst("select * from "+tablesname+" where email=? or username=?",email,username);
	}
	@Before(Tx.class)
	public void save(String username,String email, String password, UUID uuid) {
		 new User().set("email", email)
		 			.set("username",username)
				   .set("password", password)
				   .set("is_admin", 0)
				   .set("is_active",1)
				   .set("use_space", 0)
				   .set("space",2014)
				   .set("file_id", uuid.toString())
				   .save();
		int id= User.dao.findByName(username).getInt("id");
		 new RoleUser().set("role_id", 3)
		 				.set("user_id", id)
		 				.save();
		   
		
	}

	

	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return (ArrayList<User>) dao.find("select * from "+tablesname);
	}

	

	public Page<User> search(int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		String sql="select * ";
		String sqlExceptSelect="from user order by last_time desc";
		return paginate(pageNumber, pageSize, sql, sqlExceptSelect);
	}

	public List<User> findByPage(int start, int end) {
		// TODO Auto-generated method stub
		String sql="select * from user limit "+start+","+end;
		return dao.find(sql);
	}

	public User findByName(String username) {
		// TODO Auto-generated method stub
		String sql="select * from user where username=?";
		return dao.findFirst(sql, username);
	}

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		String sql="select * from user where email=?";
		return dao.findFirst(sql, email);
		
	}
	
	
	
	

}
