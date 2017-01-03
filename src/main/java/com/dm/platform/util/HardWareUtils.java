package com.dm.platform.util;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

import org.omg.CORBA.ARG_IN;
 
public class HardWareUtils {
 
    /**
     * 获取主板序列号
     * wmic CPU get ProcessorID
     * fdisk -l |grep "Disk identifier" |awk {'print $3'} 
     * @return*/
   public static String getCPUSerial() { 
            String result = ""; 
            String lineSeparator = System.getProperty("line.separator", "\n");
            String os = getOsName(); 
            if (os.startsWith("Windows")) { 
            try { 
                File file = File.createTempFile("tmp", ".vbs"); 
                file.deleteOnExit(); 
                FileWriter fw = new java.io.FileWriter(file); 
     
                String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n" 
                        + "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n" 
                        + "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n" 
                        + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n " 
                        + "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n " 
                        + "    exit for  ' do the first cpu only! \r\n" + "Next                    "; 
     
                fw.write(vbs); 
                fw.close(); 
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath()); 
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())); 
                String line; 
                while ((line = input.readLine()) != null) { 
                    result += line; 
                } 
                input.close(); 
                file.delete(); 
            } catch (Exception e) { 
                e.fillInStackTrace(); 
            } 
            }else if (os.startsWith("Linux")) { 
                String CPU_ID_CMD = "dmidecode -t 4 | grep ID |sort -u -r |awk -F': ' '{print $2}'"; 
                 Process p; 
                 try { 
                     p = Runtime.getRuntime().exec(new String[]{"sh","-c",CPU_ID_CMD});//管道 
                     BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
                     String line; 
                     while ((line = br.readLine()) != null) { 
                             result += line; 
                             break; 
                     } 
                     char ss = result.charAt(2);
                     result = result.replaceAll(String.valueOf(ss), "");
                     br.close(); 
                 } catch (IOException e) { 
                 } 
            } 
            if (result.trim().length() < 1 || result == null) { 
                result = "无CPU_ID被读取"; 
            } 
            return result.trim(); 
        }  
 /****获取磁盘id*******/
   
  
  
 /***获取操作系统类型****/
    private static String getOsName() {
		// TODO Auto-generated method stub
    	String os = ""; 
        os = System.getProperty("os.name"); 
        return os;  
	}
}