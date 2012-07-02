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
package com.cpuz.actions.admin;

import com.cpuz.service.InitService;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase prepara la información a mostrar en la ventana inicial de la aplicación
 */
public class InitAction extends ActionSupport implements RequestAware, Serializable {

    private InitService initService;
    private Map<String, Object> requestAttributes = new HashMap<>();

    public InitAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
		requestAttributes.put("recentNews",initService.getWhatsNew());
        requestAttributes.put("page", "/WEB-INF/pages/index.jsp");
        return "whatsNew";
    }
    @Override
    public void validate() {
        super.validate();
    }


	@Override
	public void setRequest(Map<String, Object> map) {
        this.requestAttributes = map;
	}
    public void setDataService(InitService initService) {
        this.initService = initService;
    }

	public InitService getInitService() {
		return initService;
	}

	public void setInitService(InitService initService) {
		this.initService = initService;
	}

	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}

	public void setRequestAttributes(Map<String, Object> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}


}
