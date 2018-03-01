package com.reload.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reload.exception.FileIsNotDirectoryException;

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
    public static byte[] file2byte(String fileName){
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

    public static void main(String[] args) {
    	File file = new File("F:\\study\\HotReloadAgent\\reload\\Hello.class");
		System.out.println(file.getName());
	}

}
