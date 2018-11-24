package org.springframework.javassist.bytecode;

import org.omg.CORBA.ServerRequest;
import org.springframework.javassist.bytecode.definition.MvcBound;
import org.springframework.javassist.bytecode.definition.MvcMethod;
import org.springframework.javassist.utils.EndpointApiUtils;

import com.github.vindell.javassist.utils.JavassistUtils;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 动态构建Controller接口
 */
public class WebFluxEndpointApiCtClassBuilder extends EndpointApiCtClassBuilder  {
	
	public static final String METHOD_MONO_NAME = "mono";
	public static final String METHOD_FLUX_NAME = "flux";
	
	public WebFluxEndpointApiCtClassBuilder(final String classname) throws CannotCompileException, NotFoundException  {
		super(classname, WebFluxEndpointApi.class);
	}
	
	public WebFluxEndpointApiCtClassBuilder(final ClassPool pool, final String classname) throws CannotCompileException, NotFoundException {
		super(pool, classname, WebFluxEndpointApi.class);
	}
	
	/**
	 * @param methodName   	： 方法名称
	 * @param bound  		：方法绑定数据信息
	 * @return {@link WebFluxEndpointApiCtClassBuilder} instance
	 * @throws CannotCompileException if can't compile
	 * @throws NotFoundException  if not found
	 */
	public WebFluxEndpointApiCtClassBuilder monoMethod(final MvcBound bound) throws CannotCompileException, NotFoundException {
		
		ConstPool constPool = this.classFile.getConstPool();
		
		// 方法参数
		CtClass[] parameters = new CtClass[1];
				  parameters[0] = pool.get(ServerRequest.class.getName());
		// 创建方法
		CtClass returnType = pool.get(Mono.class.getName());
		CtMethod ctMethod = new CtMethod(returnType, METHOD_MONO_NAME, parameters, declaring);
		
        // 设置方法体
        EndpointApiUtils.methodBody(ctMethod, METHOD_MONO_NAME);
        // 设置方法异常捕获逻辑
        EndpointApiUtils.methodCatch(pool, ctMethod);
        // @WebBound 注解
        EndpointApiUtils.methodBound(ctMethod, constPool, bound);
        
        //新增方法
        declaring.addMethod(ctMethod);
        
        return this;

	}
	
	/**
	 * @param bound  		：方法绑定数据信息
	 * @return {@link WebFluxEndpointApiCtClassBuilder} instance
	 * @throws CannotCompileException if can't compile
	 * @throws NotFoundException  if not found
	 */
	public WebFluxEndpointApiCtClassBuilder fluxMethod(final MvcBound bound) throws CannotCompileException, NotFoundException {
		
		ConstPool constPool = this.classFile.getConstPool();
		
		// 方法参数
		CtClass[] parameters = new CtClass[1];
				  parameters[0] = pool.get(ServerRequest.class.getName());
		// 创建方法
		CtClass returnType = pool.get(Flux.class.getName());
		CtMethod ctMethod = new CtMethod(returnType, METHOD_FLUX_NAME, parameters, declaring);
		
        // 设置方法体
        EndpointApiUtils.methodBody(ctMethod, METHOD_FLUX_NAME);
        // 设置方法异常捕获逻辑
        EndpointApiUtils.methodCatch(pool, ctMethod);
        // @WebBound 注解
        EndpointApiUtils.methodBound(ctMethod, constPool, bound);
        
        //新增方法
        declaring.addMethod(ctMethod);
        
        return this;

	}
	
	/**
	 * 
	 * 根据参数构造一个新的方法
	 * @param rtClass ：返回对象类型
	 * @param method ：方法注释信息
	 * @param bound  ：方法绑定数据信息
	 * @param <T> 	   ： 参数泛型
	 * @return {@link WebFluxEndpointApiCtClassBuilder} instance
	 * @throws CannotCompileException if can't compile
	 * @throws NotFoundException  if not found
	 */ 
	public <T> WebFluxEndpointApiCtClassBuilder newMethod(final Class<T> rtClass, final MvcMethod method, final MvcBound bound) throws CannotCompileException, NotFoundException {
	       
		ConstPool constPool = this.classFile.getConstPool();
		
		// 创建抽象方法
		CtClass returnType = rtClass != null ? pool.get(rtClass.getName()) : CtClass.voidType;
		// 方法参数
		CtClass[] parameters = new CtClass[1];
				  parameters[0] = pool.get(ServerRequest.class.getName());
		// 创建方法
		CtMethod ctMethod = new CtMethod(returnType, method.getName(), parameters, declaring);
		
        // 设置方法体
        EndpointApiUtils.methodBody(ctMethod, method);
        // 设置方法异常捕获逻辑
        EndpointApiUtils.methodCatch(pool, ctMethod);
        // @WebBound 注解
        EndpointApiUtils.methodBound(ctMethod, constPool, bound);
        
        //新增方法
        declaring.addMethod(ctMethod);
        
        return this;
	}
	
	public <T> WebFluxEndpointApiCtClassBuilder removeMono() throws NotFoundException {
		return this.removeMethod(METHOD_MONO_NAME);
	}
	
	public <T> WebFluxEndpointApiCtClassBuilder removeFlux() throws NotFoundException {
		return this.removeMethod(METHOD_FLUX_NAME);
	}
	
	public <T> WebFluxEndpointApiCtClassBuilder removeMethod(final String methodName) throws NotFoundException {
		
		// 方法参数
		CtClass[] parameters = new CtClass[1];
				  parameters[0] = pool.get(ServerRequest.class.getName());
			
		// 检查方法是否已经定义
		if(!JavassistUtils.hasMethod(declaring, methodName, parameters)) {
			return this;
		}
		
		declaring.removeMethod(declaring.getDeclaredMethod(methodName, parameters));
		
		return this;
	}

}