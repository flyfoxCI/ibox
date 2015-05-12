package cn.ibox.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName="u_resc")
public class Resc extends Model<Resc>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9164516104028092579L;
	public static final Resc dao=new Resc();
	public static final String ID = "id";
	public Resc findByUrl(String url) {
		// TODO Auto-generated method stub
		String sql="select id from u_resc where url=?";
		Resc resc=dao.findFirst(sql,url);
		return resc;
		
	}
	public List<Resc> findChildren(Integer id) {
		// TODO Auto-generated method stub
		String sql="select * from u_resc where parent_id=?";
		return find(sql, id);
	}
}
