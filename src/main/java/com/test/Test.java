package com.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.reload.HotReloadAgent;
import com.reload.utils.ClassUtils;
import com.reload.utils.FileUtils;
import com.reload.utils.JarUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		/*Hello hello = new Hello();
		hello.sayHello();
		hello.sayHello1();
		String path = ClassUtils.class2file("F:\\study\\HotReloadAgent\\reload\\", "Hello");
		File file = new File("F:\\study\\HotReloadAgent\\reload\\Hello.class");
		byte[] reloadData = FileUtils.file2Bytes(file);
		System.out.println("print" + "data.length:"+reloadData.length);
		HotReloadAgent.reload(Hello.class, reloadData);
		hello.sayHello();
		hello.sayHello1();*/
		/*String jarPath = "F:\\study\\HotReloadAgent\\reload\\reload.jar";
		Map<Class<?>,String> jarMap = ClassUtils.getJarFileMessage(jarPath);
		for(Map.Entry<Class<?>, String> jar:jarMap.entrySet()){
			System.out.println(jar.getKey().getClass().getName()+"\t"+jar.getValue());
		}
		JarFile jarFile = new JarFile(jarPath);
		Enumeration<JarEntry>jarEntrys = jarFile.entries();
		JarEntry entry = null;
		 byte[] data = null;
		while(jarEntrys.hasMoreElements()){
			entry = jarEntrys.nextElement();
			if(entry.getName().equals("Hello.class")){
				InputStream in = jarFile.getInputStream(entry);
				data = FileUtils.inputStreamToBytes(in);
			}
		}
		  Hello hello = new Hello();
		  System.out.println("print" + "data.length:"+data.length);
		  hello.sayHello1();
		  HotReloadAgent.reload(Hello.class, data);
		  hello.sayHello();
		  hello.sayHello1();*/
		  String jarPath = "F:\\study\\HotReloadAgent\\reload\\reload.jar";
		  JarFile jarFile = JarUtils.getJarFile(jarPath);
		  Map<String,byte[]> map = JarUtils.getJarEnetryByteMap(jarFile);
		  byte[] data = map.get("Hello.class");
		  Hello hello = new Hello();
		  for(Map.Entry<String, byte[]> entry:map.entrySet()){
			  System.out.println(entry.getKey());
		  }
		  System.out.println("print" + "data.length:"+data.length);
		  hello.sayHello1();
		  HotReloadAgent.reload(Hello.class, data);
		  hello.sayHello();
		  hello.sayHello1();
	}
}
