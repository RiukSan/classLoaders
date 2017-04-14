package instinctools.project;

import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import instinctools.project.loader.MethodInvoker;
import instinctools.project.loader.ModuleLoader;

public class Main {

	public static void main(String[] args) {
		Logger log = LogManager.getLogger("Logger");
		log.info("Write a path to folder");
		Scanner sc = new Scanner(System.in);
		String modulePath = sc.nextLine();
		ModuleLoader loader = new ModuleLoader(modulePath, ClassLoader.getSystemClassLoader());
		String className = sc.nextLine();
		MethodInvoker invoker = new MethodInvoker();
		try {
			Class clazz = loader.loadClass(className);
			loader.getAllMethods(clazz);
			String methodName = sc.nextLine();
			invoker.invokeClassMethod(clazz, methodName);
		} catch (ClassNotFoundException e) {
			log.error(e.toString());
		}
	}

}