package com.app.filter;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AtFilter implements Filter {
	
	
	 private final Logger LOG =
	            Logger.getLogger(AtFilter.class.getName());

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        LOG.info("Authentication Validation is in progress");
	        chain.doFilter(request, response);
	    }

}
