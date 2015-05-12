   function delLog (obj) {
            // body...
                var id = obj.parentNode.parentNode.firstChild.nextSibling.innerText;
                var trIndex = obj.parentNode.parentNode.rowIndex;
                $.ajax({
               url : rootPath + "/admin/loginlog/del?id=" +id ,
                data : "",
                type : 'GET',
                dataType : "json",
                success : function(json) {
                        if(json.success=false){
                            alert("delete fail");
                        }else{
                 
                             $('#editable').dataTable().fnDeleteRow(trIndex);   
                        }
                }
                
                
                });
              
        }
 
   
  $("#delLogSubmit").click(function  (argument) {
       // body...
         $.ajax({
             url : rootPath + "/admin/loginlog/delAll",
              data : "",
              type : 'GET',
              dataType : "json",
              success : function(json) {
                      if(json.success=false){
                          alert("delete fail");
                      }else{
                           $("#delLogModal").modal('hide');
                            
                      }
              }

   });
  });
   