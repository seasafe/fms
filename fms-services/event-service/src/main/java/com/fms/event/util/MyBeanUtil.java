/**
 * 
 */
package com.fms.event.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * @author kesah
 *
 */
public class MyBeanUtil {

	public static <V, T> V copyProperties(T source, Class<V> targetClazz) {
		V entity = null;
		try {
			entity = targetClazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BeanUtils.copyProperties(source, entity);
		return entity;
	}

	public static <V,T> List<V> copyProperties(List<T> source, Class<V> targetClazz) {
		List<V> list = new ArrayList<>();

		for (T t : source) {
			list.add(copyProperties(t, targetClazz));
		}

		return list;
	}
	
}
