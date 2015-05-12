package ibox.util.expect;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.jfinal.log.Logger;

public class ExpectUtil {
	static Logger logger=Logger.getLogger(ExpectUtil.class);
	 String path;
	final static ExecutorService exc=Executors.newFixedThreadPool(1);
	public ExpectUtil(String path) {
		super();
		this.path = path;
	}

	public static String excute(String path,String ip,String...params){
		String command="/usr/bin/expect";
		StringBuffer sb = new StringBuffer();
		command=command+" "+path+" "+ip;
		for(String param:params){
			command=command+" "+param;
		}
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			java.io.InputStream stdin = p.getInputStream();
		    java.io.InputStreamReader isr = new java.io.InputStreamReader(stdin);
		    java.io.BufferedReader br = new java.io.BufferedReader(isr);
		     do {
		    	    String str = br.readLine();
		    	    if (str == null)
		    	      break;
		    	    if (str.trim().equals(""))continue;
		    	    sb.append(str);
		    	    System.out.println(str);
		    	    } while (true);
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
			return e.toString();
		}
		 return sb.toString();
	}
	public static Boolean deployNode(String path,String ip,String ...params){
		String rs=isTimeout(path, ip, 600, params);
		if(rs.indexOf("Name: "+ip)!=-1){
			return true;
		}else{
			return false;
		}
		
	}
	public static Boolean copyHost(String path,String ip,String ...params){
		String rs=excute(path, ip, params);
		if(rs.indexOf("100%")!=-1){
			return true;
		}else{
			return false;
		}
		
	}
	public static Boolean delHost(String path,String ip,String ...params){
		
		String rs=isTimeout(path, ip, 120, params);
		String flag="Name: "+ip+":50010Decommission Status : Decommissioned";
		if(rs.indexOf(flag)!=-1){
			return true;
		}else{
			return false;
		}
	}
	public static String isTimeout(final String path,final String ip,int time,final String ...params){
		String obj = null ;
		Callable<String> call=new Callable<String>() {
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				 String result;
				 result=excute(path, ip, params);
				 return result;  
			}
		};
		Future<String> future=exc.submit(call);
		try {
			obj= future.get(1000 * time, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	

}
