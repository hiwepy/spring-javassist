package org.springframework.javassist;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.javassist.annotation.WebBound;

public class EndpointApiInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		System.err.println("=========Method Invoke ======================");
		
		// 获取方法上绑定的数据注解
		WebBound bound = method.getAnnotation(WebBound.class);
		if(bound == null) {
			Class<?> declaringClass = method.getDeclaringClass();
			bound = declaringClass.getAnnotation(WebBound.class);
		}
		
		System.out.println("bound : " + bound.toString());
		
		System.out.println(method.getName());
		System.err.println("=========Method Annotations======================");
		for (Annotation anno : method.getAnnotations()) {
			System.out.println(anno.toString());
		}
		System.err.println("=========Method Parameter Annotations======================");
		for (Annotation[] anno : method.getParameterAnnotations()) {
			if(anno != null && anno.length > 0) {
				System.out.println(anno[0].toString());
			}
		}
		
		for (Object arg : args) {
			System.out.println(arg.toString());
		}
		
		return null;
		
	}

}
