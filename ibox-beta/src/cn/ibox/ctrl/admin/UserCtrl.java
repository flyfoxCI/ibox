package cn.ibox.ctrl.admin;

import ibox.util.safe.MD5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.Role;
import cn.ibox.model.RoleUser;
import cn.ibox.model.User;
import cn.ibox.service.FileService;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
@ControllerBind(controllerKey="/admin/user")
@RequiresRoles(value={"admin","manager"},logical=Logical.OR)
public class UserCtrl extends BaseController<User>{
	private FileService fileService;
	public UserCtrl() throws IOException {
		this.fileService = new FileService();
	}
	public void index(){
		RoleUser curRoleUser=RoleUser.dao.getRole(getSessionAttr("uid"));
		List<Role> roles=Role.dao.findAuthRoles(curRoleUser.getInt("role_id"));
		this.setSessionAttr("roles", roles);
		this.renderHTML("index.html");
	}
	public void getUserList(){
		 String sEcho = getPara("sEcho");
		 int start=getParaToInt("iDisplayStart");
		 int length=getParaToInt("iDisplayLength");
		List<User> userList=User.dao.findAll();
		RoleUser curRoleUser=RoleUser.dao.getRole(getSessionAttr("uid"));
		List<User> operateList=new ArrayList<User>();
		for(User user:userList){
			RoleUser roleUser=RoleUser.dao.getRole(user.getInt("id"));
			if(roleUser.getInt("role_id")<=curRoleUser.getInt("role_id")){
				operateList.add(user);
			}
		}
		userList.removeAll(operateList);
	    int total=userList.size();
		if(userList.size()==0){
			this.renderJson("[]");
		}else{
			if(start+length>total){
				operateList=userList.subList(start, total);
			}else{
				operateList=userList.subList(start, start+length);
			}
			Object[][] data=new Object[length][6];
			for(int i=0;i<operateList.size();i++){
				if(null==operateList.get(i)) break;
				data[i][0]=operateList.get(i).getStr("username");
				data[i][1]=operateList.get(i).getStr("email");
				data[i][2]=operateList.get(i).getLong("space");
				data[i][3]=operateList.get(i).getInt("is_active");
				int roleId=RoleUser.dao.getRole(operateList.get(i).getInt("id")).getInt("role_id");
				Role role=Role.dao.findById(roleId);
				data[i][4]=role.getStr("name");
				data[i][5]="<a href='#'  onclick='javascript:editUser(this);'>编辑</a>|<a href='#'  onclick='javascript:delUser(this);'>删除</a>";				
			}
			    List<Object[]> list = Arrays.asList(data);
				Map<String, Object> map=new  HashMap<String, Object>();
		        map.put("aaData",list);
		        map.put("iTotalRecords",total);
		        map.put("iTotalDisplayRecords", total);
		        map.put("sEcho",sEcho);
			this.renderJson(map);
		}
	}
	public void add(){
		String username=getPara("username");
		String email=getPara("email");
		Long space=getParaToLong("space");
		//String head_photo_path=getPara("head_photo_path");
		String password=getPara("password");
		int is_active=getParaToInt("active");
		int roleId=getParaToInt("role_id");
		UUID uuid=UUID.randomUUID();
		User m = User.dao.findByEmail(email, username);
		if (m != null) {
			this.rendJson(false, null, "用户名或邮箱已经存在");
		} else {
		new User().set("username", username)
				.set("email", email)
				//.set("head_photo_path", head_photo_path)
				.set("space", space)
				.set("password", MD5.getMD5ofStr(password))
				.set("is_active", is_active)
				.set("file_id", uuid.toString())
				.set("is_admin", 0)
				.set("use_space", 0)
				.save();
		new RoleUser().set("role_id", roleId)
					.set("user_id", User.dao.findByEmail(email, username).getInt("id"))
					.save();
		fileService.mkdir(email, uuid);
		String roleName=Role.dao.findById(roleId).getStr("name");
		String data="{"+ "\"username\""+":"+"\""+username+"\""+","+"\"email\""+":"+"\""+email+"\""+","
				+"\"space\""+":"+space+","+"\"is_active\""+":"+is_active+","+"\"role\""+":"+"\""+roleName+"\""+"}";
		
		this.rendJson(true, 200, "添加成功",data);
		}
		
		
	}
	public void edit(){
		String username=getPara("username");
		User user=User.dao.findByEmail(getSessionAttr("username").toString(), username);
		setAttr("user", user);
		this.rendJson(true, 200, "添加成功",user);
		
	}
	public void update(){
		int uid=getParaToInt("uid");
		String username=getPara("username");
		String email=getPara("email");
		Long space=getParaToLong("space");
		//String head_photo_path=getPara("head_photo_path");
		int is_active=getParaToInt("is_active");
		int roleId=getParaToInt("role_id");
		User m = User.dao.findByEmail(email, username);
		if (m != null&&m.getInt("id")!=uid) {
			this.rendJson(false, null, "用户名已经存在");
		}else{
			String sql="update user set username=?,email=?,space=?,is_active=? where id=?";
			Db.update(sql, username,email,space,is_active,uid);
			Db.update("update u_role_user set role_id=? where user_id=?",roleId,uid);
			String roleName=Role.dao.findById(roleId).getStr("name");
			String data="{"+ "\"username\""+":"+"\""+username+"\""+","+"\"email\""+":"+"\""+email+"\""+","
					+"\"space\""+":"+space+","+"\"is_active\""+":"+is_active+","+"\"role\""+":"+"\""+roleName+"\""+"}";
			this.rendJson(true,200,"更新成功",data);
		}
	}
	public void del(){
		String username=getPara("username");
		User user=User.dao.findByName(username);
		String fileId=user.getStr("file_id");
		int userId=user.getInt("id");
		fileService.delDir(fileId, getSessionAttr("email").toString());
		RoleUser.dao.delUserById(userId);
		user.delete();
		this.rendJson(true, null, "删除成功");
		
	}

}
