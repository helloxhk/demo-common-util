package com.taiji.common.util.file;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	/**
	 *根据时间日期随机生成文件名
	 * @return
	 */
	public static synchronized String getFileFullName() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmmSSS");
		String fileName = sdf.format(new Date());
		double random = Math.random();
		int rand = (int) (random*10000);
		return fileName+rand+".xls";
	}
	

}
