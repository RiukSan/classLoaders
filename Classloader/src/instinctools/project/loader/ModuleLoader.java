package instinctools.project.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ModuleLoader extends ClassLoader { //Single Responsibility Principle не выполнен, можно было выделить отдельный класс для работы с файловой системой.
	private String path;
	private Logger log = LogManager.getLogger("Logger");

	public ModuleLoader(String path, ClassLoader parent) {
		super(parent);
		this.path = path.trim();
		showAllFiles();
	}

	//Данный метод можно было бы разбить на 2 метода. Один занимается конкретно поиском, второй выгрузкой.
	@Override
	public Class<?> findClass(String fileName) throws ClassNotFoundException {
		try {
			JarFile jf = new JarFile(path + "\\" + fileName);
			byte[] b = null;
			Enumeration entries = jf.entries();
			showAllEntries(jf);
			Scanner sc = new Scanner(System.in);
			String className = sc.nextLine();
			Class result = null;
			while (entries.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) entries.nextElement();
				if (jarEntry.getName().endsWith(className + ".class")) {
					b = loadClassData(jf, jarEntry);
					result = defineClass(null, b, 0, b.length);
				}
			}
			return result;
		} catch (FileNotFoundException ex) {
			return super.findClass(fileName);
		} catch (IOException ex) {
			return super.findClass(fileName);
		}
	}

	private void showAllEntries(JarFile jf) {
		log.info("Write class name (without extension)");
		Enumeration entries = jf.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) entries.nextElement();
			log.info(jarEntry.getName());
		}
	}

	private void showAllFiles() {
		log.info("Choose a file");
		File dir = new File(path);
		String[] modules = dir.list();
		for (String module : modules) {
			log.info(module);
		}
	}

	public void getAllMethods(Class clazz) {
		log.info("Choose method to invoke");
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			log.info(methods[i].getName());
		}
	}

	private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
		long size = jarEntry.getSize();
		if (size == -1 || size == 0)
			return null;
		byte[] data = new byte[(int) size];
		InputStream in = jarFile.getInputStream(jarEntry);
		in.read(data);
		return data;
	}
}
