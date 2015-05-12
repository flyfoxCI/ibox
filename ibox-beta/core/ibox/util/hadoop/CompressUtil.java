package ibox.util.hadoop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class CompressUtil {

	public CompressUtil() {
		
	}
	public void compressFile(String srcPath,String destPath,String compressName) throws IOException{
		File srcResource=new File(srcPath);
		File destFile=new File(destPath);
		 if(!destFile.exists()){     
			 destFile.mkdirs();  
	        }
	         
	        String targetName =compressName+".zip";   //目的压缩文件名
	        FileOutputStream outputStream = new FileOutputStream(destPath+"\\"+targetName);
	        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
	         
	        createCompressedFile(out, srcResource, "");
	         
	        out.close();  
	}
	private void createCompressedFile(ZipOutputStream out, File file,
			String dir) throws IOException {
		// TODO Auto-generated method stub
		if(file.isDirectory()){
			    File[] files = file.listFiles();
	            //将文件夹添加到下一级打包目录
	            out.putNextEntry(new ZipEntry(dir+"/"));
	            dir = dir.length() == 0 ? "" : dir +"/";
	             
	            //循环将文件夹中的文件打包
	            for(int i = 0 ; i < files.length ; i++){
	                createCompressedFile(out, files[i], dir + files[i].getName());         //递归处理
	            }
		}else{
			   FileInputStream fis = new FileInputStream(file);
             
	            out.putNextEntry(new ZipEntry(dir));
	            //进行写操作
	            int j =  0;
	            byte[] buffer = new byte[1024];
	            while((j = fis.read(buffer)) > 0){
	                out.write(buffer,0,j);
	            }
	            //关闭输入流
	            fis.close();
		}
		
	}
	
	public static void main(String[] args){
		 CompressUtil compressedFileUtil = new  CompressUtil();
        
       try {
           compressedFileUtil.compressFile("/home/cherry/ibox", "/home/cherry/","压缩文件");
           System.out.println("压缩文件已经生成...");
       } catch (Exception e) {
           System.out.println("压缩文件生成失败...");
           e.printStackTrace();
       }
   }

}
