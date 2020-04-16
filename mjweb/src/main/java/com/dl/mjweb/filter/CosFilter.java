package com.dl.mjweb.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许跨域
 */
public class CosFilter extends GenericFilterBean {

    private final static String ALLOW_HEADERS = StringUtils.joinWith(",", HttpHeaders.CONTENT_TYPE, "ADMIN-" + HttpHeaders.AUTHORIZATION, "API-" + HttpHeaders.AUTHORIZATION);
    private final static String ALLOW_METHODS = StringUtils.joinWith(",", HttpMethod.GET, HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.OPTIONS);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,request.getHeader(HttpHeaders.ORIGIN));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,ALLOW_HEADERS);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,ALLOW_METHODS);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE,"3600");

        if (!CorsUtils.isPreFlightRequest(request)){
            filterChain.doFilter(request,response);
        }
    }
}
