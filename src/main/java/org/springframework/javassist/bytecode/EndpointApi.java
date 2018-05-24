package org.springframework.javassist.bytecode;

import java.lang.reflect.InvocationHandler;

public abstract class EndpointApi {

	protected InvocationHandler handler;
	
	public EndpointApi() {
	}
	
	public EndpointApi(InvocationHandler handler) {
		this.handler = handler;
	}

	public InvocationHandler getHandler() {
		return handler;
	}
	
}