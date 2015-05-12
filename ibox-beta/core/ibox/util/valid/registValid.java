package ibox.util.valid;

import org.apache.commons.lang3.StringUtils;

import ibox.util.safe.CipherUtil;

import com.jfinal.core.Controller;

public class registValid extends BaseValid{

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		this.validateRequired("email", "emailMsg", "email不能为空");
		this.validateRequired("password", "password", "密码不能为空");
		this.validateRequired("confirmPassword", "confirmPasswordMsg", "确认密码不能为空");
		this.validateRequired("validCode", "validCodeMsg", "验证码不能为空");
		String check=c.getCookie("ValidCode");
		String validCode=c.getPara("validCode");
		if(StringUtils.isEmpty(check)){
			this.addError("validCodeMsg", "验证码过期");
			c.setCookie("ValidCode","",0);
		}else if(!(CipherUtil.decryptData(check).equals(validCode.toLowerCase()))){
			this.addError("validCodeMsg", "验证码错误");
		}
		
		
	}

	

}
