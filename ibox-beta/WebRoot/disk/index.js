 $(function() {
	 
           $("#checkAll").click(function() {
        	   var subBox=$('#dataiframe').contents().find("input[name='subBox']");
        	   subBox.attr("checked",this.checked);
              
            });
           
           $("#newDir").click(function  (argument) {
        	   var $iframe=$('#dataiframe').contents();
        	   var $firstLi=$iframe.find('ul li:first-child');
        	   var content="<li><li><li class='tile ui-sortable-handle'><div class='checkbox checkbox-styled tile-text'><div class='col-xs-8'><label><input type='checkbox' name='subBox' id='subBox'><span ><img src="+rootPath+"/assets/images/icon/dir.png width='30px' height='30px'/></span><input name='dirName' id='dirName' type='text' autofocus='autofocus' value='新建文件夹'style='margin-left:10px;'/><button class='addDir'><i class='fa fa-check'></i></button><button class='remDir'><i class='fa fa-remove'></i></button></label></div><div class='col-xs-2'><span style=''></span></div><div class='col-xs-2'><span></span></div></div></li><li><li>";
        	   $(content).insertBefore($firstLi);
        	   $iframe.find('.remDir').click(function(){
        		  var $li=$(this).parent().parent().parent().parent();
        		  $li.next('li').remove();
        		  $li.remove();
        	   });
        	   $iframe.find('.addDir').click(function(){
        		  var dirName=$(this).prev().val();
        		  if(dirName==""){
        			  alert("文件名不能为空");
        			  return false;
        		  }
        		  var $li=$(this).parent().parent().parent().parent();
        		  $li.next('li').remove();
        		  $li.remove();
        		  $.ajax({
        	             url :rootPath+"/disk/mkDir",
        	             data : {"dirName":dirName,"dirId":dir_id},
        	             type : 'POST',
        	             dataType : "json",
        	             success : function(responseJSON) {
        	              if(responseJSON.success==true){
        	            	  var $iframe=$('#dataiframe').contents();
                     		 var $firstLi=$iframe.find('ul li:first-child');
                     		 var img;
                     		 img="<img alt='' src='"+rootPath+"/assets/images/icon/"+responseJSON.data.type+".png' width='30px' height='30px'>";
                     		 var content="<li><li><li class='tile ui-sortable-handle'><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+responseJSON.data.id+"'><input type='hidden' name='fileType' id='fileType' value='"+responseJSON.data.type+"'><span>"+img+"<a href='#' onclick='listDir(this)'>"+responseJSON.data.name+"</a></span><input  id='fileName' type='hidden' value='"+responseJSON.data.name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>-</span></div><div class='col-xs-2'><span>"+responseJSON.data.upload_time+"</span></div></div></li><li><li>";
                     		 $(content).insertBefore($firstLi);
        	              }else{
        	            	  alert("文件夹创建失败");
        	              } 
        	             }		
        	             });
         	   });
        	  
       	});
        $('#delSubmit').click(function(){
        	 var $iframe=$('#dataiframe').contents();
        	  var adIds = "";  
        	  var types="";
              $iframe.find("input[type=checkbox]:checked").each(function(i){  
                 adIds += (","+$(this).val());  
                 types +=(","+$(this).next().val());
             });
              if(adIds==""){
            	  alert("请选择要删除的选项 ");
            	  return false;
              }
              $.ajax({
 	             url :rootPath+"/disk/delMutipli",
 	             data : {"adIds":adIds,"types":types},
 	             type : 'POST',
 	             dataType : "json",
 	             success : function(json) {
 	              if(json.success==true){
 	            	 $iframe.find("input[type=checkbox]:checked").each(function(i){  
 	                   var $li=$(this).parent().parent().parent().parent();
 	                   $li.next().remove();
 	                   $li.remove();
 	                });
 	            	 $("#delModal").modal('hide');
 	            	 
 	              }else{
 	            	  alert("文件删除失败");
 	              } 
 	             }		
 	             });
        });   
        });
 function guide(href,obj){
	 $obj=$(obj);
	 var $iframe=$('#dataiframe').contents();
	 $.ajax({
			url:href,
			data:{},
			dataType:"json",
			method:"GET",
			success:function(json){
				if(json.success==true){
					$(".guide",parent.document).html(json.data.guide);
					var len=json.data.dataList.length;
					var content="<li><li>";
					if(len==0){
						$iframe.find("ul").html('<li class="divider-full-bleed"></li>');
					}else{
						for(var i=0;i<len;i++){
							var id=json.data.dataList[i].attrValues[5];
							var type=json.data.dataList[i].attrValues[6];
							var category=json.data.dataList[i].attrValues[7];
							var name=json.data.dataList[i].attrValues[3];
							var size=json.data.dataList[i].attrValues[1];
							var upload_time=json.data.dataList[i].attrValues[0];
							var img;
							if(type=="dir"){
								img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/dir.png' width='30px' height='30px'/>";
							}else if(category==4){
								img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/file.png' width='30px' height='30px'/>";
							}else{
								img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/"+type+".png' width='30px' height='30px'/>";
							}
							content+="<div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+id+"'><input type='hidden' name='fileType' id='fileType' value='"+type+"'><span >";
							content+=img;
							if(type=="dir"){
								content+="<a href='#' onclick='listDir(this)'>"+name+"</a></span>";
							}else{
								content+=name+"</span>";
							}
							var sizeStr;
							if(type=="dir"){
								sizeStr="-";
							}else{
								sizeStr=size+"b";
							}
							content+="<input  id='fileName' type='hidden' value='"+name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+sizeStr+"</span></div><div class='col-xs-2'><span>"+upload_time+"</span></div></div></li><li><li>";
							
							 
						}
						$iframe.find("ul").html(content);
					}
				}else{
					alert("获取数据失败");
				}
			}
			
		});
 }
 
    $('#moveModal').on('show.bs.modal', function (e) {
		 $('#treeiframe').attr('src', $('#treeiframe').attr('src'));
		});
     $("#moveConfirm").click(function(){
      var $iframe=$('#dataiframe').contents();
   	  var adIds = "";  
   	  var types="";
   	  $iframe.find("input[type=checkbox]:checked").each(function(i){  
   	        adIds += (","+$(this).val());  
   	        types +=(","+$(this).next().val());
   	    });
        if(adIds==""){
      	  alert("请选中要移动的选项");
      	  return ;
        }
       
    	 var toDirId=$('#treeiframe').contents().find("#toPath").val(); 
    	 if(toDirId==""){
    		 alert("请选择要移动的目的文件夹");
    	 }
   
    	 $("#moveConfirm").attr("data-dismiss","modal");
    	 var index = layer.load(1);
    	 $.ajax({
        	 url:rootPath+"/disk/move",
        	 data:{"ids":adIds,"types":types,"toDirId":toDirId},
        	 dataType:"json",
        	 method:"POST",
        	 success:function(json){
        		 if(json.success==true){
        			 $iframe.find("input[type=checkbox]:checked").each(function(i){  
        			      var $li=$(this).parent().parent().parent().parent();
        			      $li.next().remove();
        			      $li.remove();
        			    });
        			
        			 layer.close(index);
        			 layer.msg('移动成功', {
        				    icon: 1,
        				    time: 2000 //2秒关闭（如果不配置，默认是3秒）
        				});
        			
        		 }else{
        			 layer.close(index);
        			 layer.msg('移动失败', {
     				    icon: 2,
     				    time: 2000 //2秒关闭（如果不配置，默认是3秒）
     				});
        			
        		 }
        	 }
        	 
         });
    	
    	  
     });
	 
$("#download").click(function(){
	var adIds = "";  
 	  var types="";
 	 var $iframe=$('#dataiframe').contents();
 	  $iframe.find("input[type=checkbox]:checked").each(function(i){  
 	        adIds += (","+$(this).val());  
 	        types +=(","+$(this).next().val());
 	    });
 	  
      if(adIds==""){
    	  alert("请选择要移动的选项 ");
    	  return ;
      }
      var url=rootPath+"/disk/downloadMutipli?ids="+adIds+"&types="+types;
      window.open(url,'下载',"fullscreen=1");
      
});


