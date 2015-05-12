package cn.ibox.ctrl.base;

import ibox.util.properties.PropertiesContent;
import ibox.util.safe.CipherUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.render.JsonRender;
/*静态变量*/

public class BaseController<M extends Model<M>> extends Controller{
	public Logger log=Logger.getLogger(getClass());
	public static final SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected Class<M> modelClass;
	public void index(){
		String para=this.getPara();
		if(StringUtils.isNotEmpty(para))
			this.renderHTML(para.replaceAll("\\-","/"));
		else{
			String gotoUrl=this.getPara("go");
			if(StringUtils.isNotEmpty(gotoUrl))
			this.renderHTML(gotoUrl);
		}
	}

	@Override
	public void render(String view) {
		// TODO Auto-generated method stub
		setAttr("ctx", getRequest().getContextPath());
		super.render(view);
	}
	protected void renderHTML(String view) {
		if(view.endsWith(".html")){
			super.render(view);
		}else{
			super.render(view+".html");
		}
	}
	protected void rendJson(boolean success,Integer statusCode,String msg,Object... data){
		Map<String,Object>json=new HashMap<String,Object>();
		json.put("success",success);
		json.put("status",success?200:(statusCode==null?401:statusCode));
		json.put("msg",msg);
		if(data!=null&&data.length>0){
			json.put("data",data[0]);
			if(data.length>1){
				json.put("tokenid",data[1]);
			}
		}
		rendJson(json);
	}
	protected void rendJson(Object json){
		String agent = getRequest().getHeader("User-Agent");
		if(agent.contains("MSIE"))
			this.render(new JsonRender(json).forIE());
		else{
			this.render(new JsonRender(json));
		}
	}
	protected String getId(){
		String id = this.getPara(0);
		if (StringUtils.isEmpty(id)) {
			id = this.getPara("id");
		}
		return id;
	}
	public String getCurrentUserId(){
		return  getUserMap().get("uid");
	}
	public String getDir(){
		return getUserMap().get("dir");
	}
	public String isAdmin(){
		return getUserMap().get("is_admin");
	}
	public String getEmail(){
		return getUserMap().get("email");
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getUserMap() {
		Map<String, String> userMap =new HashMap<String,String>();
		String cookieVal = this.getCookie(PropertiesContent.get("cookie_field_key"));
		if (StringUtils.isNotEmpty(cookieVal)){
			cookieVal = CipherUtil.decryptData(cookieVal);
			userMap = (Map<String,String>) JSON.parse(cookieVal);
		}
		return userMap;
	}

}
