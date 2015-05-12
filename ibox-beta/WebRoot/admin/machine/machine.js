avalon.config({
loader: false
});
 var model = avalon.define({
     $id : "addMacCtrl",
     data : {
         ip:"",
         password:"",
         hostname : "",
         username :"",
         role:""
     },
     errMsg : "",
     submit : function() {
         model.errMsg ="";
         if (model.data.ip== "") {
             model.errMsg = "ip不能为空";
             return;
         }
         if(!model.data.ip.match(/^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/)){
         	model.errMsg = "ip格式不正确";
             return;
         }
         if (model.data.username == "") {
             model.errMsg = "用户名不能为空";
             return;
         }
         if (model.data.hostname == "") {
             model.errMsg = "主机名不能为空";
             return;
         }
         if (model.data.password == ""||model.data.password.length<6) {
             model.errMsg = "密码长度不能少于6位";
             return;
         }
         $('#addModal').modal("hide");
         var index = layer.load(1); 
         $.ajax({
             url :rootPath+"/admin/machine/add",
             data : model.data.$model,
             type : 'POST',
             dataType : "json",
             success : function(json) {				
             	if (json.success == false) {
                 model.data.password = "";
                 layer.msg('部署失败', {
                	    icon: 8,
                	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                	}, function(){
                	    //do something
                	}); 
                 layer.close(index);
                 
             } else {
            	 layer.close(index);
            	 layer.msg('部署成功', {
             	    icon: 1,
             	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
             	}, function(){
             	    //do something
             		var jsonObj=JSON.parse(json.data);
             		$('#editable').dataTable().fnAddData([   jsonObj.ip,
		                                    jsonObj.hostname,
  		                                   jsonObj.username,
  		                                  jsonObj.role,
  		                                     "<a href='#' onclick='javascript:delHost(this);'>删除</a>"
             		                            ]);
             		
             	}); 
             }
             }
         });
         
         
     }
 });
 function delHost(obj){
	 var ip=obj.parentNode.parentNode.firstChild.innerText;
	 var trIndex=obj.parentNode.parentNode.rowIndex;
	 var index = layer.load(1);
	    $.ajax({
	url :rootPath+"/admin/machine/del?ip="+ip,
	type : 'GET',
	dataType : "json",
	success : function(json) {              
	    if (json.success == false) {
	    layer.close(index);
	    layer.msg('删除主机失败', {
	        icon: 8,
	        time: 2000 //2秒关闭（如果不配置，默认是3秒）
	    }, function(){
	        //do something
	    }); 
	   
	    
	} else {
	 layer.close(index);
	 layer.msg('删除成功', {
	        icon: 1,
	        time: 2000 //2秒关闭（如果不配置，默认是3秒）
	    }, function(){
	        //do something
	    	 layer.close(index);
	    	$('#editable').dataTable().fnDeleteRow(trIndex);;
	       
	});
	}
	}
	});
 }
 

 

 

      
      