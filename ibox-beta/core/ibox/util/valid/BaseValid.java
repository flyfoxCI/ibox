package ibox.util.valid;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BaseValid extends Validator{

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		Map<String, Object> responseM=new HashMap<String, Object>();
		StringBuilder msg=new StringBuilder();
		Enumeration<String>  en =c.getParaNames();
	        while (en.hasMoreElements()) {
	            //错误键，我们规定，所有的错误为 请求字段加上 Msg 
	            String key = en.nextElement().toString()+"Msg";
	           
	            if(c.getAttrForStr(key)!=null){
	                msg.append(c.getAttrForStr(key));
	            }
	             
	        }
	        //这样我们可以将所有的错误作为一个json串返回前端页面
	        responseM.put("success", false);
	        responseM.put("msg", msg.toString());
	        c.renderJson(responseM);
	}

}
