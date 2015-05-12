package cn.ibox.ctrl.admin;

import ibox.util.lucene.LuceneUtil;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.BoxFile;
import cn.ibox.service.FileService;

@ControllerBind(controllerKey = "/admin/file")
@RequiresRoles(value={"admin","manager"},logical=Logical.OR)
public class FileCtrl extends BaseController<BoxFile> {
	private final FileService fileService;
	public FileCtrl() throws IOException {
		// TODO Auto-generated constructor stub
		this.modelClass=BoxFile.class;
		fileService=new FileService();
	}
	public void index() {
		Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 9, "select *", "from file where name!=? and type!=?","null","dir");
		setAttr("filePage", filePage);
		this.renderHTML("index.html");
	}
	public void getFileList(){
		Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 9, "select *", "from file where name!=? and type!=?","null","dir");
		String head=" <table class='table table-striped'><thead><tr><th><input type='checkbox' id='selectAll'  onclick='selectAll(this);' >全选</th><th>文件名</th><th class='col-sm-2'>tool</th><th>大小</th><th>最后日期</th></tr></thead><tbody id='tbody'>";
		String content=head+"";
		setAttr("filePage", filePage);
		List<BoxFile>fileList=filePage.getList();
		for(BoxFile file:fileList){
			content+="<tr><td><input type='checkbox'  checked class='i-checks' name='input[]'></td><td>"+file.getStr("name")+"</td><td class='icon-tool'><span class='download'><i class='fa fa-cloud-download'></i></span><span class='edit'><i class='fa fa-edit'></i></span><span class='del'><i class='fa fa-trash'></i></span></td><td>"+file.getLong("size")/1024/1024+"Mb</td><td>"+file.getDate("upload_time")+"</td><input type='hidden' value='"+file.getStr("id")+"' id='id'/><input type='hidden' value='"+file.getStr("email")+"' id='email'/><input type='hidden' value='"+file.getStr("type")+"' id='type'/></tr>";
		}
		
		content=content+"</tbody>"+"<object style='border:0px' type='text/x-scriptlet' data='/common/_paginate.html' width=100% height=30></object>";
		content=content+
                                    "</table>";
		this.rendJson(true, 200, "", content);
	}
	public void download() {
		Controller c=this.keepPara();
		String email=getPara("email");
		String fileId=getPara("fileId");
		if(!fileService.download(c,fileId,email)){
			this.rendJson(false,null,"下载失败");
		}
		this.rendJson(true,null,"下载成功");
		

	}

	public void delete() {
		String fileId=getPara("fileId");
		String fileType=getPara("fileType");
		String email=getPara("email");
		if(fileType.equals("dir")){
			if(!fileService.delDir(fileId,email)){
				this.rendJson(false,null,"删除失败");	
			}else{
				this.rendJson(true,null,"删除成功");	
			}
			
		}else{
			if(!fileService.del(fileId, email)){
				this.rendJson(false,null,"删除失败");	
				}else{
					this.rendJson(true,null,"删除成功");	
				}
		}
		

	}
	public void deleteAll(){
		String ids=getPara("fileIds");
		String emails=getPara("emails");
		String types=getPara("fileTypes");
		String[] fileId=ids.split(",");
		String[] email=emails.split(",");
		String[] type=types.split(",");
		for(int i=0;i<fileId.length;i++){
			if(type[i].equals("dir")){
				if(!fileService.delDir(fileId[i],email[i])){
					this.rendJson(false,null,"删除失败");	
				}else{
					this.rendJson(true,null,"删除成功");	
				}
				
			}else{
				if(!fileService.del(fileId[i], email[i])){
					this.rendJson(false,null,"删除失败");	
					}else{
						this.rendJson(true,null,"删除成功");	
					}
			}
		}
		this.rendJson(true,null,"删除成功");	
		
	}

	public void search() {
		String keywords=getPara("keywords");
		String resultIds=LuceneUtil.searchString(keywords);
		if(resultIds.equals("")){
			setAttr("keywords", keywords);
			this.renderHTML("noresult.html");
		}else{
			String sql="from file where type!='dir'  and id in ("+resultIds+") ";
			Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 9, "select *", sql);
			setAttr("filePage", filePage);
			setAttr("keywords", keywords);
			this.renderHTML("search.html");
		}
		

	}
}
