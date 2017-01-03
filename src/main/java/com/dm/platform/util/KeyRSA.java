 package com.dm.platform.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/*     */ public class KeyRSA
/*     */ {
	public static final String KEY_ALGORITHM = "RSA";  
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  

	/** *//** 
	 * 获取公钥的key 
	 */  
	private static final String PUBLIC_KEY = "RSAPublicKey";  
	  
	/** *//** 
	 * 获取私钥的key 
	 */  
	private static final String PRIVATE_KEY = "RSAPrivateKey";  
	  
	/** *//** 
	 * RSA最大加密明文大小 
	 */  
	private static final int MAX_ENCRYPT_BLOCK = 117;  
	  
	/** *//** 
	 * RSA最大解密密文大小 
	 */  
	private static final int MAX_DECRYPT_BLOCK = 128;  

	/** *//** 
	 * <p> 
	 * 生成密钥对(公钥和私钥) 
	 * </p> 
	 *  
	 * @return 
	 * @throws Exception 
	 */  
   private static KeyRSA instance = new KeyRSA();
 
/*     */   public static KeyRSA getInstance()
/*     */   {
/*  25 */     return instance;
/*     */   }
/*     */   
/*  27 */   public Properties properties = new Properties();
/*     */   
/*     */   public String getValue(String key)
/*     */   {
	          String  valueString = null;
	          try
	          {
                  valueString =  ConfigUtil.getConfigContent("license", key);
	        	 /*InputStream in = getClass().getClassLoader().getResourceAsStream("config/properties/license.properties");
	        	  Properties properties = new Properties();
	        	  properties.load(in);
	        	  valueString = properties.getProperty(key);*/
	          }
	          catch(Exception e)
	          {
	        	  e.printStackTrace();
              System.out.println("未找到授权文件！");
	          }
	          return valueString;
/*     */   }
/*     */   
/*     */   
/*解密算法*/

public  String decrypt(String cryptograph) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
	   /** 将文件中的私钥对象读出 */
	   /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
	   Cipher cipher = Cipher.getInstance("RSA");
	   cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
	   BASE64Decoder decoder = new BASE64Decoder();
	   byte[] cpuId = decoder.decodeBuffer(cryptograph);
	   /** 执行解密操作 */
	   byte[] cpuIdByte = cipher.doFinal(cpuId);
	   return new String(cpuIdByte);
	}
        public boolean checkLicense()
             {
        	boolean flag = true;
	           String cpuIdString = null;
			try {
				cpuIdString = decrypt(getValue("mcode"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("解码失败！");
			}
	          if(!HardWareUtils.getCPUSerial().equals(cpuIdString))
	          {
	             return false;
	          }
	          else {
	        	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        	  Date date = null;
				try {
					date = sdf.parse(decrypt(getValue("endate")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("授权时间输入有误，请联系管理员重新生成license！");
					return false;
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("解码失败！");
					return false;
				}
	        	  if(date.getTime()<new Date().getTime())
	        	  {
	        		  System.out.println("过期");
	        		  return false;
	        	  }
	          }
	          return flag;
             }
        
        public  Key getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException  { 
            String str = getClass().getClassLoader().getResource("config/properties/private.dat").getPath();
            FileInputStream in = null;
			try {
				in = new FileInputStream(str);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("缺少秘钥文件！");
			}
        	PrivateKey privateKey = null;
        	byte[] bytes ; 
        		try {
        			bytes = new BASE64Decoder().decodeBuffer(in);
        			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);  
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
                   privateKey = keyFactory.generatePrivate(keySpec);   
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			System.out.println("读取秘钥失败！");
        		}
             try {
        		in.close();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
           return privateKey;
        }  
     }


