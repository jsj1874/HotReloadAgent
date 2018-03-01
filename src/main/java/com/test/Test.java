package com.test;

import java.io.File;

import com.reload.HotReloadAgent;
import com.reload.utils.ClassUtils;
import com.reload.utils.FileUtils;

public class Test {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.sayHello();
		hello.sayHello1();
		String path = ClassUtils.class2file("F:\\study\\HotReloadAgent\\reload\\", "Hello");
		File file = new File("F:\\study\\HotReloadAgent\\reload\\Hello.class");
		//byte[] reloadData = ClassUtils.file2byte(path);
		byte[] reloadData = FileUtils.file2Bytes(file);
		HotReloadAgent.reload(Hello.class, reloadData);
		hello.sayHello();
		hello.sayHello1();
	}
}
