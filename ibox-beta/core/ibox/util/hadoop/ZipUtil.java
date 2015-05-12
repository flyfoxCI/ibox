package ibox.util.hadoop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {
	   
	 public void ZipFiles(File[] srcfile,File zipfile){
	        byte[] buf = new byte[1024];
	        try{
	            //create the zip file
	            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
	            out.setEncoding("gbk");
	            //Compress the files
	            for(int i = 0; i < srcfile.length;i++){
	                String path = srcfile[i].getName();
	                zip(out,srcfile[i],path);               
	            }
	            //Complete the zip file
	            out.close();
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	       
	}

	private void zip(org.apache.tools.zip.ZipOutputStream out,File f,String base){
	  try{
	    if(f.isDirectory()){
	        File[] f1 = f.listFiles();
	        out.putNextEntry(new org.apache.tools.zip.ZipEntry(base+"/"));
	        base=base + "/";
	        for(int i = 0; i <f1.length; i++){
	            zip(out,f1[i],base + f1[i].getName());
	        }
	    }else{
	        String fileName=f.getName();
	        int index = 0;
	        index = fileName.lastIndexOf(".");
	        String sub = fileName.substring(index+1);
	        if(!sub.equals("crc")){
	            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
	            FileInputStream in = new FileInputStream(f);
	            byte[] buf = new byte[1024];
	            int b;
	            while((b = in.read(buf))>0){
	                out.write(buf,0,b);
	            }
	            in.close();
	        }
	    }
	  }catch(IOException e){
	        e.printStackTrace();
	  }
	}
}
