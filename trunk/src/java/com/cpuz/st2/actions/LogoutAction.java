package com.cpuz.st2.actions;

import com.cpuz.domain.NewsPiece;
import com.cpuz.domain.Role;
import com.cpuz.model.InitModel;
import com.cpuz.st2.beans.ListControlParams;
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

    private ListControlParams control = new ListControlParams();
    private List<NewsPiece> dataList = new ArrayList<>();
    private InitModel initModel;
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
    public void setDataModel(InitModel initModel) {
        this.initModel = initModel;
    }

	public InitModel getInitModel() {
		return initModel;
	}

	public void setInitModel(InitModel initModel) {
		this.initModel = initModel;
	}

	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}

	public void setRequestAttributes(Map<String, Object> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}


}
