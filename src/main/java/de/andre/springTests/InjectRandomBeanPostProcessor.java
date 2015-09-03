package de.andre.springTests;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by andreika on 9/1/2015.
 */
public class InjectRandomBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		for (Field declaredField : declaredFields) {
			InjectRandom annotation = declaredField.getAnnotation(InjectRandom.class);
			if (annotation != null) {
				int min = annotation.min();
				int max = annotation.max();
				Random random = new Random();
				declaredField.setAccessible(true);
				ReflectionUtils.setField(declaredField, bean, min + random.nextInt(max - min));
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
