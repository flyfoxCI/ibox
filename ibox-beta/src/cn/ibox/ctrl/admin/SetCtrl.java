package cn.ibox.ctrl.admin;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.Role;

import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey="/admin/set")
public class SetCtrl extends BaseController<Role>{
	public void index(){
		setAttr("parentUrl", "/admin/set");
		this.forwardAction("/admin/role");
	}

}
