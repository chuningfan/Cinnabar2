package cinnabar.core.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
	
	public static List<Field> getFields(Class<?> clazz, List<Field> emptyList) {
		Class<?> superClass = clazz.getSuperclass();
		emptyList.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (superClass.getName().equals("java.lang.Object")) {
			return emptyList;
		} else {
			return getFields(superClass, emptyList);
		}
	}
	
	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			Class<?> superClass = clazz.getSuperclass();
			if (!superClass.getName().equals("java.lang.Object")) {
				return getField(superClass, name);
			} else {
				throw new NoSuchFieldException("There is not a field named " + name);
			}
		}
	}
}
