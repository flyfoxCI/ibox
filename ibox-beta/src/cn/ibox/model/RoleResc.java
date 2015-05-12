package cn.ibox.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
@TableBind(tableName="u_role_resc")
public class RoleResc extends Model<RoleResc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581918901343281338L;
	public static final RoleResc dao=new RoleResc();
	public static final String RESC_ID = "resc_id";
	public List<RoleResc> findByRoleId(Integer id) {
		// TODO Auto-generated method stub
		String sql="select * from u_role_resc where role_id=?";
		return find(sql, id);
	}
}
