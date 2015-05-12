package cn.ibox.model;

import ibox.util.constant.Constant;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName="u_role_user")
public class RoleUser extends Model<RoleUser>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3866078643319780432L;
	public static final RoleUser dao=new RoleUser();
	public static final String ROLE_ID = "role_id";
	public void getRoleId(Object sessionAttr) {
		// TODO Auto-generated method stub
		
	}
	public RoleUser getRole(Object uid) {
		// TODO Auto-generated method stub
		return dao.findFirst(Constant.DEFAULT_USER_ROLES_QUERY,uid);
	}
	public void delUserById(int userId) {
		// TODO Auto-generated method stub
		
		String sql="delete from u_role_user where user_id=? ";
		Db.update(sql, userId);
	}
	public RoleUser findRoleId(Integer uid) {
		// TODO Auto-generated method stub
		String sql="select * from u_role_user where user_id=?";
		return dao.findFirst(sql, uid);
	}
	
	

}
