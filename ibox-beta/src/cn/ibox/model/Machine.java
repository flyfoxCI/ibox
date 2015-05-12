package cn.ibox.model;



import ibox.util.safe.CipherUtil;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Machine extends Model<Machine>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9176944856024928669L;
	public final String tablesname="machine";
	public static final Machine dao=new Machine();
	public List<Machine> findAll() {
		String sql="select id,ip,hostname,username,role,operation from "+ tablesname;
		return find(sql);
	}
	public void add(String ip, String username, String hostname, String password, String role) {
		// TODO Auto-generated method stub
		new Machine().set("ip", ip)
		.set("hostname", hostname)
		.set("username", username)
		.set("passwd",CipherUtil.encryptData(password))
		.set("role", role)
		.save();
	}
	public List<Machine> findDataNode() {
		// TODO Auto-generated method stub
		String sql="select * from "+tablesname+" where role=?";
		return find(sql, "datanode");
	}
	public void deleteByIp(String ip) {
		// TODO Auto-generated method stub
		String sql="select id from "+tablesname+" where ip=?";
	    dao.deleteById(findFirst(sql, ip).getInt("id"));
	    
	    
		
	}
	
}
