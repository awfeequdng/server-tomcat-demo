package com.demo.server;

import java.io.File;
import java.io.IOException;

import com.demo.server.utils.StringUtils;

public class FileTest {

	public static void main(String[] args) throws IOException {
		
		String path="D://file1//file2///1.txt";
		String[] paths=path.split("//");
		String tempPath="";
		File file=null;
		for(String p:paths){
			if(!StringUtils.isEmpty(p)&&p.contains(".")){
				file=new File(path);
				if(!file.exists()){
					file.createNewFile();
				}
				break;
			}else{
				if(p.contains(":")){
					tempPath=tempPath+p+"//";
                	continue;
                }
                tempPath=tempPath+p+"//";
                
                file=new File(tempPath);
                if(!file.exists()){
                	file.mkdir();
                }
			}
		}
	}

}
