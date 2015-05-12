/**
 * Created by cherry on 15-3-7.
 */
var codeUrl=rootPath+"/ValidCode/jpg?id=";
var model = avalon.define({
    $id : "registCtrl",
    data : {
        email:"",
        username:"",
        password:"",
        confirmPassword:"",
        validCode: ""
    },
    errMsg : "",
    vcUrl:codeUrl,
    ldCode:function(){
        model.vcUrl=codeUrl+(new Date()).getTime();
    },
    submit : function() {
    	var checkbox=document.getElementById("check");
        model.errMsg ="";
        if (model.data.username.length<5||model.data.username.length>25) {
            model.errMsg = "用户名长度在5-25字符之间";
            return;
        }
        if (model.data.email== "") {
            model.errMsg = "email不能为空";
            return;
        }
        if(!model.data.email.match(/\w@\w*\.\w/)){
        	model.errMsg = "email格式不正确";
            return;
        }
        if (model.data.password == ""||model.data.password.length<6) {
            model.errMsg = "密码长度不能少于6位";
            return;
        }
        if (model.data.confirmPassword == ""||model.data.confirmPassword.length<6) {
            model.errMsg = "确认密码长度不能少于6位";
            return;
        }
        if(!checkbox.checked){
        	model.errMsg="请先同意协议";
        	return;
        }
        if (!model.data.confirmPassword==model.data.password) {
            model.errMsg = "两次输入密码不一致";
            return;
        }
        
        if (model.data.validCode == "") {
            model.errMsg = "验证码不能为空";
            return;
        }
        $.ajax({
            url : rootPath + '/register',
            data : model.data.$model,
            type : 'POST',
            dataType : "json",
            success : function(data) {				if (data.success == false) {
                $("#validCodeImg").click();
                model.data.validCode = "";
                model.data.password = "";
                model.data.confirmPassword = "";
                model.errMsg = data.msg;
            } else {
                window.location.href=rootPath+"/";
            }
            }
        });
    }
});
