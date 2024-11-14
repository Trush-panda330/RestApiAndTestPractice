package com.example.rest.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		//リクエストのログを出力
		log.info("[IN]{}:{}",req.getMethod(), req.getRequestURI());
		
		try { 
			//他のフィルタやコントローラに例外を渡す
			chain.doFilter(request, response);
		} finally {
			//レスポンスのログを出力
			log.info("[OUT] {}: {}", req.getMethod(), req.getRequestURI());
		}
	}

}
