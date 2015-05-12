package cn.ibox.ctrl.admin;



import ibox.util.constant.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.BoxFile;
import cn.ibox.model.Resc;
import cn.ibox.model.User;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
@ControllerBind(controllerKey="/admin/")
public class IndexCtrl extends BaseController<User>{
	@Before(SessionInViewInterceptor.class)
	public void index(){
		Subject subject=SecurityUtils.getSubject();;
		User loginUser = (User) subject.getPrincipal();
		//权限过滤
		List<Resc> rescList=Resc.dao.find(Constant.DEFAULT_RESC_QUERY);
		List<Resc> removeList=new ArrayList<Resc>();
		for(Resc resc:rescList){
			if(!subject.isPermitted(resc.getInt("id").toString())){
				removeList.add(resc);
			}
		}
		rescList.removeAll(removeList);
		if(null==loginUser.getStr("head_photo_path")){
			setSessionAttr("head_photo", "/admin/img/head.jpg");
		}else{
			this.setSessionAttr("head_photo", loginUser.getStr("head_photo_path"));
		}
		this.setSessionAttr("rescList", rescList);
		this.setSessionAttr("username", loginUser.getStr("username"));
		//文件统计
		List<Record> sevenCount=BoxFile.dao.getRec7Day();
		Record sevenSum=BoxFile.dao.getSum7Day();
		Record videoCount=BoxFile.dao.getCateCount(1);
		Record picCount=BoxFile.dao.getCateCount(3);
		Record musicCount=BoxFile.dao.getCateCount(2);
		//封装数据
		String records ="";
		for(Record record:sevenCount){
			records=records+record.get("date(upload_time)");
			records=records+","+record.get("count(id)")+"|";
		}
		records=records.substring(0, records.length()-1);
		setAttr("records", records);
		setAttr("sevenSum", sevenSum.get("count(id)"));
		setAttr("videoCount", videoCount.get("count(id)"));
		setAttr("picCount", picCount.get("count(id)"));
		setAttr("musicCount", musicCount.get("count(id)"));
		String clustContent=getClusterInfo("http://172.31.5.142:50070/dfshealth.jsp");
		setAttr("clusterContent", clustContent);
		this.renderHTML("index.html");
	}
	/*
	 * 统计文件sql 语句汇总
	 * 
	 	select count(*),date(upload_time) from `file` where date_sub(curdate(), INTERVAL 7 DAY) <= date(`upload_time`) and name is not null group by date(upload_time);//最近7天
		select sum(id),date(upload_time) from `file` where date_sub(curdate(), INTERVAL 7 DAY) <= date(`upload_time`) and name is not null and category=4 ;
	 * 
	 * 
	 * 
	 */
	@Test
	public String getClusterInfo(String ip){
		try {
			URL url=new URL(ip);
			URLConnection connection=url.openConnection();
			InputStream in=connection.getInputStream();
			BufferedInputStream reader=new BufferedInputStream(in);
			StringBuffer sb=new StringBuffer();
			byte[] b=new byte[1024]; 
			while((reader.read(b))!=-1){
				sb.append(new String(b));
			}
			String content=sb.toString();
			int index1=content.indexOf("<h3> NameNode Storage: </h3>");
			int index2=content.indexOf("<h3>Cluster Summary</h3>");
			return content.substring(index2, index1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	


}
