package cn.ibox.ctrl.admin;




import ibox.util.expect.ExpectUtil;
import ibox.util.safe.CipherUtil;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.Machine;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.PathKit;

@ControllerBind(controllerKey="/admin/machine")
@RequiresRoles(value={"admin"})
public class MacCtrl extends BaseController<Machine>{
	String rootPath=PathKit.getRootClassPath();
	
	public void index(){
		String username=getUserMap().get("email");
		setAttr("username", username);
		this.renderHTML("machine.html");
	}
	public void getMacList(){
		List<Machine> macList=Machine.dao.findAll();
		if(macList.size()==0){
			this.renderJson("[]");
		}else{	
			String[][] data=new String[macList.size()][5];
			for(int i=0;i<macList.size();i++){
				data[i][0]=macList.get(i).getStr("ip");
				data[i][1]=macList.get(i).getStr("hostname");
				data[i][2]=macList.get(i).getStr("username");
				data[i][3]=macList.get(i).getStr("role");
				data[i][4]="<a href='#' onclick='javascript:delHost(this);'>删除</a>";
				
			}
			this.renderJson(data);
		}
	}
	public void add(){
		String ip=getPara("ip");
		String username=getPara("username");
		String hostname=getPara("hostname");
		String password=getPara("password");
		String role=getPara("role");
		String data="{"+"\"ip\""+":"+"\""+ip+"\""+","+"\"hostname\""+":"+"\""+hostname+"\""+","
					+"\"username\""+":"+"\""+username+"\""+","+"\"role\""+":"+"\""+role+"\""+"}";
		//'{"ip":"172.26.23.175","hostname":"cherry-pc","username":"cherry","role":"datanode"}'
		String sql="select ip,username,passwd from machine where role=?";
		Machine mac=Machine.dao.findFirst(sql, "namenode");
		List<Machine> dataNodeList=Machine.dao.findDataNode();
		if(Machine.dao.findById(ip)!=null){
			this.rendJson(false,null,"主机已经存在");
		}else{
			
			String deployPath=rootPath+"/deploy_datanode.sh";
			String scpHostPath=rootPath+"/scp_hosts.sh";
			String params[]={mac.getStr("ip"),mac.getStr("username"),CipherUtil.decryptData(mac.getStr("passwd"))
					,username,password,hostname};
			if(ExpectUtil.deployNode(deployPath, ip, params)){
				if(dataNodeList!=null){
					for(Machine datanode:dataNodeList){
						ExpectUtil.excute(scpHostPath,datanode.getStr("ip"),datanode.getStr("username"));
					}
				}
				Machine.dao.add(ip, username, hostname, password, role);
				this.rendJson(true, 200, "添加成功", data);
			}else{
				this.rendJson(false,null,"添加主机失败");
			}
		}	
	}
	public void del(){
		String ip=getPara("ip");
		String delHostPath=rootPath+"/del_hosts.sh";
		String sql="select ip,username,passwd from machine where role=?";
		Machine mac=Machine.dao.findFirst(sql, "namenode");
		String params[]={mac.getStr("ip"),mac.getStr("username"),CipherUtil.decryptData(mac.getStr("passwd"))};
		if(ExpectUtil.delHost(delHostPath, ip, params)){
			this.rendJson(true,null,"关闭主机成功，并成功下线");
			Machine.dao.deleteByIp(ip);
		}else{
			this.rendJson(false,null,"删除主机失败,请检查集群");
		}
	}

}
