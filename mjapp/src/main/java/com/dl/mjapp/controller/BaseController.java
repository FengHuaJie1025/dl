package com.dl.mjapp.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class BaseController{

	protected static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<HttpServletRequest>();
	protected static ThreadLocal<HttpServletResponse> _response = new ThreadLocal<HttpServletResponse>();
	protected static ThreadLocal<HttpSession> _session = new ThreadLocal<HttpSession>();

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){

		_request.set(request);
		_response.set(response);
		_session.set(request.getSession());
	}

	public HashMap<String,String> getRequestParamMap(){

		HashMap<String,String> map=new HashMap<String,String>();
		Enumeration<String> pNames=_request.get().getParameterNames();
		while(pNames.hasMoreElements()){
			String name=(String)pNames.nextElement();
			String cnm=_request.get().getParameter(name);
			String value=cnm.toString();
			map.put(name, value);
		}
		return map;
	}
}
