package ibox.util.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;

public class UploadFileUtil {
	private Controller c;
	
	public UploadFileUtil(Controller c) {
		super();
		this.c = c;
	}

	public String  getPath(){
		InputStream info;
		try {
			info = c.getRequest().getInputStream();
			String str="";
			byte[] buffer = new byte[1024];
			
			while((info.read(buffer))>0){
			   str +=new String(buffer);
			}
			String subString=str.substring(str.lastIndexOf("Content-Disposition: form-data;"),str.length());
			Pattern pattern=Pattern.compile("[a-zA-z]+://[^\\s]*");
			Matcher matcher=pattern.matcher(subString);
			if(matcher.find()){
				return matcher.group();
			}else{
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}
}
