package com.reload;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class HotReloadAgent {

	public static Instrumentation INSTRUMENTATION = null;
	
    public static void premain(String agentArgs, Instrumentation inst) {
    	INSTRUMENTATION = inst;
        System.out.println("Instrumentation has init!");
    }
    
    public static void reload(Class<?> clazz,byte[] reloadData){
    	ClassDefinition classDefinition = new ClassDefinition(clazz, reloadData);
    	try {
			INSTRUMENTATION.redefineClasses(classDefinition);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnmodifiableClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
