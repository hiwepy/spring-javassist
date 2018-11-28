package org.springframework.javassist;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javassist.bytecode.EndpointApiCtClassBuilder;
import org.springframework.javassist.bytecode.definition.MvcBound;
import org.springframework.javassist.bytecode.definition.MvcMethod;
import org.springframework.javassist.bytecode.definition.MvcParam;
import org.springframework.javassist.bytecode.definition.MvcParamFrom;
import org.springframework.web.bind.annotation.RequestMethod;

import javassist.CtClass;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EndpointApiCtClassBuilder_Test {

	//@Test
	@SuppressWarnings("deprecation")
	public void testClass() throws Exception {
		
		CtClass ctClass = new EndpointApiCtClassBuilder("org.apache.cxf.spring.boot.FirstCase1")
				.controller()
				.makeField("public int k = 3;")
				.newField(String.class, "uid", UUID.randomUUID().toString())
				.makeMethod("public void sayHello(String txt) { System.out.println(txt); }")
				.build();
		
		Class clazz = ctClass.toClass();
		
		System.err.println("=========Type Annotations======================");
		for (Annotation element : clazz.getAnnotations()) {
			System.out.println(element.toString());
		}
		
		System.err.println("=========Fields======================");
		for (Field element : clazz.getDeclaredFields()) {
			System.out.println(element.getName());
			for (Annotation anno : element.getAnnotations()) {
				System.out.println(anno.toString());
			}
		}
		System.err.println("=========Methods======================");
		for (Method element : clazz.getDeclaredMethods()) {
			System.out.println(element.getName());
			for (Annotation anno : element.getAnnotations()) {
				System.out.println(anno.toString());
			}
		}
		System.err.println("=========sayHello======================");
		Method sayHello = clazz.getMethod("sayHello", String.class);
		sayHello.invoke(ConstructorUtils.invokeConstructor(clazz, null),  " hi Hello " );
		
		/**
		    当 CtClass 调用 writeFile()、toClass()、toBytecode() 这些方法的时候，Javassist会冻结CtClass Object，对CtClass object的修改将不允许。
		    这个主要是为了警告开发者该类已经被加载，而JVM是不允许重新加载该类的。如果要突破该限制，方法如下：
		*/
		ctClass.writeFile();
		ctClass.defrost();
		
		/**
		 * 1、api名称
		 * 2、参数名称
		 * 
		 */
		
		byte[] byteArr = ctClass.toBytecode();
		FileOutputStream output = new FileOutputStream(new File("D://FirstCaseV2.class"));
		
		IOUtils.write(byteArr, output);
		IOUtils.closeQuietly(output);
		
	}
	
	@Test
	public void testInstance() throws Exception{
		
		InvocationHandler handler = new EndpointApiInvocationHandler();

		Object ctObject = new EndpointApiCtClassBuilder("org.apache.cxf.spring.boot.FirstCaseV2")
				.newMethod("sayHello", "say/{word}", RequestMethod.POST, MediaType.ALL_VALUE, new MvcBound("100212"),
						new MvcParam(String.class, "text"))
				.newMethod(ResponseEntity.class,
						new MvcMethod("sayHello2", new String[] { "say2/{word}", "say22/{word}" }, new RequestMethod[] {RequestMethod.POST, RequestMethod.GET} ),
						new MvcBound("100212"), new MvcParam(String.class, "word", MvcParamFrom.PATH))
				.controller()
				.makeField("public int k = 3;")
				.newField(String.class, "uid", UUID.randomUUID().toString())
				.toInstance(handler);
		Class clazz = ctObject.getClass();
		
		System.err.println("=========Type Annotations======================");
		for (Annotation element : clazz.getAnnotations()) {
			System.out.println(element.toString());
		}
		
		System.err.println("=========Fields======================");
		for (Field element : clazz.getDeclaredFields()) {
			System.out.println(element.getName());
			for (Annotation anno : element.getAnnotations()) {
				System.out.println(anno.toString());
			}
		}
		System.err.println("=========Methods======================");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
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
			}
			searchType = searchType.getSuperclass();
		}
		System.err.println("=========sayHello======================");
		Method sayHello = clazz.getMethod("sayHello", String.class);
		sayHello.invoke(ctObject,  " hi Hello " );
		System.err.println("=========sayHello2======================");
		Method sayHello2 = clazz.getMethod("sayHello2", String.class);
		sayHello2.invoke(ctObject,  " hi Hello2 " );
	}

}
