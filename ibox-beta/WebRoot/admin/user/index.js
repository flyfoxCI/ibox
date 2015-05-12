avalon.config({
loader: false
});
 var model1 = avalon.define({
     $id : "addUserCtrl",
     data : {
    	 username :"",
    	 role_id:"",
    	 email:"",
    	 space:"",
    	 active:"",
         password:""
         
         
     },  
     errMsg : "",
     submit : function() {
    	 var is_active=$("input[name='is_active']:checked").val();
         model1.errMsg ="";
         model1.data.active=is_active;
         if (model1.data.username == "") {
             model1.errMsg = "用户名不能为空";
             return;
         }
         if(username.length<5||username.length>25){
    		 $("#errMsg").text("用户名在5-25字符之间");
    		 return;
    	 }
         if (model1.data.role_id== "") {
             model1.errMsg = "用户角色不能为空";
             return;
         }
         if (model1.data.email== "") {
             model1.errMsg = "email不能为空";
             return;
         }
         if (!model1.data.email.match(/\w@\w*\.\w/)) {
             model1.errMsg = "email格式不匹配";
             return;
         }
         if(model1.data.space==""){
        	 model1.errMsg = "请输入空间大小";
             return;
         }
         if(!model1.data.space.match(/^[0-9]*$/)){
        	 model1.errMsg = "空间大小请输入数字";
             return;
         }
         if (model1.data.active== "") {
             model1.errMsg = "请选择是否激活";
             return;
         }
         if (model1.data.password == ""||model1.data.password.length<6) {
             model1.errMsg = "密码长度不能少于6位";
             return;
         }
         $.ajax({
             url :rootPath+"/admin/user/add",
             data : model1.data.$model,
             type : 'POST',
             dataType : "json",
             success : function(json) {				
             	if (json.success == false) {
                 model1.data.password = "";
                 model1.errMsg = json.msg;
                 
             } else {
            	
            	 model1.data.password = "";
            	 model1.data.username="";
            	 model1.data.email="";
            	 model1.data.space="";
            	 model1.data.role_id="";
            	 model1.data.active="";
            	 var jsonObj=JSON.parse(json.data);
          		$('#editable').dataTable().fnAddData([jsonObj.username,
		                                    jsonObj.email,
		                                   jsonObj.space,
		                                  jsonObj.is_active,
		                                  jsonObj.role,
		                                  "<a href='#'  onclick='javascript:editUser(this);'>编辑</a>|<a href='#'  onclick='javascript:delUser(this);'>删除</a>"
          		   ]);
          		 $("#addModal").hide("modal");
          		
          		   
             	    
             		
             }
             }
         });
         
         
     }
 });
function editUser(obj){
	var username=obj.parentNode.parentNode.firstChild.innerText;
	var trIndex=obj.parentNode.parentNode.rowIndex;
	 $.ajax({
         url :rootPath+"/admin/user/edit?username="+username,
         data :"",
         type : 'GET',
         dataType : "json",
         success : function(json) {				
         	if (json.success == false) {
         		layer.msg('查询失败，请检查服务器', {
            	    icon: 8,
            	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
            	}, function(){
            	    //do something
            	}); 
         		return;
         } else {
        	 $("#uid").val(json.data.id);
        	 $("#editUsername").val(json.data.username);
        	$("#editRoleId").attr("value",json.data.role_id);
           	$("#editEmail").val(json.data.email);
        	$("#editSpace").val(json.data.space);
        	 $("#editUserModal").modal('show');
        	 $("#editSubmit").click(function(){
        		 var uid=$("#uid").val();
            	 var username=$("#editUsername").val();
            	 var role_id=$("#editRoleId").val();
            	 var email=$("#editEmail").val();
            	 var space=$("#editSpace").val();
            	 var is_active=$("input[name='editActive']:checked").val();
            	 if(username==""){
            		 $("#errMsg").text("用户名不能为空");
            		 return;
            	 }
            	 if(username.length<5||username.length>25){
            		 $("#errMsg").text("用户名在5-25字符之间");
            		 return;
            	 }
            	 if(role_id==""){
            		 $("#errMsg").text("用户角色不能为空");
            		 return;
            	 }
            	 if(is_active==""){
            		 $("#errMsg").text("是否激活不能为空");
            		 return;
            	 }
            	 if(email==""){
            		 $("#errMsg").text("email不能为空");
            		 return;
            	 }
            	 if(space==""){
            		 $("#errMsg").text("用户空间不能为空");
            		 return;
            	 }
            	 if(!space.match(/^[0-9]*$/)){
            		 $("#errMsg").text("请填写数字");
            		 return;
            	 }
            	 if(!email.match(/\w@\w*\.\w/)){
            		 $("#errMsg").text("请填写正确的邮件地址");
            		 return;
            	 }
            	 $.ajax({
    	             url :rootPath+"/admin/user/update",
    	             data : {"uid":uid,"username":username,"role_id":role_id,"email":email,"space":space,"is_active":is_active},
    	             type : 'POST',
    	             dataType : "json",
    	             success : function(json) {				
    	             	if (json.success == false) {
    	                 $("#errMsg").val(json.msg);
    	                 
    	             } else {
    	            	 var jsonObj=JSON.parse(json.data);
    	          		 $('#editable').dataTable().fnUpdate([jsonObj.username,
    			                                    jsonObj.email,
    			                                   jsonObj.space,
    			                                  jsonObj.is_active,
    			                                  jsonObj.role,
    			                                  "<a href='#'  onclick='javascript:editUser(this);'>编辑</a>"
    	          		   ],trIndex);
    	          		 $("#uid").val();
    	            	 $("#editUsername").val();
    	            	$("#editRoleId").attr("value","");
    	            	$("#editEmail").val();
    	            	$("#editSpace").val();
            	        
    	          		 $("#editUserModal").modal('hide');
    	          		
    	          		   
    	             	    
    	             		
    	             }
    	             }
    	         });
        	 });
        	
        	
        	 
         }
         }
     });
}
function delUser(obj){
	var username=obj.parentNode.parentNode.firstChild.innerText;
	var trIndex=obj.parentNode.parentNode.rowIndex;
	$("#delUserModal").modal('show');
	$("#delSubmit").click(function(){
		  $.ajax({
	             url :rootPath+"/admin/user/del",
	             data : {"username":username},
	             type : 'POST',
	             dataType : "json",
	             success : function(json) {				
	             	if (json.success == false) {
	                 $('#delMsg').val(json.msg);
	                 
	             } else {
	            	 $("#delUserModal").modal('hide');
	          		 $('#editable').dataTable().fnDeleteRow(trIndex);		             	    
	             	 
	             }
	             }
	         });
	});
	
}
 