package ibox.util.hadoop;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;

import cn.ibox.cfg.HdfsConfig;

public class HDFS_Downloader  
{  
    public static FileSystem hdfs;  
      
    public static void downloadFile(String srcPath, String dstPath) throws Exception  
    {  
        FSDataInputStream in = null;  
        FileOutputStream out = null;  
        try  
        {  
            in = hdfs.open(new Path(srcPath));  
            out = new FileOutputStream(dstPath);  
            IOUtils.copyBytes(in, out, 4096, false);  
        }  
        finally  
        {  
            IOUtils.closeStream(in);  
            IOUtils.closeStream(out);  
        }  
    }  
      
    public static void downloadFolder(String srcPath, String dstPath) throws Exception  
    {  
        File dstDir = new File(dstPath);  
        if (!dstDir.exists())  
        {  
            dstDir.mkdirs();  
        }  
        FileStatus[] srcFileStatus = hdfs.listStatus(new Path(srcPath));  
        Path[] srcFilePath = FileUtil.stat2Paths(srcFileStatus);  
        for (int i = 0; i < srcFilePath.length; i++)  
        {  
            String srcFile = srcFilePath[i].toString();  
            int fileNamePosi = srcFile.lastIndexOf('/');  
            String fileName = srcFile.substring(fileNamePosi + 1);  
            download(srcPath + '/' + fileName, dstPath + '/' + fileName);  
        }  
    }  
      
    public static void download(String srcPath, String dstPath) throws Exception  
    {  
        if (hdfs.isFile(new Path(srcPath)))  
        {  
            downloadFile(srcPath, dstPath);  
        }  
        else  
        {  
            downloadFolder(srcPath, dstPath);  
        }  
    }  
      
    public static void main(String[] args)  
    {  
    	String hdfsPath;
    	JobConf conf;
       
        
            try  
            {  
            	 hdfsPath = HdfsConfig.getHdfsPath();
            	 conf = HdfsConfig.config();
                hdfs =FileSystem.get(URI.create(hdfsPath), conf);;  
                download("/swordson@gmail.com/测试3", "/home/cherry/测试3");  
            }  
            catch (Exception e)  
            {  
                System.out.println("Error occured when copy files");  
            }  
        }  
    
}  
