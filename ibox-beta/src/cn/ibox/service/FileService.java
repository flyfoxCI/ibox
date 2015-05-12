package cn.ibox.service;


import ibox.util.constant.Constant;
import ibox.util.hadoop.HdfsTool;
import ibox.util.lucene.LuceneUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.ibox.bean.LuceneBean;
import cn.ibox.model.BoxFile;
import cn.ibox.model.TreeNode;
import cn.ibox.model.User;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;

public class FileService {
	public final HdfsTool hdfsTool;
	private static final Logger logger = Logger.getLogger(FileService.class);
	public FileService() throws IOException {
		hdfsTool = new HdfsTool();
	}
	public boolean mkdir(String dirName,String dirId,String email){
		String url=null;
		UUID uuid=UUID.randomUUID();
		url=BoxFile.dao.findPath(dirId)+"/"+dirName;
		BoxFile file=BoxFile.dao.findByColumn("url", url);
		if(file!=null){
			return false;
		}
		if(!BoxFile.dao.mkdir(uuid.toString(),dirName, dirId,url)){
			return false;
		}
		if(!hdfsTool.mkdirs("/"+email+url)){
			return false;
		}
		LuceneBean luceneBean=new LuceneBean(uuid.toString(), dirName, email);
		try {
			LuceneUtil.addIndex(luceneBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加文件夹索引异常!" + e);
		}
		
		return true;
	}
	public boolean mkdir(String email,UUID uuid) {
		 String url="";
		 new BoxFile().set("id",uuid.toString())
		  .set("upload_time",new Timestamp(new Date().getTime()))
		  .set("url",url)
		  .set("type","dir")
		  .set("modify_time",new Timestamp(new Date().getTime()))
		  .save();
		 if(!hdfsTool.mkdirs(url+email)){
				return false;
			}
			return true;
	
	}
	@SuppressWarnings("resource")
	public boolean upload(List<UploadFile> files, String saveDir,String dirId, String uid) {
		for(UploadFile file:files){
			InputStream in;
			UUID uuid=UUID.randomUUID();
			String savePath;
			if(saveDir.equals("")){
				savePath="/"+saveDir+file.getOriginalFileName();
			}else{
				savePath=saveDir+"/"+file.getOriginalFileName();
			}
			long size;
			int category;
			String type=file.getFileName().substring(file.getFileName().lastIndexOf(".")+1);
			if(Constant.VIDEO_FORMAT.indexOf(type)!=-1){
				category=1;
			}else if(Constant.MUSIC_FORMAT.indexOf(type)!=-1){
				category=2;
			}else if(Constant.PIC_FORMAT.indexOf(type)!=-1){
				category=3;
			}else{
				category=4;
			}
			
			try {
				File fileTemp=new File(file.getSaveDirectory()+file.getOriginalFileName());
				in = new FileInputStream(fileTemp);
				size=in.available();
				User user=User.dao.findById(uid);
				long useSpace=user.getLong("use_space");
				long space=user.getLong("space");
				if(BoxFile.dao.findByPath(savePath)!=null){
					return false;
				}
				if(useSpace/1024/1024+size/1024/1024>space){
					return false;
				}
				hdfsTool.copyFile(in, "/"+user.getStr("email")+savePath);
				new BoxFile().set("url", savePath)
				.set("size", size)
				.set("type", type)
				.set("upload_time", new Timestamp(new Date().getTime()))
				.set("modify_time",new Timestamp(new Date().getTime()))
				.set("pid", dirId)
				.set("name", file.getOriginalFileName())
				.set("id", uuid.toString())
				.set("category", category)
				.set("email", user.getStr("email"))
				.save();
				Db.update("update user set use_space=? where id=?",useSpace+size/1024+1024,user.getInt("id"));
				in.close();
				LuceneBean luceneBean=new LuceneBean(uuid.toString(), file.getOriginalFileName(), user.getStr("email"));
				try {
					LuceneUtil.addIndex(luceneBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("添加文章索引异常!" + e);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			
			
		}
		return true;
		
	}
	public int updateName(String name, String fileId,String email) {
		// TODO Auto-generated method stub
		String originPath=BoxFile.dao.findById(fileId).getStr("url");
		String newPath=originPath.substring(0,originPath.lastIndexOf("/"))+"/"+name;
		originPath="/"+email+originPath;
		if(BoxFile.dao.findByPath(newPath)!=null){
			return -1;
		}
		if(!hdfsTool.rename(originPath,"/"+email+newPath)){
			return -1;
		};
		int result=Db.update("update file set name=?,url=? where id=?",name,newPath,fileId);
		LuceneBean luceneBean=new LuceneBean(fileId, name,email);
		try {
			LuceneUtil.updateIndex(luceneBean);;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新文章索引异常!" + e);
		}
		return result;
	}
	public boolean download(Controller c, String fileId, String email) {
		// TODO Auto-generated method stub
		HttpServletResponse response=c.getResponse();
		BoxFile file=BoxFile.dao.findById(fileId);
		String remote="/"+email+file.getStr("url");
		response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename="+file.getStr("name"));
        response.setHeader("Content-Length", file.getLong("size").toString());
		if(hdfsTool.downloadFromCloud(remote,response)){
			return true;
		}
		return false;
	}
	public String listDir(String dirId,Controller c) {
		// TODO Auto-generated method stub
		List<BoxFile> fileList=new ArrayList<BoxFile>();
		List<TreeNode> treeList=new ArrayList<TreeNode>();
		fileList=BoxFile.dao.find("select * from file where pid=? and type='dir'",dirId);
		String json="";
		if(fileList!=null){
			for(BoxFile file:fileList){
				String iconPath=c.getRequest().getContextPath()+"/assets/images/folder.png";
				TreeNode node=new TreeNode();
				node.setName(file.getStr("name"));
				node.setDirId(file.getStr("id"));
				node.setPid(dirId);
				node.setAsync(true);
				node.setIsParent("true");
				node.setIcon(iconPath);
				treeList.add(node);
			}
		}
		json=JSON.toJSONString(treeList);
		return json;
		
	}
	public boolean del(String fileId,String email){
		BoxFile file=BoxFile.dao.findById(fileId);
		String path="/"+email+file.getStr("url");
		System.out.println(path);
		if(!hdfsTool.rmr(path)){
			return false;
		}
		BoxFile.dao.deleteById(fileId);
		LuceneBean luceneBean=new LuceneBean(fileId, file.getStr("name"),email);
		try {
			LuceneUtil.deleteIndex(luceneBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加文章索引异常!" + e);
		}
		return true;
	}
	public boolean move(String fileId, String toDirId,String email) {
		// TODO Auto-generated method stub
		String newPath;
		BoxFile file1=BoxFile.dao.findById(fileId);
		BoxFile file2=BoxFile.dao.findById(toDirId);
		if(file2.get("url").equals("")){
			newPath="/"+file1.getStr("name");
		}else{
			newPath=file2.getStr("url")+"/"+file1.getStr("name");
		}
		if(BoxFile.dao.findByPath(newPath)!=null){
			return false;
		}
		String originPath=file1.get("url");
		if(!hdfsTool.move(originPath,newPath,email)){
			return false;
		}
		
		if(Db.update("update file set url=?,pid=? where id=?",newPath,toDirId,fileId)!=1){
			return false;
		}
		
		return true;
	}
	
	public List<BoxFile> findByCate(int category) {
		// TODO Auto-generated method stub
		String sql="select * from file where category=?";
		
		List<BoxFile> fileList=BoxFile.dao.find(sql, category);
		return fileList;
	}
	public boolean delDir(String fileId, String email) {
		// TODO Auto-generated method stub
		BoxFile dir=BoxFile.dao.findById(fileId);
		String url=dir.getStr("url");
		String path="/"+email+url;
		List<BoxFile> fileList=BoxFile.dao.findByPid(fileId);
		if(!hdfsTool.rmr(path)){
			return false;
		}
		if(null!=fileList){
			for(BoxFile file:fileList){
				BoxFile.dao.deleteById(file.getStr("id"));
				LuceneBean luceneBean=new LuceneBean(fileId, file.getStr("name"),email);
				try {
					LuceneUtil.deleteIndex(luceneBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("添加文章索引异常!" + e);
				}
			}
		}
		BoxFile.dao.deleteById(fileId);	
		LuceneBean luceneBean=new LuceneBean(fileId, dir.getStr("name"),email);
		try {
			LuceneUtil.deleteIndex(luceneBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加文章索引异常!" + e);
		}
		return true;
	}
	public int updateDirName(String newDirName, String fileId, String email) {
		String sql="select getChildList('"+fileId+"')";
		String ids=(String)Db.query(sql).toArray()[0];
		String[] fileIds=ids.split(",");
		String updateDirSql="update file set name=?,url=? where id=?";
		String updateSubDirSql="update file set url=? where id=?";
		BoxFile dir=BoxFile.dao.findById(fileId);
		String  originDirName=dir.getStr("name");
		String originDirPath=dir.getStr("url");
		String newDirPath=originDirPath.substring(0,originDirPath.lastIndexOf("/"))+"/"+newDirName;
		Db.update(updateDirSql,newDirName,newDirPath,fileId);
		Object[][] paras=new Object[fileIds.length-2][2];
		if(fileIds.length>2){
			for(int i=2;i<fileIds.length;i++){
				String url=BoxFile.dao.findById(fileIds[i]).getStr("url");
				String prefix=url.substring(0, originDirPath.length()-originDirName.length());
				String postfix=url.substring(originDirPath.length());
				String newUrl=prefix+newDirName+postfix;
				paras[i-2][0]=newUrl;
				paras[i-2][1]=fileIds[i];
				
			}
			Db.batch(updateSubDirSql, paras,fileIds.length-2);
		}
		if(!hdfsTool.rename("/"+email+originDirPath,"/"+email+newDirPath)){
			return -1;
		};
		LuceneBean luceneBean=new LuceneBean(fileId, newDirName,email);
		try {
			LuceneUtil.updateIndex(luceneBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加文章索引异常!" + e);
		}
		return 0;
	}
	public boolean moveDir(String fileId, String toDirId, String email) {
		// TODO Auto-generated method stub
		String newDirPath;
		String sql="select getChildList('"+fileId+"')";
		String updateMovDirSql="update file set url=? where id=?";
		String ids=(String)Db.query(sql).toArray()[0];
		String[] fileIds=ids.split(",");
		if(ids.indexOf(toDirId)!=-1){
			return false;
		}
		BoxFile originDir=BoxFile.dao.findById(fileId);
		BoxFile toDir=BoxFile.dao.findById(toDirId);
		String originDirPath=originDir.getStr("url");
		String originDirName=originDir.getStr("name");
		if(toDir.get("url").equals("")){
			newDirPath="/"+originDir.getStr("name");
		}else{
			newDirPath=toDir.getStr("url")+"/"+originDirName;
		}
		if(BoxFile.dao.findByPath(newDirPath)!=null){
			return false;
		}
		Db.update("update file set url=?,pid=? where id=?",newDirPath,toDirId,fileId);
		Object[][] paras=new Object[fileIds.length-1][2];
		if(fileIds.length>2){
			for(int i=2;i<fileIds.length;i++){
				String url=BoxFile.dao.findById(fileIds[i]).getStr("url");
				String postfix=url.substring(originDirPath.length());
				String newUrl=newDirPath+postfix;
				paras[i-1][0]=newUrl;
				paras[i-1][1]=fileIds[i];
				
			}
			Db.batch(updateMovDirSql, paras,fileIds.length-1);
		}
		if(!hdfsTool.move(originDirPath,newDirPath,email)){
			return false;
		}
		return true;
	}
	public boolean downloadMutipli(Controller c, String email, String[] fileId) {
		// TODO Auto-generated method stub
		String[] fileIds=new String[fileId.length-1];
		for(int i=1;i<fileId.length;i++){
			fileIds[i-1]=fileId[i];
		}
		String[] remotes=new String[fileIds.length];
		String[] names=new String[fileIds.length];
		for(int i=0;i<fileIds.length;i++){
			BoxFile file=BoxFile.dao.findById(fileIds[i]);
			remotes[i]="/"+email+file.getStr("url");
			names[i]=file.getStr("name");
		}
		HttpServletResponse response=c.getResponse();
		if(hdfsTool.downloadMutiPliFromCloud(remotes,names,response)){
			return true;
		}
		return false;
		
	}
	
	
	
	

}
