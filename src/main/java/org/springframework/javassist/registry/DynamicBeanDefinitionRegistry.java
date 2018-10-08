package org.springframework.javassist.registry;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface DynamicBeanDefinitionRegistry extends BeanDefinitionRegistry {

	/**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanClass			: The class type of bean 
     */
    public void registerBean(Class<?> beanClass);
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     */
    public void registerBean(Class<?> beanClass, String scope); 
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     * @param lazyInit			: lazyInit value
     */
    public void registerBean(Class<?> beanClass, String scope, boolean lazyInit); 
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     * @param lazyInit			: lazyInit value
     * @param autowireCandidate	: autowireCandidate value
     */
    public void registerBean(Class<?> beanClass, String scope, boolean lazyInit,boolean autowireCandidate); 
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanName			: The name of bean 
     * @param beanClass			: The class type of bean
     */
    public void registerBean(String beanName, Class<?> beanClass);
	
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanName			: The name of bean 
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     */
    public void registerBean(String beanName, Class<?> beanClass, String scope); 
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanName			: The name of bean 
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     * @param lazyInit			: lazyInit value
     */
    public void registerBean(String beanName, Class<?> beanClass, String scope, boolean lazyInit); 
    
    /**
     * 动态注册指定类型的Class到Spring上下文
     * @param beanName			: The name of bean 
     * @param beanClass			: The class type of bean
     * @param scope				: scope value
     * @param lazyInit			: lazyInit value
     * @param autowireCandidate	: autowireCandidate value
     */
    public void registerBean(String beanName, Class<?> beanClass, String scope, boolean lazyInit,boolean autowireCandidate); 
    
    
}
