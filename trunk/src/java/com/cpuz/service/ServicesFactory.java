package com.cpuz.service;

import com.cpuz.exceptions.UserException;
import org.springframework.context.ApplicationContext;

/**
 * Factoría para proporcionar a los jsp acceso para obtener objetos desde un contendor Spring
 */
public class ServicesFactory {
	
	private ApplicationContext springContext;
	private String beanName;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public ApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	public Object getReturnedService() throws UserException {
		if (springContext!=null && beanName!=null && !beanName.isEmpty()){
		return springContext.getBean(beanName);}
		else{
			throw new UserException("ServicesFactory.errorObtainingObjectFromSpringContainer");
		}
	}
	
	
}
