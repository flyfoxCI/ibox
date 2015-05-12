package ibox;

import java.io.IOException;
import org.junit.Test;
public class ExpectTest {
 @Test
 public void test(){
	String rootPath=getClass().getResource("/").getFile().toString();
	String cmd_exec_expect = "/usr/bin/expect -f /home/cherry/deploy_datanode.sh 172.31.5.142 cherry zxh428193 172.26.23.175 cherry zxh428193 cherry-pc";
	Process p;
	try {
		p = Runtime.getRuntime().exec(cmd_exec_expect);
		java.io.InputStream stdin = p.getInputStream();
	     java.io.InputStreamReader isr = new java.io.InputStreamReader(stdin);
	     java.io.BufferedReader br = new java.io.BufferedReader(isr);

	     StringBuffer sb = new StringBuffer();
	     do {
	    	    sb.setLength(0);
	    	    String str = br.readLine();
	    	    if (str == null)
	    	      break;
	    	    if (str.trim().equals(""))continue;
	    	    sb.append(str);
	    	    System.out.println(sb.toString());
	    	    } while (true);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     // 获得返回值
     
 }
}
