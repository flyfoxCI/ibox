$("#addRoleSave")
		.click(
				function(argument) {
					// body...
					var role_name = $("#role_name").val();
					var role_sort = $("#role_sort").val();
					var role_type = $("input[name='role_type']:checked").val();
					var is_active = $("input[name='is_active']:checked").val();
					var ids = $("#ids").val();
					if (role_name == "" || role_type == "" || is_active ==""
							|| ids == "") {
						$("#errMsg").text("请填写和选择所有的选项");
						return;
					}
					$
							.ajax({
								url : rootPath + "/admin/role/add",
								data : {
									"role_name" : role_name,
									"role_type" : role_type,
									"role_sort" : role_sort,
									"is_active" : is_active,
									"ids" : ids
								},
								type : 'POST',
								dataType : "json",
								success : function(json) {
									if (json.success == false) {
										$("#role_name").val();
										$("#ids").val();
										alert("添加失败");
									} else {
										window.location.reload();
									}
								}
							});
				});
//编辑
function editRole(obj) {
	var datanode=obj.parentNode.parentNode.firstChild;
	var id=datanode.innerText;
	var name = datanode.nextSibling.innerText;
	var trIndex = obj.parentNode.parentNode.rowIndex;
	$
			.ajax({
				url : rootPath + "/admin/role/edit?name=" + name,
				data : "",
				type : 'GET',
				dataType : "json",
				success : function(json) {
					if (json.success == false) {
						layer.msg('修改失败，请检查服务器', {
							icon : 8,
							time : 2000
						// 2秒关闭（如果不配置，默认是3秒）
						}, function() {
							// do something
						});
						return;
					} else {
						$("#edit_role_name").val(name);
						$("#edit_role_sort").val(json.data.sort);
						$("input[name='edit_role_type'][value="+json.data.type+"]").attr("checked",true);
						$("input[name='edit_is_active'][value="+json.data.type+"]").attr("checked",true);
						var zTree = $.fn.zTree.getZTreeObj("tree2");
						for(var i=0;i<json.tokenid.length;i++){
							var node = zTree.getNodeByParam("id",json.tokenid[i].resc_id);
							zTree.checkNode(node, true, true);
						}
						$("#editRoleModal").modal('show');
						$("#editRoleSubmit")
								.click(
										function() {
											var role_name = $("#edit_role_name")
													.val();
											var role_sort = $("#edit_role_sort")
													.val();
											var role_type = $(
													"input[name='edit_role_type']:checked")
													.val();
											var is_active = $(
													"input[name='edit_is_active']:checked")
													.val();
											nodes2 = zTree.getCheckedNodes(true);  
											var ids2="";  
									        for (var i=0, l=nodes2.length; i<l; i++) {  
									            ids2+=nodes2[i].id+",";  
									        }  
									          
									        if (ids2.length > 0 ) ids2 = ids2.substring(0, ids2.length-1);  
			
											if(ids==""||is_active==""||role_type==""||role_sort==""||role_name==""){
												$("#editErrMsg").text("请选择和填写所有选项");
											}

											$
													.ajax({
														url : rootPath
																+ "/admin/role/update",
														data : {
															"role_name" : role_name,
															"role_sort" : role_sort,
															"role_type" : role_type,
															"is_active" : is_active,
															"ids" : ids2,
															"id":id
														},
														type : 'POST',
														dataType : "json",
														success : function(json) {
															if (json.success == false) {
																$("#editErrMsg")
																		.val(
																				json.msg);

															} else {
																window.location.reload();

															}
														}
													});
										});

					}
				}
			});
}  

function delRole(obj) {
	var id = obj.parentNode.parentNode.firstChild.innerText;
	var trIndex = obj.parentNode.parentNode.rowIndex;
	$("#delRoleModal").modal('show');
	$("#delRoleSubmit").click(function() {
		$.ajax({
			url : rootPath + "/admin/role/del",
			data : {
				"id" : id
			},
			type : 'POST',
			dataType : "json",
			success : function(json) {
				if (json.success == false) {
					$('#delErrMsg').val(json.msg);

				} else {
					window.location.reload();
					

				}
			}
		});
	});
}

