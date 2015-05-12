/**
 * Created by cherry on 15-3-7.
 */
var codeUrl=rootPath+"/ValidCode/jpg?id=";
var model = avalon.define({
    $id : "loginCtrl",
    data : {
        password:"",
        validCode : "",
        username:""
    },
    errMsg : "",
    vcUrl:codeUrl,
    ldCode:function(){
        model.vcUrl=codeUrl+(new Date()).getTime();
    },
    submit : function() {
        model.errMsg ="";
        if(model.data.username.length<5||model.data.username.length>25){
        	model.errMsg = "用户名在5-25个字符之间";
        	 $("#validCodeImg").click();
            return;
        }
        if (model.data.password == ""||model.data.password.length<6) {
            model.errMsg = "密码长度不能少于6位";
            $("#validCodeImg").click();
            return;
        }
        if (model.data.validCode == "") {
            model.errMsg = "验证码不能为空";
            $("#validCodeImg").click();
            return;
        }
        $.ajax({
            url : rootPath + '/login',
            data : model.data.$model,
            type : 'POST',
            dataType : "json",
            success : function(data) {				if (data.success == false) {
                $("#validCodeImg").click();
                model.data.validCode = "";
                model.data.password = "";
                model.errMsg = data.msg;
            } else {
                window.location.reload();
            }
            }
        });
    }
});
