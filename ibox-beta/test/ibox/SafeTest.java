package ibox;

import org.junit.Test;

import ibox.util.safe.CipherUtil;

public class SafeTest {
	@Test
	public void test(){
		String password=CipherUtil.encryptData("zxh428193");
		System.out.println(password);
	}

}
