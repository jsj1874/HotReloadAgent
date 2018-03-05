package com.reload.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.reload.HotReloadAgent;
import com.reload.constants.Constants;
import com.reload.exception.FileIsNotDirectoryException;
import com.test.Hello;

public class ClassUtils {
	
	
	
	
    /**
     * 类名转为文件名
     * @param name
     * @return
     */
    public static String class2file(String path,String name){
        /**编译后的class文件存放的目录**/
        //String baseDir = "F:\\java Projects\\ClassLoader\\class\\";
    	String baseDir = path;
        name = name.replace("." , File.separator);
        name = baseDir + name + ".class";
        return name;
    }
    

    /**
     * 读取文件byte数组
     * @param fileName
     * @return
     */
    public static byte[] file2bytes(String fileName){
        RandomAccessFile file = null;
        FileChannel channel = null;
        byte[] bytes = null;
        try {
            /**随机存取文件对象，只读取模式**/
            file = new RandomAccessFile(fileName , "r");
            /**NIO文件通道**/
            channel = file.getChannel();
            /**NIO字节缓冲**/
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int size = (int) channel.size();
            bytes = new byte[size];
            int index = 0;
            /**从NIO文件通道读取数据**/
            while (channel.read(buffer) > 0){
                /**字节缓冲从写模式转为读取模式**/
                buffer.flip();
                while (buffer.hasRemaining()){
                    bytes[index] = buffer.get();
                    ++index;
                }
                /**字节缓冲的readerIndex、writerIndex置零**/
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }
    
    /**
     *
     * class文件转化为二进制数组
     * 
     */
    public byte[] fileToBytes(String classPathUrl){
    	byte[] bytes = null;
    	Path classPath = Paths.get(classPathUrl);
    	try {
			InputStream inputStream = Files.newInputStream(classPath);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			DataInputStream dataOutputStream = new DataInputStream(inputStream);
			/*ByteArrayInputStream bao = new ByteArrayInputStream(arg0)
			  dataInputStream.*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return bytes;
    }
    
    /**
     *
     * 批量加载可更新类
     * @throws FileNotFoundException 
     * 
     */
    public static Map<String,byte[]> classesToBytes(String reloadClassesPath) throws FileNotFoundException{
    	Map<String,byte[]> classesMap = new HashMap<>();
    	File dir = new File(reloadClassesPath);
    	if(!dir.isDirectory()){
    		try{
    			throw new FileIsNotDirectoryException(dir); 
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
			}
    	}
    	File[] files = dir.listFiles();
    	List<File> classesList = new ArrayList<>();
    	for(File calssFile:files){
    		if(calssFile.getName().endsWith(".class")){
    			classesList.add(calssFile);
    			
    		}
    	}
    	for(File file:classesList){
    		classesMap.put(file.getName(), FileUtils.file2Bytes(file));
    	}
    	return classesMap;
    }
    
    /**
     * 
     * jar中的class文件转化为二进制
     * 
     */
    public static Map<Class<?>,String> getJarFileMessage(String jarPath){
    	JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    	return getJarFileMessage(jarFile);
    }
    
    public static  Map<Class<?>,String> getJarFileMessage(JarFile jarFile){
    	Map<Class<?>,String> classesMap = new HashMap<>();
    	JarEntry jarEntry = null;
    	Enumeration<JarEntry> jarEntrys = jarFile.entries();  
    	while(jarEntrys.hasMoreElements()){
    		jarEntry = jarEntrys.nextElement();
    		if(jarEntry.getName().endsWith(".class")){
    			classesMap.put(jarEntry.getClass(), jarEntry.getName());
    			try {
					System.out.println(jarEntry.getAttributes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	return classesMap;
    }
    
    
    
    
    
    public static Map<Class<?>,byte[]> jarFileToBytes(String jarPath,Map<Class<?>,String> jarFileMap){
    	Map<Class<?>,byte[]> classBytesMap = new HashMap<>();
    	jarFileMap.forEach((clazz,path)->{
    		
    		byte[] data = file2bytes(jarPath+path);
    		classBytesMap.put(clazz, data);
    	});
    	return classBytesMap;
    }
    
    public static Map<Class<?>,byte[]> jarFileToBytes(Map<Class<?>,String> jarFileMap){
    	Map<Class<?>,byte[]> classBytesMap = new HashMap<>();
    	jarFileMap.forEach((clazz,path)->{
    		
    		byte[] data = file2bytes(Constants.DEFAULT_DIR+path);
    		classBytesMap.put(clazz, data);
    	});
    	return classBytesMap;
    }
   
    
    private static final String CLASS_NAME_REGX = ".+/(.+)$";
    public static String getClassName(String classPath){
    	if(classPath.endsWith(".class")){
    		return "";
    	}
    	String className = "";
        Pattern p = Pattern.compile(CLASS_NAME_REGX);  
        Matcher m = p.matcher(classPath);  
        if (!m.find()) {  
            System.out.println("文件路径格式错误!");  
            return className;  
        }  
    	return classPath;
    }
    

    public static Class<?> getClass(String classname){
    	try {
			return Class.forName(classname);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
     
    public static void main(String[] args) throws IOException {
    
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
		  System.out.println("print");
		  hello.sayHello1();
		  HotReloadAgent.reload(Hello.class, data);
		  hello.sayHello();
		  hello.sayHello1();*/
    	
    	Hello hello = new Hello();
    	File file = new File("F:\\study\\HotReloadAgent\\reload\\Hello.class");
		//byte[] reloadData = ClassUtils.file2byte(path);
		hello.sayHello1();
		byte[] reloadData = FileUtils.file2Bytes(file);
		HotReloadAgent.reload(Hello.class, reloadData);
		hello.sayHello();
		hello.sayHello1();
		System.out.println(System.getProperty("user.dir"));
 
	}

    /**
     *
     * jar包内的路径转类名
     */
    public static String pathToClassName(String clazzPath){
    	if(!clazzPath.endsWith(".class")){
    		return "";
    	}
    	String packageClassName = clazzPath.replace("/", ".");
    	return packageClassName;
    }

}
