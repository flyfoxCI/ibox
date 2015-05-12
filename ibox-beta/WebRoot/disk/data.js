 $(function() {
     
    
        });
function listDir(obj){
	var $obj=$(obj);
	var dirId=$obj.parent().prev().prev().val();
	var name=$obj.html();
	window.parent.dir_id=dirId;
	window.parent.manualUploader._options.request.params.dir=dirId;
	$.ajax({
		url:window.parent.rootPath+"/disk/list",
		data:{"dirId":dirId},
		dataType:"json",
		method:"POST",
		success:function(json){
			if(json.success==true){
				$(".guide",parent.document).html(json.data.guide);
				var len=json.data.dataList.length;
				var content="<li><li>";
				if(len==0){
					$("ul").html('<li class="divider-full-bleed"></li>');
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
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/dir.png' width='30px' height='30px' />";
						}else if(category==4){
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/file.png' width='30px' height='30px'/>";
						}else{
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/"+type+".png' width='30px' height='30px'/>";
						}
						var nameStr="";
						if(type=="dir"){
							nameStr="<a href='#' onclick='listDir(this)'>"+name+"</a></span>";
						}else{
							nameStr=name+"</span>";
						}
						var sizeStr;
						if(type=="dir"){
							sizeStr="-";
						}else{
							sizeStr=size+"b";
						}
						content=content+"<li class='tile ui-sortable-handle' ><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+id+"'><input type='hidden' name='fileType' id='fileType' value='"+type+"'><span>"+img+nameStr+"</span><input  id='fileName' type='hidden' value='"+name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+sizeStr+"</span></div><div class='col-xs-2'><span>"+upload_time+"</span></div></div></li><li></li>";
						
						 
					}
					$("ul").html(content);
				}
			}else{
				alert("获取数据失败");
			}
		}
		
	});
	
	
}
function editFile(obj){
	var label=obj.parentNode.parentNode.parentNode.children[0].children[0];
	var $fileName=$(label.children[3]);
	var innerhtml=$(label.children[2]).html().trim();
	var fileType=label.children[1].value;
	var content="";
	var innertext="";
	if(fileType=="dir"){
		content=innerhtml.substr(0,innerhtml.indexOf("<a href"));
	}else{
		 innertext=$(label.children[2]).text().trim();
		 content=innerhtml.substr(0,innerhtml.indexOf(innertext));
	}
	
	$(label.children[2]).html("");
	var id=label.children[0].value;
	
	$(label).append("<button class='editName'><i class='fa fa-check'></i></button><button class='cancel'><i class='fa fa-remove'></i></button>");
	$fileName.attr("style","width:360px");
	$fileName.attr("type","text");
	$(".editName").click(function(){
		layer.msg('正在修改名字');
		var fileName=$fileName.val();
		$.ajax({
			url:window.parent.rootPath+"/disk/editSave",
			data:{"name":fileName,"fileId":id,"fileType":fileType},
			dataType:"json",
			method:"POST",
			success:function(json){
				if(json.success==true){
					layer.msg('修改成功', {icon: 6});
					if(fileType=="dir"){
						content=content+'<a href="#" onclick="listDir(this)">'+fileName+'</a>';
					}else{
						content=content+fileName;
					}
					
					$(label.children[2]).html(content);
				}else{
					layer.msg('修改失败', {icon: 8});
					$(label.children[2]).html(innerhtml);
				}
			}
		});
		$(this).prev().attr("type","hidden");
		$(label.children[2]).html(innerhtml);
		$(this).next().remove();
		$(this).remove();
	});
	$(".cancel").click(function(){
		$(this).prev().prev().attr("type","hidden");
		$(label.children[2]).html(innerhtml);
		$(this).prev().remove();
		$(this).remove();
		
	});
	
	
}
function moveSingle(obj){
 var label=obj.parentNode.parentNode.parentNode.children[0].children[0];
 if($(label.children[0]).prop('checked')==false){
	 $(label.children[0]).attr("checked","checked");	 
 }
 var adIds=","+label.children[0].value;
 var types=","+label.children[1].value;
 $('#treeiframe',parent.document).attr('src', $('#treeiframe',parent.document).attr('src'));
 $("#moveModal",parent.document).modal('show');

 
 
}
function deleteSingle(obj){
	var label=obj.parentNode.parentNode.parentNode.children[0].children[0];
	var id=label.children[0].value;
	var fileType=label.children[1].value;
	var index = layer.load(1);
	$.ajax({
		url:window.parent.rootPath+"/disk/delFile",
		data:{"fileId":id,"fileType":fileType},
		dataType:"json",
		method:"POST",
		success:function(json){
			if(json.success==true){
				layer.close(index);
				layer.msg('删除成功', {icon: 6});
				 var $li=$(obj).parent().parent().parent().parent();
			     $li.next().remove();
			     $li.remove();
			}else{
				layer.close(index);
				layer.msg('删除失败', {icon: 8});
				
			}
		}
	});
	$(this).prev().attr("type","hidden");
	$(label.children[2]).html(innerhtml);
	$(this).next().remove();
	$(this).remove();

}
function listBycate(href){
	var dirId=href.substring(href.indexOf("&")+7);
	window.parent.manualUploader._options.request.params.dir=dirId;
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
					$("ul").html('<li class="divider-full-bleed"></li>');
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
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/dir.png' width='30px' height='30px' />";
						}else if(category==4){
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/file.png' width='30px' height='30px'/>";
						}else{
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/"+type+".png' width='30px' height='30px'/>";
						}
						var nameStr="";
						if(type=="dir"){
							nameStr="<a href='#' onclick='listDir(this)'>"+name+"</a></span>";
						}else{
							nameStr=name+"</span>";
						}
						var sizeStr;
						if(type=="dir"){
							sizeStr="-";
						}else{
							sizeStr=size+"b";
						}
						content=content+"<li class='tile ui-sortable-handle' ><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+id+"'><input type='hidden' name='fileType' id='fileType' value='"+type+"'><span>"+img+nameStr+"</span><input  id='fileName' type='hidden' value='"+name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+sizeStr+"</span></div><div class='col-xs-2'><span>"+upload_time+"</span></div></div></li><li></li>";
						
						 
					}
					$("ul").html(content);
				}
			}else{
				alert("获取数据失败");
			}
		}
		
	});
	
}
function search(dirId){
	var keyword=$("#keyword",parent.document).val();
	if(keyword==""){
		alert("请输入关键字");
		return false;
	}
	window.parent.manualUploader._options.request.params.dir=dirId;
	$.ajax({
		url:window.parent.rootPath+"/disk/search",
		data:{"keyword":keyword},
		dataType:"json",
		method:"POST",
		success:function(json){
			if(json.success==true){
				$(".guide",parent.document).html(json.data.guide);
				var len=json.data.dataList.length;
				var content="<li><li>";
				if(len==0){
					$("ul").html('<li class="divider-full-bleed"></li>');
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
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/dir.png' width='30px' height='30px' />";
						}else if(category==4){
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/file.png' width='30px' height='30px'/>";
						}else{
							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/"+type+".png' width='30px' height='30px'/>";
						}
						var nameStr="";
						if(type=="dir"){
							nameStr="<a href='#' onclick='listDir(this)'>"+name+"</a></span>";
						}else{
							nameStr=name+"</span>";
						}
						var sizeStr;
						if(type=="dir"){
							sizeStr="-";
						}else{
							sizeStr=size+"b";
						}
						content=content+"<li class='tile ui-sortable-handle' ><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+id+"'><input type='hidden' name='fileType' id='fileType' value='"+type+"'><span>"+img+nameStr+"</span><input  id='fileName' type='hidden' value='"+name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+sizeStr+"</span></div><div class='col-xs-2'><span>"+upload_time+"</span></div></div></li><li></li>";	 
					}
					$("ul").html(content);
					var url=window.parent.rootPath+"/disk/search";
					var pageNum=json.data.pageNum;
					var params="{"+'keyword'+':'+keyword+','+'pageNum'+':'+pageNum+"}";
					scrollListen(url,params);
				}
			}else{
				//$(".guide",parent.document).html(json.data.guide);
				$("ul").html("<li>没有找到相关结果<li>");
			}
		}
		
	});
	return false;
	
}
function scrollListen(url,params){
	$(window).scroll(function () {
        //$(window).scrollTop()这个方法是当前滚动条滚动的距离
        //$(window).height()获取当前窗体的高度
        //$(document).height()获取当前文档的高度
        var bot = 50; //bot是底部距离的高度
        if ((bot + $(window).scrollTop()) >= ($(document).height() - $(window).height())) {
           //当底部基本距离+滚动的高度〉=文档的高度-窗体的高度时；
            //我们需要去异步加载数据了
        	$.ajax({
        		url:url,
        		data:params,
        		dataType:"json",
        		method:"POST",
        		success:function(json){
        			if(json.success==true){
        				$(".guide",parent.document).html(json.data.guide);
        				var len=json.data.dataList.length;
        				var content="<li><li>";
        				if(len==0){
        					$("ul").html('<li class="divider-full-bleed"></li>');
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
        							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/dir.png' width='30px' height='30px' />";
        						}else if(category==4){
        							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/file.png' width='30px' height='30px'/>";
        						}else{
        							img="<img alt='' src='"+window.parent.rootPath+"/assets/images/icon/"+type+".png' width='30px' height='30px'/>";
        						}
        						var nameStr="";
        						if(type=="dir"){
        							nameStr="<a href='#' onclick='listDir(this)'>"+name+"</a></span>";
        						}else{
        							nameStr=name+"</span>";
        						}
        						var sizeStr;
        						if(type=="dir"){
        							sizeStr="-";
        						}else{
        							sizeStr=size+"b";
        						}
        						content=content+"<li class='tile ui-sortable-handle' ><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+id+"'><input type='hidden' name='fileType' id='fileType' value='"+type+"'><span>"+img+nameStr+"</span><input  id='fileName' type='hidden' value='"+name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+sizeStr+"</span></div><div class='col-xs-2'><span>"+upload_time+"</span></div></div></li><li></li>";
        						
        						 
        					}
        					$("ul").append(content);
        				}
        			}else{
        				
        			}
        		}
        		
        	});
           
        }
    });
}





