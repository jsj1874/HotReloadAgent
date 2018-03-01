package com.reload.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {

	public static byte[] file2Bytes(File file){
		byte[] datas = null;
		try {
			
			 FileInputStream fileInputStream = new FileInputStream(file);
			 datas = new byte[4096];
			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 int n = 0;
			 try {
				for(;(n = fileInputStream.read(datas))!= -1;){
					 bos.write(datas, 0, n);
				}
				fileInputStream.close();
	            bos.close();
	            datas = bos.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
		
	}
}
