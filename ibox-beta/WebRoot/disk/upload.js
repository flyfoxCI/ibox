
       
        var manualUploader = new qq.FineUploader({
            element: document.getElementById('fine-uploader-manual-trigger'),
            template: 'qq-template-manual-trigger',
            request: {
                endpoint: rootPath+'/disk/uploadSave',
                params:{'dir':dir_id},
            },
            thumbnails: {
                placeholders: {
                    waitingPath: '/source/placeholders/waiting-generic.png',
                    notAvailablePath: '/source/placeholders/not_available-generic.png'
                }
            },
            autoUpload: true,
            debug: true,
            callbacks: {
                onComplete : function(id, name, responseJSON,xhr) {
                	if(responseJSON.success==true){
                		 var $iframe=$('#dataiframe').contents();
                		 var $firstLi=$iframe.find('ul li:first-child');
                		 var img;
                		 if(responseJSON.data.category==4){
                			 img="<img alt='' src="+rootPath+"/assets/images/icon/file.png width='30px' width='30px'>";
                		 }else{
                			 img="<img alt='' src='"+rootPath+"/assets/images/icon/"+responseJSON.data.type+".png' width='30px' width='30px'>";
                		 }
                		 var content="<li class='tile ui-sortable-handle'><div class='checkbox checkbox-styled tile-text'><div class='col-xs-7'><label><input type='checkbox' name='subBox' id='subBox' value='"+responseJSON.data.id+"'><input type='hidden' name='fileType' id='fileType' value='"+responseJSON.data.type+"'><span>"+img+responseJSON.data.name+"</span><input  id='fileName' type='hidden' value='"+responseJSON.data.name+"'/></label></div><div class='col-xs-2' id='toolbar'><span><i class='fa fa-edit fa-lg iconEdit' onclick='editFile(this)'></i></span><span><i class='fa fa-arrows-alt fa-lg iconMove' onclick='moveSingle(this)'></i></span><span><i class='fa fa-trash-o fa-lg iconTrash' onclick='deleteSingle(this)'></i></span></div><div class='col-xs-1'><span style=''>"+responseJSON.data.size+"b</span></div><div class='col-xs-2'><span>"+responseJSON.data.upload_time+"</span></div></div></li><li class='divider-full-bleed></li>";
                		 $(content).insertBefore($firstLi);
                	}
                }
            }
            
        });
        qq(document.getElementById("trigger-upload")).attach("click", function() {
            manualUploader.uploadStoredFiles();
        });
        
        