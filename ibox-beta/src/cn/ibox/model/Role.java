package cn.ibox.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName="u_role")
public class Role extends Model<Role>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3577519051624084878L;
	public static final String ID = "id";
	public static final Role dao=new Role();
	public List<Role> findAuthRoles(int  role_id) {
		// TODO Auto-generated method stub
		String sql="select * from u_role where id >?";
		return find(sql, role_id);
	}
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from u_role";
		return find(sql);
	}
	
	public Role findByColumn(String column, String value) {
		// TODO Auto-generated method stub
		String sql="select * from u_role where  "+column+"=?";
		return findFirst(sql, value);
	}
}
