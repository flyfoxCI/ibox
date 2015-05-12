package cn.ibox.ctrl.admin;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import ibox.util.safe.MD5;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.User;
@ControllerBind(controllerKey="/admin/info")
@RequiresRoles(value={"admin","manager"},logical=Logical.OR)
public class InfoCtrl extends BaseController<User>{
	public void index(){
		this.renderHTML("index.html");
	}

	public void update(){
		String username=getPara("username");
		String password=getPara("password");
		String email=getPara("email");
		String uid=getSessionAttr("uid");
		String sql="update user set username=?,email=?,password=? where id=?";
		Db.update(sql,username,email,MD5.getMD5ofStr(password),uid);
		this.rendJson(true, 200, "更新成功");
	}
}
