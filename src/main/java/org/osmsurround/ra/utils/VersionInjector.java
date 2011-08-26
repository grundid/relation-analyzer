package org.osmsurround.ra.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class VersionInjector extends HandlerInterceptorAdapter {

	private String appVersion;

	public VersionInjector(String appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("version", appVersion);
		return true;
	}
}