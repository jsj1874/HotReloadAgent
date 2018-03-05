package com.reload.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {
	
	/**
	 *
	 * 获取jarFile文件
	 * @param jarPath jar包路径
	 * 
	 */
	public static JarFile getJarFile(String jarPath){
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jarFile;
	}
	
	/**
	 *
	 * jarEntry对象实例 映射实例
	 * @param jarFile
	 * 
	 */
	public static Map<String, JarEntry> getJarEntries(JarFile jarFile){
		Enumeration<JarEntry> jarEntrys = jarFile.entries();
		JarEntry jarEntry = null;
		Map<String, JarEntry> jarEntryMap = new HashMap<>();
		String className = "";
		
		for( ;jarEntrys.hasMoreElements() ; ){
			jarEntry = jarEntrys.nextElement();
			if(jarEntry.getName().endsWith(".class")){
				className = ClassUtils.pathToClassName(jarEntry.getName());
				if(className != null && ClassUtils.pathToClassName(jarEntry.getName()).length() > 0){
					jarEntryMap.put(ClassUtils.pathToClassName(jarEntry.getName()), jarEntry);
				}
			}
		}
		return jarEntryMap;
	}
	
	public static Map<String,byte[]> getJarEnetryByteMap(JarFile jarFile){
		Map<String, JarEntry> jarEntries = getJarEntries(jarFile);
		Map<String,byte[]> jarBytesMap = new HashMap<>();
		for(Entry<String, JarEntry> jarEntry:jarEntries.entrySet()){
			jarBytesMap.put(jarEntry.getKey(), getJarEentryBytes(jarFile, jarEntry.getValue()));
			System.out.println(jarEntry.getKey().length());
		}
		return jarBytesMap;
	}
	
	/**
	 *
	 * jarEntry 二进制 对象实例 映射实例
	 * @param jarFile
	 * 
	 */
	public static byte[] getJarEentryBytes(JarFile jarFile,JarEntry jarEntry){
		byte[] data = null;
		try {
			InputStream in = jarFile.getInputStream(jarEntry);
			data = FileUtils.inputStreamToBytes(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	
	

}
