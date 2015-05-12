
    var zTree;
    var demoIframe;

    var setting = {
        check: {
            enable: true
        },
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        async: {
        enable: true,
        url: rootPath+"/admin/resc/getRescList",
        autoParam: ["id"],
        dataType: "text",  
        dataType: "json",
        type: "post"
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pid",
                rootId: ""
            }
        },
            callback : {    
            onClick : function(event, treeId, treeNode, clickFlag) {    
                if(true) {  
                    alert(" 节点id是：" + treeNode.id + ", 节点文本是：" + treeNode.name);        
  
                }    
                  
            },    
            //捕获异步加载出现异常错误的事件回调函数 和 成功的回调函数    
            onAsyncSuccess : function(event, treeId, treeNode, msg){    
                //  alert("调用成功！");  
               //var nodes=getCheckedNodes(true);  
                //alert(nodes);  
            },  
            beforeClick: beforeClick,  
            onCheck: onCheck  
        }    
    };

     function beforeClick(treeId, treeNode) {  
        var zTree = $.fn.zTree.getZTreeObj("tree");  
        zTree.checkNode(treeNode, !treeNode.checked, null, true);  
        return false;  
    }  
     function onCheck(e,treeId,treeNode)  
     {  
    	zTree1 = $.fn.zTree.getZTreeObj("tree1");   
    	zTree2 = $.fn.zTree.getZTreeObj("tree2");    
        nodes1 = zTree1.getCheckedNodes(true); 
        nodes2 = zTree2.getCheckedNodes(true);  
        var ids1="";  
        for (var i=0, l=nodes1.length; i<l; i++) {  
            ids1+=nodes1[i].id+",";  
        }  
          
        if (ids1.length > 0 ) ids1 = ids1.substring(0, ids1.length-1); 
        var ids2="";  
        for (var i=0, l=nodes2.length; i<l; i++) {  
            ids2+=nodes2[i].id+",";  
        }  
          
        if (ids2.length > 0 ) ids2 = ids2.substring(0, ids2.length-1);  
        $("#ids").val(ids1);
        $("#edit_ids").val(ids2);
        alert(ids2);
        } 
    
	  

    
    $(document).ready(function(){
    	var zNodes;
    	var t1= $("#tree1");
    	var t2=$("#tree2");
  		$.ajax({
            url : rootPath+"/admin/resc/getRescList?id=0",
            data : {},
            type : 'GET',
            dataType : "json",
            success : function(json) {				
            	zNodes=json;
            	t1 = $.fn.zTree.init(t1, setting,zNodes);
            	t2= $.fn.zTree.init(t2, setting,zNodes);
            },
            error: function (msg) {

                alert(" 数据加载失败！" + msg);
            } 
        });
        demoIframe = $("#testIframe");
        demoIframe.bind("load", loadReady);
        var zTree1 = $.fn.zTree.getZTreeObj("tree1");
  

    });

    function loadReady() {
        var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
                htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
                maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
                h = demoIframe.height() >= maxH ? minH:maxH ;
        if (h < 530) h = 530;
        demoIframe.height(h);
    }