 
package com.dm.platform.aop;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.dm.platform.util.BASE64Encoder;
import com.dm.platform.util.HardWareUtils;
import com.dm.platform.util.KeyRSA;

@Component
public class initAction implements InitializingBean
   {
	
	boolean flag=false;
	boolean checked = true;
	int num = 0;
			
	 /*  @PostConstruct
		public void validate()
				{
			if(checked)
			{
				checked=false;
				flag = KeyRSA.getInstance().checkLicense();
				if(!flag)
				{
					System.out.println("检测到未授权");
					System.exit(0);
				}
			}
		}*/

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(checked && num<3)
		{
			checked=false;
			flag = KeyRSA.getInstance().checkLicense();
			if(!flag)
			{
				BASE64Encoder encode = new BASE64Encoder();
				System.out.println("系统检测到未授权");
				System.out.println("注册码："+ encode.encode(HardWareUtils.getCPUSerial().getBytes("utf-8")));
				System.exit(0);
			}
		}
	}
 }


