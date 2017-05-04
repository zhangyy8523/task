package com.jing.system.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 文件的工具类<br>
 * public static void main(String[] args) {
		String dir = "D:/project/csm/csm-core";
		List<String> list = getFiles(dir, "pom.xml");
		for (String path : list) {
			System.out.println(path);
		}
		String path = "D:\\project\\csm\\csm-core\\functions\\window\\csm-window-api\\pom.xml";
		String content = readFileString(path);
		System.out.println(content);
	}
 * @author yuejing
 * @date 2016年5月2日 下午3:07:40
 * @version V1.0.0
 */
public class FrameFileUtil {

	private static final Logger LOGGER = Logger.getLogger(FrameFileUtil.class);

	/**
	 * 创建指定路径的文件夹[不存在,则创建]
	 * @param destDirName
	 */
	public static void createDir(String destDirName) {
		File dir = new File(destDirName);
		if(dir.exists()) {
			LOGGER.info("创建目录" + destDirName + "失败，目标目录已存在!");
		} else {
			//创建目录
			dir.mkdirs();
		}
	}

	/**
	 * 读取文件[不存在,则创建]
	 * @param destFileName 文件名
	 * @return 创建成功返回true,否则返回false
	 * @throws IOException
	 */
	public static File readFile(String destFileName) throws IOException {
		File file = new File(destFileName);
		if (file.exists()) {
			LOGGER.info("目标文件已存在: " + destFileName);
			return file;
		}
		if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
			LOGGER.info("创建目录文件所在的目录失败!");
		}
		//创建目标文件
		if (file.createNewFile()) {
			LOGGER.info("创建单个文件成功: " + destFileName);
		} else {
			LOGGER.info("创建单个文件失败: " + destFileName);
		}
		return file;
	}

	/**
	 * 读取文件为byte[]
	 * @param destFileName
	 * @return
	 */
	public static byte[] readFileBytes(String destFileName) {
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(destFileName));
			int len = bufferedInputStream.available();
			byte[] bytes = new byte[len];
			int r = bufferedInputStream.read(bytes);
			if (len != r) {
				bytes = null;
				LOGGER.error("读取文件不正确");
			}
			bufferedInputStream.close();
			return bytes;
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return new byte[0];
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			return new byte[0];
		}
	}

	/**
	 * 根据目录获取下面为指定文件名的文件
	 * @param dir
	 * @param filename
	 * @return
	 */
	public static List<String> getFiles(String dir, String filename) {
		List<String> list = new ArrayList<String>();
		Map<String, List<String>> map = getDirFile(dir);
		List<String> allList = map.get("files");
		if(allList == null) {
			list = new ArrayList<String>();
		} else {
			for (String string : allList) {
				if(string.endsWith(File.separator + filename)) {
					list.add(string);
				}
			}
		}
		return list;
	}

	/**
	 * 获取目录下的文件和文件夹
	 * @param dir
	 * @return
	 */
	public static Map<String, List<String>> getDirFile(String dir){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		File file = new File(dir);
		getDirFileDtl(file, map);
		return map;
	}
	/*
	 * 获取目录的指定内容
	 * @param file
	 * @param map
	 */
	private static void getDirFileDtl(File file, Map<String, List<String>> map){
		File[] t = file.listFiles();
		if(t == null) {
			return;
		}
		for(int i = 0; i < t.length; i++){
			//判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
			if(t[i].isDirectory()){
				List<String> dirs = map.get("dirs");
				if(dirs == null) {
					dirs = new ArrayList<String>();
				}
				dirs.add(t[i].getAbsolutePath());
				map.put("dirs", dirs);
				getDirFileDtl(t[i], map);
			} else{
				List<String> files = map.get("files");
				if(files == null) {
					files = new ArrayList<String>();
				}
				files.add(t[i].getAbsolutePath());
				map.put("files", files);
				getDirFileDtl(t[i], map);
			}
		}
	}

	/**
	 * 读取文件的内容到字符串
	 * @param path
	 * @return
	 */
	public static String readFileString(String path) {
		StringBuilder buffer = new StringBuilder();
		try {
			InputStream is = new FileInputStream(path);
			//用来保存每行读取的内容
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			//读取第一行
			line = reader.readLine();
			//如果 line 为空说明读完了
			while (line != null) {
				//将读到的内容添加到 buffer 中
				buffer.append(line);
				//添加换行符
				buffer.append("\n");
				//读取下一行
				line = reader.readLine();
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return buffer.toString();
	}

}