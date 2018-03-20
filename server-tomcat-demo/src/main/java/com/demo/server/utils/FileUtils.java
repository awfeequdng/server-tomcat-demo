package com.demo.server.utils;

import java.io.File;

public class FileUtils {
	
	/**
	 * 创建文件，
	 * 如果path 包含目录，则循环创建
	 * @param path
	 * @throws Exception
	 */
	public static File newFile(String path)throws Exception{
		String[] paths=path.split("\\");
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
		return new File(path);
	}
	
	public static void main(String[] args) throws Exception {
		String path="D:\file1\file2\1.txt";
		path.replaceAll("\\", "//");
		newFile(path);
	}
	

}
