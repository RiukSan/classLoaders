package instinctools.project.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MethodInvoker { //Single Responsibility Principle выполнен

	public void invokeClassMethod(Class clazz, String methodName) {

		try {
			Constructor constructor = clazz.getConstructor();
			Object myClassObject = constructor.newInstance();
			Method method = clazz.getMethod(methodName);
			method.invoke(myClassObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}