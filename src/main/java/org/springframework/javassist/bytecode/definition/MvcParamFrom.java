package org.springframework.javassist.bytecode.definition;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

/**
 * 参数注解类型枚举
 * 
 * @see org.springframework.web.bind.annotation.CookieValue
 * @see org.springframework.web.bind.annotation.MatrixVariable
 * @see org.springframework.web.bind.annotation.PathVariable
 * @see org.springframework.web.bind.annotation.RequestAttribute
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see org.springframework.web.bind.annotation.RequestHeader
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see org.springframework.web.bind.annotation.RequestPart
 */
public enum MvcParamFrom {

	/**
	 * Annotation which indicates that a method parameter should be bound to an HTTP
	 * cookie.
	 * 
	 * @see org.springframework.web.bind.annotation.CookieValue
	 */
	COOKIE("COOKIE", "HTTP Cookie"),
	/**
	 * Annotation which indicates that a method parameter should be bound to a
	 * name-value pair within a path segment. Supported for {@link RequestMapping}
	 * annotated handler methods in Servlet environments.
	 * 
	 * @see org.springframework.web.bind.annotation.MatrixVariable
	 */
	MATRIX("MATRIX", "Name-value Pair Within A Path Segment"),
	/**
	 * Annotation which indicates that a method parameter should be bound to a URI
	 * template variable. Supported for {@link RequestMapping} annotated handler
	 * methods in Servlet environments.
	 * 
	 * @see org.springframework.web.bind.annotation.PathVariable
	 */
	PATH("PATH", "URI Template Variable"),
	/**
	 * Annotation to bind a method parameter to a request attribute.
	 * 
	 * @see org.springframework.web.bind.annotation.RequestAttribute
	 */
	ATTR("ATTR", "Request Attribute"),
	/**
	 * Annotation indicating a method parameter should be bound to the body of the
	 * web request. The body of the request is passed through an
	 * {@link HttpMessageConverter} to resolve the method argument depending on the
	 * content type of the request. Optionally, automatic validation can be applied
	 * by annotating the argument with {@code @Valid}.
	 * 
	 * @see org.springframework.web.bind.annotation.RequestBody
	 */
	BODY("BODY", "Request Body"),
	/**
	 * Annotation which indicates that a method parameter should be bound to a web
	 * request header.
	 *
	 * <p>
	 * Supported for annotated handler methods in Servlet and Portlet environments.
	 * </p>
	 *
	 * <p>
	 * If the method parameter is {@link java.util.Map Map&lt;String, String&gt;},
	 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String,
	 * String&gt;}, or {@link org.springframework.http.HttpHeaders HttpHeaders} then
	 * the map is populated with all header names and values.
	 * </p>
	 * 
	 * @see org.springframework.web.bind.annotation.RequestHeader
	 */
	HEADER("HEADER", "Request Header"),
	/**
	 * Annotation which indicates that a method parameter should be bound to a web
	 * request parameter.
	 *
	 * <p>
	 * Supported for annotated handler methods in Servlet and Portlet environments.
	 * </p>
	 *
	 * <p>
	 * If the method parameter type is {@link Map} and a request parameter name is
	 * specified, then the request parameter value is converted to a {@link Map}
	 * assuming an appropriate conversion strategy is available.
	 * </p>
	 * <p>
	 * If the method parameter is {@link java.util.Map Map&lt;String, String&gt;} or
	 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String,
	 * String&gt;} and a parameter name is not specified, then the map parameter is
	 * populated with all request parameter names and values.
	 * </p>
	 * 
	 * @see org.springframework.web.bind.annotation.RequestParam
	 */
	PARAM("PARAM", "Request Parameter"),

	/**
	 * Annotation that can be used to associate the part of a "multipart/form-data"
	 * request with a method argument.
	 *
	 * <p>
	 * Supported method argument types include {@link MultipartFile} in conjunction
	 * with Spring's {@link MultipartResolver} abstraction,
	 * {@code javax.servlet.http.Part} in conjunction with Servlet 3.0 multipart
	 * requests, or otherwise for any other method argument, the content of the part
	 * is passed through an {@link HttpMessageConverter} taking into consideration
	 * the 'Content-Type' header of the request part. This is analogous to
	 * what @{@link RequestBody} does to resolve an argument based on the content of
	 * a non-multipart regular request.
	 *
	 * <p>
	 * Note that @{@link RequestParam} annotation can also be used to associate the
	 * part of a "multipart/form-data" request with a method argument supporting the
	 * same method argument types. The main difference is that when the method
	 * argument is not a String, @{@link RequestParam} relies on type conversion via
	 * a registered {@link Converter} or {@link PropertyEditor}
	 * while @{@link RequestPart} relies on {@link HttpMessageConverter}s taking
	 * into consideration the 'Content-Type' header of the request
	 * part. @{@link RequestParam} is likely to be used with name-value form fields
	 * while @{@link RequestPart} is likely to be used with parts containing more
	 * complex content (e.g. JSON, XML).
	 * 
	 * @see org.springframework.web.bind.annotation.RequestPart
	 */
	PART("PART", "\"multipart/form-data\" Request Part");
	
	private String key;
	private String desc;

	private MvcParamFrom(String key, String desc) {
		this.key = key;
		this.desc = desc;

	}
	
	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public boolean equals(MvcParamFrom from) {
		return this.compareTo(from) == 0;
	}

	public boolean equals(String from) {
		return this.compareTo(MvcParamFrom.valueOfIgnoreCase(from)) == 0;
	}
	
	public static MvcParamFrom valueOfIgnoreCase(String from) {
		for (MvcParamFrom fromEnum : MvcParamFrom.values()) {
			if (fromEnum.getKey().equals(from)) {
				return fromEnum;
			}
		}
		throw new NoSuchElementException("Cannot found MvcParamFrom with from '" + from + "'.");
	}
	
	public Map<String, String> toMap() {
		Map<String, String> driverMap = new HashMap<String, String>();
		driverMap.put("key", this.getKey());
		driverMap.put("desc", this.getDesc());
		return driverMap;
	}
	
	public static List<Map<String, String>> fromList() {
		List<Map<String, String>> fromList = new LinkedList<Map<String, String>>();
		for (MvcParamFrom fromEnum : MvcParamFrom.values()) {
			fromList.add(fromEnum.toMap());
		}
		return fromList;
	}

}
