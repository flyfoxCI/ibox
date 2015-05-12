package cn.ibox.ctrl.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.Resc;
import cn.ibox.model.Role;
import cn.ibox.model.RoleResc;
import cn.ibox.model.RoleUser;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
@ControllerBind(controllerKey="/admin/role")
@RequiresRoles(value={"admin"})
public class RoleCtrl extends BaseController<RoleResc>{
	public void index(){
		String url=getAttr("parentUrl");
		Resc resc=Resc.dao.findByUrl(url);
		List<Resc> setRescList=Resc.dao.findChildren(resc.getInt("id"));
		setSessionAttr("setRescList", setRescList);
		this.renderHTML("index.html");
	}
	public void getRoleList(){
		 List<Role> roles=Role.dao.findAll();
		 RoleUser curRoleUser=RoleUser.dao.getRole(getSessionAttr("uid"));
		 String sEcho = getPara("sEcho");
		 int start=getParaToInt("iDisplayStart");
		 int length=getParaToInt("iDisplayLength");
		 int total=roles.size();
		 Object[][] data=new Object[length][6];
		 List<Role> operateList=new ArrayList<Role>();
		 if(roles.size()==0){
				this.renderJson("[]");
			}else{
				if(start+length>total){
					operateList=roles.subList(start, total);
				}else{
					operateList=roles.subList(start, start+length);
				}
			}
		for(int i=0;i<length;i++){
			if(i>operateList.size()-1)break;
			if(operateList.get(i).getInt("id")<=curRoleUser.getInt("role_id"))continue;
			data[i][0]=operateList.get(i).getInt("id");
			data[i][1]=operateList.get(i).getStr("name");
			data[i][2]=operateList.get(i).getStr("type");
			data[i][3]=operateList.get(i).getDate("create_time");
			data[i][4]=operateList.get(i).getStr("enable");
			data[i][5]="<a href='#'  onclick='javascript:editRole(this);'>编辑</a>|<a href='#'  onclick='javascript:delRole(this);'>删除</a>";
		}
		 List<Object[]> list = Arrays.asList(data);
		 Map<String, Object> map=new  HashMap<String, Object>();
	        map.put("aaData",list);
	        map.put("iTotalRecords",total);
	        map.put("iTotalDisplayRecords", total);
	        map.put("sEcho",sEcho);
	        this.renderJson(map);
			
	}
	public void add(){
		String name=getPara("role_name");
		String type=getPara("role_type");
		String enable=getPara("is_active");
		String[] rid=getPara("ids").split(",");
		int sort=getParaToInt("role_sort");
		Role role1=Role.dao.findByColumn(name, "name");
		if(role1!=null){
			this.rendJson(false, 200, "添加失败");
		}
		new Role().set("name", name)
					.set("type", type)
					.set("enable", enable)
					.set("sort", sort)
					.set("create_time", new Date())
					.save();
		Role role=Role.dao.findByColumn(name,"name");
		for(String id:rid){
			new RoleResc().set("role_id", role.getInt("id"))
			.set("resc_id", Integer.parseInt(id))
			.save();
		}
		String data="{"+"\"id\""+":"+"\""+role.getInt("id")+"\""+","+"\"name\""+":"+"\""+name+"\""+","+"\"type\""+":"+"\""+type+"\""+","
				+"\"time\""+":"+"\""+role.getDate("create_time")+"\""+","+"\"enable\""+":"+"\""+enable+"\""+"}";
		this.rendJson(true, 200, "添加成功",data);
		
	}
	public void edit(){
		String name=getPara("name");
		Role role=Role.dao.findByColumn("name",name);
		List<RoleResc> rescs=RoleResc.dao.findByRoleId(role.getInt("id"));
		Object[] data=new Object[2];
		data[0]=role;
		data[1]=rescs;
		this.rendJson(true, 200, "添加成功",data);
		
	}
	public void update(){
		int  id=getParaToInt("id");
		String name=getPara("role_name");
		int type=getParaToInt("role_type");
		int enable=getParaToInt("is_active");
		String[] rids=getPara("ids").split(",");
		int sort=getParaToInt("role_sort");
		String sql2="delete from u_role_resc where role_id=?";
		Db.update(sql2, id);
		if(rids.length>1){
			for(String rid:rids){
				new RoleResc().set("role_id", id)
				.set("resc_id", Integer.parseInt(rid))
				.save();
			}
		}
		String sql1="update u_role set name=?, type=? ,enable=? ,sort=? where id=?";
		Db.update(sql1, name,type,enable,sort,id);
		this.rendJson(true, 200, "更新成功");
		
		
		
		
	}
	public void del(){
		//删除的前提是删掉该角色的所有用户
		int id=getParaToInt("id");
		String sql1="delete from u_role where id=?";
		String sql2="delete from u_role_resc where role_id=?";
		Db.update(sql1,id);
		Db.update(sql2, id);
		this.rendJson(true, 200, " 删除成功");
	}
	
}
