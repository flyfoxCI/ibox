package cn.ibox.ctrl.user;

import ibox.util.constant.Constant;
import ibox.util.lucene.LuceneUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.ibox.bean.LuceneBean;
import cn.ibox.ctrl.base.BaseController;
import cn.ibox.model.BoxFile;
import cn.ibox.service.FileService;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey="/disk")
public class FileOperateCtrl extends BaseController<BoxFile>{
	private static final Logger logger = Logger.getLogger(FileOperateCtrl.class);
	private final FileService fileService;
	public FileOperateCtrl() throws IOException {
		// TODO Auto-generated constructor stub
		this.modelClass=BoxFile.class;
		fileService=new FileService();
	}
	public void index(){
		String rootPath=getRequest().getContextPath();
		String fileId=getSessionAttr("file_id");
	    Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 100, "select *", "from file where pid=? order by upload_time desc",fileId);
		setAttr("filePage", filePage);
		setAttr("dirId", fileId);
		String guide="<a href="+rootPath+"/list?dirId="+fileId+">全部文件</a>";
		setAttr("url", guide);
		this.renderHTML("index.html");
		
	}
	public void mkDir(){
		String dirName=this.getPara("dirName");
		String dirId=this.getPara("dirId");//当前文件夹的Id
		String email=getSessionAttr("email");
		if(fileService.mkdir(dirName, dirId,email)){
			BoxFile file=BoxFile.dao.findByColumn("name",dirName);
			this.rendJson(true,null,"创建文件夹成功",file);
		}else{
			this.rendJson(false,null,"创建文件夹失败");
		}
		
			
	}
	
	public void delFile(){
		String fileId=getPara("fileId");
		String fileType=getPara("fileType");
		String email=getSessionAttr("email");
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
	
	public void list(){
		String rootPath=getRequest().getContextPath();
		String dirId=getPara("dirId");
		String email=getSessionAttr("email");
		setAttr("email",email);
		String file_id=getSessionAttr("file_id");
		Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 100, "select *", "from file where pid=? order by upload_time desc",dirId);
		String url=BoxFile.dao.findPath(dirId);
		String[] dirNames=url.split("/");
		String path="";
		String guide="<a href="+rootPath+"/list?dirId="+file_id+">全部文件</a>";
			for(int i=1;i<dirNames.length;i++){
				path=path+"/"+dirNames[i];
				String parentDirId=BoxFile.dao.findByPath(path).getStr("id");
				String href=rootPath+"/disk/list?dirId="+parentDirId;
				guide=guide+"<a href='#' onclick=guide('"+href+"',this)>/"+dirNames[i]+"</a>";
			}
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("pageNum", filePage.getPageNumber());
			map.put("pageSize", filePage.getPageSize());
			map.put("dataList", filePage.getList());
			map.put("guide", guide);
			this.rendJson(true,null,"删除成功",JSON.toJSON(map));	
			
		
		
		
	}
	public void upload(){
		this.renderHTML("upload.html");
	}
	public void uploadSave() throws IOException{
		MultipartRequest multipartRequest=new MultipartRequest(getRequest(),"",1048576000);
		String dirId=multipartRequest.getParameter("dir");
		String path;
		if(dirId.equals("")){
			dirId=getSessionAttr("file_id");
		}			
		BoxFile boxFile=BoxFile.dao.findById(dirId);
		String saveDir=boxFile.getStr("url");
		List<UploadFile> files=multipartRequest.getFiles();
		if(!fileService.upload(files,saveDir,dirId,getSessionAttr("uid").toString())){
			 this.rendJson(false,null,"上传失败，请检查存储空间");	
		}else{
			if(saveDir.equals("")){
				path=saveDir+"/"+files.get(0).getOriginalFileName();
			}else{
				path=saveDir+"/"+files.get(0).getOriginalFileName();
			}
			
			BoxFile file=BoxFile.dao.findByPath(path);
			this.rendJson(true,null,"上传成功",file);	
		}
	    
	}

	public void edit(){
		String fileId=this.getPara("fileId");
		String fileName=BoxFile.dao.findById(fileId).getStr("name");
		this.rendJson(true, 400, fileName);
	}
	public void editSave(){
		String fileId=getPara("fileId");
		String name=getPara("name");
		String fileType=getPara("fileType");
		String email=getSessionAttr("email");
		if(fileType.equals("dir")){
			if(fileService.updateDirName(name,fileId,email)!=-1){
				this.rendJson(true,null,"更新成功");
				
			}else{
				this.rendJson(false,null,"更新失败，文件名已经存在");
			}
		}else{
			if(fileService.updateName(name,fileId,email)!=-1){
				this.rendJson(true,null,"更新成功");
				
			}else{
				this.rendJson(false,null,"更新失败，文件名已经存在");
			}
		}
		
		
		
	}
	public void download(){
		Controller c=this.keepPara();
		String email=getSessionAttr("email");
		String fileId=getPara("fileId");
		if(!fileService.download(c,fileId,email)){
			this.rendJson(false,null,"下载失败");
		}
		this.rendJson(true,null,"下载成功");
		
	}
	public void listDir(){
		Controller c=this.keepPara();
		String dirId=getPara("dirId");
		String json=fileService.listDir(dirId,c);	
	   //String json="{dirId:000,pid:\"e55a0fd7-bd94-440d-a5a3-01160bc8eacc\",name:\"我的文件\", isParent:true, async:true}";
		c.renderJson(json);
		
	}
	public void getTree(){
		String fileId=getSessionAttr("file_id");
		setAttr("dirId",fileId);
		this.renderHTML("tree.html");
	}
	public void move(){
		String ids=getPara("ids");
		String types=getPara("types");
		String[] fileId=ids.split(",");
		String[] fileType=types.split(",");
		String toDirId=getPara("toDirId");
		String email=getSessionAttr("email");
		for(int i=1;i<fileId.length;i++){
			if(fileType[i].equals("dir")){
				if(!fileService.moveDir(fileId[i],toDirId,email)){
					this.rendJson(false,null,"移动失败,不能移动文件到子文件夹或者该文件已经存在");
					return;
				}
			}else{
				if(!fileService.move(fileId[i],toDirId,email)){
					this.rendJson(false,null,"移动失败,不能移动文件到子文件夹或该文件已经存在");
					return;
				}
			}
			
		}
		this.rendJson(true,200,"移动成功",toDirId);
	}
	public void listByCate(){
		int category=getParaToInt("category");
		String fileId=getSessionAttr("file_id");
		String rootPath=getRequest().getContextPath();
		Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 9, "select *", "from file where category=? order by upload_time desc",category);
		setAttr("dirId",fileId);
		String guide="<a href="+rootPath+"/list?dirId="+fileId+">全部文件</a>"+Constant.CATEGORY[category-1];
		this.setAttr("url", guide);
		setAttr("email", getSessionAttr("email"));
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("pageNum", filePage.getPageNumber());
		map.put("pageSize", filePage.getPageSize());
		map.put("dataList", filePage.getList());
		map.put("guide", guide);
		this.rendJson(true,null,"删除成功",JSON.toJSON(map));	
		
	}
	
	public void generateIndexes() {
		String result = "创建文章索引成功";
		List<LuceneBean> files= BoxFile.dao.findOnline(getSessionAttr("email").toString());
		try {
			LuceneUtil.createIndex(files);
		} catch (Exception e) {
			result = "创建文章索引异常";
			logger.error("创建文章索引异常!" + e);
		}
		renderText(result);
	}
	public void search(){
		String rootPath=getRequest().getContextPath();
		String keyword=getPara("keyword");
		String email=getSessionAttr("email");
		String resultIds=LuceneUtil.search(keyword,email);
		setAttr("keyword", keyword);
		setAttr("email", email);
		String fileId=getSessionAttr("file_id");
		setAttr("dirId",fileId);
		HashMap<String, Object> map=new HashMap<String, Object>();
		String guide="<a href="+rootPath+"/list?dirId="+fileId+">全部文件</a>"+">搜索"+keyword+"结果";
		if(resultIds.equals("")){
			map.put("guide", guide);
			map.put("guide", guide);
			this.rendJson(false,null,"搜索失败",JSON.toJSON(map));	
		}else{
			String sql="from file where id in ("+resultIds+")";
			Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 100, "select *", sql);
			setAttr("keyword", keyword);
			
			this.setAttr("url", guide);
			map.put("pageNum", filePage.getPageNumber());
			map.put("pageSize", filePage.getPageSize());
			map.put("dataList", filePage.getList());
			map.put("guide", guide);
			this.rendJson(true,null,"搜索成功",JSON.toJSON(map));	
			
		}
		
	}
	public void data(){
		String fileId=getSessionAttr("file_id");
		Page<BoxFile> filePage=BoxFile.dao.paginate(this.getParaToInt("pageNum", 1), 12, "select *", "from file where pid=?",fileId);
		setAttr("filePage", filePage);
		String rootPath=getRequest().getContextPath();
		String guide="<a href="+rootPath+"/list?dirId="+fileId+">全部文件</a>";
		setAttr("url", guide);
		setAttr("dirId", fileId);
		this.render("data.html");
	}
	public void delMutipli(){
		String adIds=getPara("adIds");
		String types=getPara("types");
		String[] fileId=adIds.split(",");
		String[] fileType=types.split(",");
		String email=getSessionAttr("email");
		for(int i=1;i<fileId.length;i++){
				if(fileType[i].equals("dir")){
					if(!fileService.delDir(fileId[i],email)){
						this.rendJson(false,null,"删除失败");	
					}else{
						this.rendJson(true,null,"删除成功");	
					}
					
				}else{
					if(!fileService.del(fileId[i], email)){
						this.rendJson(false,null,"删除失败");	
						}else{
							this.rendJson(true,null,"删除成功");	
						}
				}
			}
		this.rendJson(true,null," 删除成功");
	}
	public void downloadMutipli(){
		String ids=getPara("ids");
		String types=getPara("types");
		String[] fileId=ids.split(",");
		String[] fileType=types.split(",");
		Controller c=this.keepPara();
		String email=getSessionAttr("email");
		if(fileId.length==2){
			if(fileType[1].equals("dir")){
				fileService.downloadMutipli(c,email,fileId);
			}else{
				fileService.download(c, fileId[1], email);
			}
		}else{
			fileService.downloadMutipli(c,email,fileId);
		}
		
		
		
	}
	

}
