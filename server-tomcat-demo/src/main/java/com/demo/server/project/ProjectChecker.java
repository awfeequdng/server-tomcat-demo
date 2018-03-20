package com.demo.server.project;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;

import com.demo.server.BootStraper;
import com.demo.server.utils.WarUtils;
import com.demo.server.utils.ZipUtils;

public class ProjectChecker {

	/**
	 * 服务器支持多项目部署，所以，用Set来接收项目名称列表
	 * 找到项目列表，解压项目列表，返回项目名称列表
	 * @param work_space  服务器根路径
	 * @return
	 * @throws Exception 
	 */
	public Set<String> checkout(String work_space) throws Exception{
		Set<String> projectNames=new HashSet<String>();
		//发现war包
		File workSpacefile=new File(work_space);
		//检查是不是war包
		FilenameFilter warFilter=new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".war");
			}
		};
		//开始遍历解压缩war
		File[] wars=workSpacefile.listFiles(warFilter);
		for(File war:wars){
			WarUtils.unzip(war.getPath());
			System.out.println("解压项目war------"+war.getName());
		}
		System.out.println("服务器war包解压完成");
;
		//返回项目名称列表
		File[] files=new File(work_space).listFiles();
		for(File f:files){
			if(f.isDirectory()){
				System.out.println("发现项目："+f.getName());
				projectNames.add(f.getName());
			}
		}
		System.out.println("项目搜寻完毕");
		return projectNames;
		
	}

}
