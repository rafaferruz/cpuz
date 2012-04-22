package com.cpuz.st2.actions;

import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Role;
import com.cpuz.service.InitService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase prepara la información a mostrar en la ventana inicial de la aplicación
 */
public class LogoutAction extends ActionSupport implements RequestAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<NewsPiece> dataList = new ArrayList<>();
    private InitService initService;
    private Map<Integer, String> mapStatus = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private String selec1;

    public LogoutAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        requestAttributes.put("page", "/WEB-INF/pages/logout.jsp");
        return "logout";
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
