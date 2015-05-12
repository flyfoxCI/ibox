package cn.ibox.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ibox.bean.LuceneBean;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@TableBind(tableName = "file")
public class BoxFile extends Model<BoxFile>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7095359269690507219L;
	public static final BoxFile dao=new BoxFile();
	public static final String tablesname="file";
	/**/

	

	public List<BoxFile> findByPid(String pid) {
		// TODO Auto-generated method stub
		String sql="select * from "+tablesname+" where pid=?";
		return dao.find(sql,pid);
	}

	public boolean mkdir(String uuid,String dirName, String dirId, String url) {
		// TODO Auto-generated method stub
		
		try {
			new BoxFile().set("url", url).set("type", "dir").set("name", dirName)
			.set("upload_time",new Timestamp(new Date().getTime()))
			.set("pid", dirId)
			.set("id", uuid)
			.save();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
			
	}
	public String findPath(String dirId){
		BoxFile file=BoxFile.dao.findById(dirId);
		if(file!=null){
			return file.getStr("url");
		}else{
			return "";
		}
	}

	public BoxFile findByPath(String saveDir) {
		// TODO Auto-generated method stub
		return BoxFile.dao.findFirst("select * from "+tablesname+" where url=?", saveDir);
	}

	
	public List<LuceneBean> findOnline(String email) {
		String sql = "select id,name from file where name is not null";
		List<BoxFile> files = find(sql);
		List<LuceneBean> beans = new ArrayList<LuceneBean>();
		for (BoxFile file : files) {
			LuceneBean bean = new LuceneBean(file.getStr("id"),file.getStr("name"),email);
			beans.add(bean);
		}
		return beans;
	}

	public List<Record> getRec7Day() {
		// TODO Auto-generated method stub
		String sql="select count(id),date(upload_time) from file where name is not null and type!='dir' and"
				+ " date_sub(curdate(), INTERVAL 7 DAY ) <= date (now()) "
				+ "group by date(upload_time);";
		return Db.find(sql);
		 
	}

	public Record getCateCount(int i) {
		// TODO Auto-generated method stub
		String sql="select count(id) from `file` where to_days(`upload_time`) = to_days(now()) "
				+ "and name is not null and category=? and type!='dir' ";
		return Db.findFirst(sql, i);
	}

	public Record getSum7Day() {
		// TODO Auto-generated method stub
		String  sql="select count(id) from file where name is not null and type!='dir' and"
				+ " date_sub(curdate(), INTERVAL 7 DAY ) <= date (now());";
		return Db.findFirst(sql);
	}

	public BoxFile findByColumn(String column,String value) {
		String sql="select * from "+tablesname+"  where "+column+"=?";
		return dao.findFirst(sql, value);
	}
	
	

}
