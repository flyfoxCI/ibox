package ibox.util.hadoop;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.junit.Test;

import cn.ibox.cfg.HdfsConfig;

import com.jfinal.kit.PathKit;

public class HdfsTool {
	public Logger log = Logger.getLogger(getClass());
	public String hdfsPath;
	public JobConf conf;
	public FileSystem fs;
	public HdfsTool(){
		hdfsPath = HdfsConfig.getHdfsPath();
		conf = HdfsConfig.config();
		try {
			fs = FileSystem.get(URI.create(hdfsPath), conf);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info(e);
		}
	}

	public Boolean mkdirs(String folder) {
		Path path = new Path(folder);
		try {
			if (fs.exists(path)) {
				return false;
			}
			fs.mkdirs(path);
			System.out.println("Create: " + folder);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean copyFile(InputStream in, String remote) {
		Path path = new Path(remote);
		try {

			if (fs.exists(path)) {
				return false;
			}
			OutputStream out = fs.create(path);
			IOUtils.copyBytes(in, out, 4096, true);// 4096是4k字节

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// remote---/用户/用户下的文件或文件夹
		return true;
	}

	public Boolean rmr(String folder) {
		Path path = new Path(folder);
		try {
			if (!fs.exists(path)) {
				return false;
			}
			fs.delete(path, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

//	public Boolean download(String remote, String local) {
//		Path path = new Path(remote);
//		try {
//
//			if (!fs.exists(path)) {
//				return false;
//			}
//			fs.copyToLocalFile(path, new Path(local));
//			System.out.println("download: from" + remote + " to " + local);
//			return true;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//
//	}

	public ArrayList<FileStatus> ls(String folder) {
		ArrayList<FileStatus> fileList = new ArrayList<FileStatus>();
		Path path = new Path(folder);
		FileStatus[] list;
		try {
			list = fs.listStatus(path);
			System.out.println("ls: " + folder);
			System.out
					.println("==========================================================");
			if (list != null) {
				for (FileStatus f : list) {
					// System.out.printf("name: %s, folder: %s, size: %d\n",
					// f.getPath(), f.isDir(), f.getLen());
					fileList.add(f);
					System.out.printf("%s, folder: %s, 大小: %dK\n", f.getPath()
							.getName(), (f.isDir() ? "目录" : "文件"),
							f.getLen() / 1024);
				}
				System.out
						.println("==========================================================");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileList;
	}



	public boolean downloadFromCloud(String remote, HttpServletResponse response) {

		FSDataInputStream HDFS_IN;
		try {
			HDFS_IN = fs.open(new Path(remote));
			OutputStream out = response.getOutputStream();
			// 将InputStrteam 中的内容通过IOUtils的copyBytes方法复制到OutToLOCAL中
			byte[] b = new byte[1024];
			int n;
			while ((n = HDFS_IN.read(b)) != -1) {
				out.write(b, 0, n);
			}
			HDFS_IN.close();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}
 
	public boolean rename(String originPath, String newPath) {
		// TODO Auto-generated method stub
		FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			Path fromPath = new Path(originPath);
			Path toPath = new Path(newPath);
			if (!(fs.exists(fromPath))) {
				log.info("No such destination " + fromPath);
				return false;
			}

			if (fs.exists(toPath)) {
				log.info("Already exists! " + toPath);
				return false;
			}

			boolean isRenamed = fs.rename(fromPath, toPath); // renames file
																// name indeed.
			if (isRenamed) {
				log.info("Renamed from " + originPath + " to " + newPath);
			}
			fs.close();
			return true;

		} catch (IOException e1) {
			log.error("fs Exception caught! :", e1);
			new RuntimeException(e1);
			return false;
		}

	}

	public boolean move(String originPath, String newPath, String email) {
		String oldPath="/"+email+originPath;
		String movePath="/"+email+newPath;
		if(!rename(oldPath,movePath)){
			return false; 
		}else{
			return true;
		}
		
		
	}
	
	public boolean downloadMutiPliFromCloud(String[] remotes,
			String[] names, HttpServletResponse response) {
		FSDataInputStream HDFS_IN;
		String tmpFileName=PathKit.getWebRootPath()+"/download/批量下载文件.zip";
		 byte[] buffer = new byte[1024]; 
		 File file=new File(tmpFileName);
		
		 
		 try {
			ZipOutputStream out=new ZipOutputStream(new FileOutputStream(tmpFileName));
			 for(int i=0;i<remotes.length;i++){
				 
				 if(fs.isFile(new Path(remotes[i]))){
					 HDFS_IN = fs.open(new Path(remotes[i]));
					    
					 	out.putNextEntry(new ZipEntry(names[i]));    
		                //设置压缩文件内的字符编码，不然会变成乱码    
					 	 out.setEncoding("GBK");   
					 	 
		                int len;    
		                // 读入需要下载的文件的内容，打包到zip文件    
		                while ((len =HDFS_IN .read(buffer)) > 0) {    
		                    out.write(buffer, 0, len);    
		                }    
		                out.closeEntry();    
		                HDFS_IN.close();
				 }else{
					 compress(out, new Path(remotes[i]),"../"+names[i],response);
				 }
				 
			 }
			  out.close(); 
	          this.downFile(response,tmpFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private void downFile(HttpServletResponse response, String tmpFileName) {
		// TODO Auto-generated method stub
		try {
			String path=tmpFileName;
			File file = new File(path);    
            if (file.exists()) {    
                InputStream ins = new FileInputStream(path);    
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面    
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流    
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setCharacterEncoding("GBK");
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader( "Content-Disposition", "attachment;filename="  + new String( "批量下载文件".getBytes("utf8"), "ISO8859-1"));  
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                // 开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                    bouts.write(buffer, 0, bytesRead);    
                }    
                bouts.flush();// 这里一定要调用flush()方法    
                ins.close();    
                bins.close();
                 
                outs.close();    
                bouts.close();
               
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void test(){
	
		HdfsTool hdfsTool=new HdfsTool();
		hdfsTool.rmr("/test1@gmail.com/测试1");
	}
	
	public void compress(ZipOutputStream out, Path pathString,
			String dir,HttpServletResponse response) throws IOException{
		FileStatus[] srcFileStatus;
		srcFileStatus = fs.listStatus(pathString);
		Path[] srcFilePath = FileUtil.stat2Paths(srcFileStatus);
		for (int i = 0; i < srcFilePath.length; i++)  
        {  
            String srcFile = srcFilePath[i].toString();  
            int fileNamePosi = srcFile.lastIndexOf('/');		  
            String fileName = srcFile.substring(fileNamePosi + 1);
            if(fs.isFile(srcFilePath[i])){
            	FSDataInputStream	fis=fs.open(srcFilePath[i]);
            	out.putNextEntry(new ZipEntry(dir));
            	out.setEncoding("GBK");            	
  	            //进行写操作
  	            int j =  0;
  	            byte[] buffer = new byte[1024];
  	            while((j = fis.read(buffer)) > 0){
  	                out.write(buffer,0,j);
  	            }
  	            out.closeEntry();    
  	            fis.close();
            }else{
            	 out.putNextEntry(new ZipEntry(dir+"/"));
            	 dir = dir.length() == 0 ? "" : dir +"/";
 	             compress(out, srcFilePath[i], dir +fileName,response);         //递归处理
            }
          
        } 
	}
	
	
}
