package cn.ibox.ctrl.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.LoginLog;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;

@ControllerBind(controllerKey="/admin/loginlog")
public class LoginLogCtrl extends BaseController<LoginLog> {
	public void index() {
		this.renderHTML("index.html");
	}

	public void getLogList() {
		String sEcho = getPara("sEcho");
		int start = getParaToInt("iDisplayStart");
		int length = getParaToInt("iDisplayLength");
		List<LoginLog> logList = LoginLog.dao.findAll();
		List<LoginLog> operateList = new ArrayList<LoginLog>();
		int total = logList.size();
		if (logList.size() == 0) {
			this.renderJson("[]");
		} else {
			if (start + length > total) {
				operateList = logList.subList(start, total);
			} else {
				operateList = logList.subList(start, start + length);
			}
			Object[][] data = new Object[length][5];
			for (int i = 0; i < operateList.size(); i++) {
				if (null == operateList.get(i))
					break;
				data[i][0]=operateList.get(i).getInt("id");
				data[i][1] = operateList.get(i).getStr("username");
				data[i][2] = operateList.get(i).getDate("login_time");
				data[i][3] = operateList.get(i).getStr("login_ip");
				data[i][4] = "<a href='#'  onclick='javascript:delLog(this);'>删除</a>";
			}
			List<Object[]> list = Arrays.asList(data);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aaData", list);
			map.put("iTotalRecords", total);
			map.put("iTotalDisplayRecords", total);
			map.put("sEcho", sEcho);
			this.renderJson(map);
		}

	}

	public void del() {
		int id=getParaToInt("id");
		LoginLog.dao.deleteById(id);
		this.rendJson(true, 200, "delete success");

	}
	public void delAll(){
		Db.update("delete from u_login_log where id != ?", "null");
		this.rendJson(true, 200, "delete success");
		
	}
}
