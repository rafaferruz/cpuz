/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Filtro para creación de un contenedor Spring
 * 
 */
public class Spring2 implements Filter {

	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;

	public Spring2() {
	}

	private void doBeforeProcessing(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
	}

	private void doAfterProcessing(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
	}

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		doBeforeProcessing(request, response);

		/* Si no existe un atriubuto de aplicación 'springServicesContext', se crea un objeto
		 * ApplicationContext con ese nombre y se guarda en el ámbito de la aplicación.
		 */
		if (filterConfig.getServletContext().getAttribute("springServicesContext")==null){
			ApplicationContext springServicesContext=new ClassPathXmlApplicationContext("springServicesContext.xml");
			filterConfig.getServletContext().setAttribute("springServicesContext",springServicesContext);
		}

			chain.doFilter(request, response);

		doAfterProcessing(request, response);

	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destáoy method for this filter 
	 */
	public void destroy() {
	}

	/**
	 * Init method for this filter 
	 */
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
