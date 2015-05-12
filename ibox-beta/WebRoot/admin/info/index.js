  $("#save").click(function(event) {
            /* Act on the event */
	  var username=$("#username").val();
	  var email=$("#email").val();
	  var password=$("#password").val();
	  var confirmPassword=$("#confirmPassword").val();
	  if(username.length<5){
		  $(".errMsg").text("用户名少于5个字符");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  if(password.length<6){
		  $(".errMsg").text("密码少于6个字符");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  if(confirmPassword.length<6){
		  $(".errMsg").text("确认密码少于6个字符");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  if(email==""){
		  $(".errMsg").text("email不能为空");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  if(!email.match(/\w@\w*\.\w/)){
		  $(".errMsg").text("email格式不正确");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  if(confirmPassword!=password){
		  $(".errMsg").text("两次密码不一样");
		  $(".errMsg").removeClass("hidden");
		  return false;
	  }
	  var index = layer.load(1); 
	  $.ajax({
		  url:rootPath+"/admin/info/update",
	  	  data:{"username":username,"password":password,"email":email},
	  	  method:"POST",
	  	  success:function(json){ 
	  		 if(json.success==true){
	  			 layer.close(index);
	  			layer.msg('保存成功', {
	        	    icon: 1,
	        	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
	        	}, function(){
	        	    //do something
	        	}); 
	  		 }else{
	  			 layer.close(index);
	  			 alert("更新失败");
	  		 }
	  		
	  	  }
	  });

        });