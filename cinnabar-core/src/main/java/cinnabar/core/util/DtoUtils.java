package cinnabar.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DtoUtils {

	public static <O, N> List<N> convertListToTarget(List<O> originalList, List<N> newList, Class<N> clazz) throws InstantiationException, IllegalAccessException {
		for (O originalObject: ifNullReturnEmpty(originalList)) {
			N newObject = clazz.newInstance();
			newObject = convertObjectToTarget(originalObject, newObject);
			newList.add(newObject);
		}
		return newList;
	}
	
	public static <O, N> N convertObjectToTarget(O originalObject, N newObject) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz_o = originalObject.getClass();
		List<Field> fields_o = ReflectionUtils.getFields(clazz_o, new ArrayList<Field>());
		Class<?> clazz_n = newObject.getClass();
		List<Field> fields_n = ReflectionUtils.getFields(clazz_n, new ArrayList<Field>());
		for (Field field_o: ifNullReturnEmpty(fields_o)) {
			for (Field field_n: ifNullReturnEmpty(fields_n)) {
				if (field_o.getName().equals(field_n.getName()) && field_o.getType() == field_n.getType()) {
					field_n.setAccessible(true);
					field_o.setAccessible(true);
					field_n.set(newObject, field_o.get(originalObject));
				}
			}
		}
		return newObject;
	}
	
	public static <T> Collection<T> ifNullReturnEmpty(Collection<T> coll) {
		return coll == null ? Collections.emptyList() : coll;
	}
	
}
