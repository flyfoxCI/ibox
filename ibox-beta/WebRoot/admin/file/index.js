 
	  function selectAll(checkbox) {
			$('input[type=checkbox]').prop('checked', $(checkbox).prop('checked'));
		}

		$(".download").click(
				function() {
					var fileId = this.parentNode.parentNode.childNodes[11].value;
					var email = this.parentNode.parentNode.childNodes[13].value;
					var url = rootPath + "/admin/file/download?fileId=" + fileId
							+ "&email=" + email;
					window.open(url);
				});
		$(".del").click(function (argument) {
		    // body...
			 var index = layer.load(1); 
			 var node = $(this).parent('td').parent('tr')[0];
		     var fileId = node.children[5].value;
		     var email = node.children[6].value;
		     var type = node.children[7].value;
		     
		     $
		     .ajax({
		         url : rootPath + "/admin/file/delete",
		         data : {
		             "fileId" : fileId,
		             "email" : email,
		             "fileType" : type
		         },
		         type : 'GET',
		         dataType : "json",
		         success : function(json) {
		             if (json.success = false) {
		            	 layer.msg('删除失败', {
		             	    icon: 8,
		             	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
		             	}, function(){
		             	    //do something
		             	}); 
		              layer.close(index);
		             } else {
		            	 window.location.reload(); 
		            	 layer.msg('删除成功', {
                       	    icon: 1,
                       	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                       	}, function(){
                       	   
                       	});
                         layer.close(index);
		                 

		         }
		     }
		 });
		});
		  $("#delSubmit").click(function(){
		$("#delModal").modal("hide");
		var index = layer.load(1); 
	    var ids="";
	    var emails="";
	    var types="";
	    $("input[name='item']:checked").each(function() {
	        ids=ids+","+$(this).val();
	        var node = $(this).parent('td').parent('tr')[0];
	        emails=emails+","+node.children[6].value;
	        types=types+","+node.children[7].value;
	        
	    });
	    if(ids==""){
	    	alert("请选择要删除的选项");
	    	return;
	    }
	     ids=ids.substring(1,ids.length);
	     emails=emails.substring(1,emails.length);
	     types=types.substring(1,types.length);
	     $
	     .ajax({
	         url : rootPath + "/admin/file/deleteAll",
	         data : {
	             "fileIds" : ids,
	             "emails" : emails,
	             "fileTypes" : types
	         },
	         type : 'GET',
	         dataType : "json",
	         success : function(json) {
	             if (json.success = false) {
	            	 layer.msg('删除失败', {
	             	    icon: 8,
	             	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
	             	}, function(){
	             	    //do something
	             	}); 
	              layer.close(index);
	             } else {
	            	 window.location.reload(); 
	            	 layer.msg('删除成功', {
                   	    icon: 1,
                   	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                   	}, function(){
                   	   
                   	});
                     layer.close(index);
	                 

	         }
	     }
	 });
		  });
$("#search").click(function(){
	var keywords=$("#keywords").val();
	if(keywords==""){
		alert("请输入关键字");
		return;
	}
	window.location.href=rootPath+"/admin/file/search?keywords="+keywords;
});
