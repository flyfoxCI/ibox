package ibox.util.valid;

import ibox.util.safe.CipherUtil;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
@ClearInterceptor
public class LoginValid extends BaseValid{
	
	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		String check=c.getCookie("ValidCode");
		String validCode=c.getPara("validCode");
		if(StringUtils.isEmpty(check)){
			addError("validCodeMsg","验证码已超时，请重新获取！");
		}else{
			if(!(CipherUtil.decryptData(check)).equals(validCode.toLowerCase())){
				addError("validCodeMsg","验证码不匹配");
			}
		}
		c.setCookie("ValidCode","",0);	
		
	}
	

	}
