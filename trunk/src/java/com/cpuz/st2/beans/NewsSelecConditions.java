/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.beans;

import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.RequestAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsSelecConditions extends ActionSupport implements  RequestAware,Serializable  {

    private ControlParams control = new ControlParams();
        private Map<String, Object> requestáttributes = new HashMap<String, Object>();

    // Variables de control de formulario
    private String titleKey;
    private String helpKey;
// Variables de datos de formulario
    private Integer fromDay;
    private Integer fromMonth;
    private Integer fromYear;
    private Integer toDay;
    private Integer toMonth;
    private Integer toYear;
    private Integer firstYear = 2008;
    private Integer lastYear;
    private String words;
    private Date firstDate;
    private Date lastDate;

    public NewsSelecConditions() {
            super();
}

        @Override
    public String execute() throws Exception {

            // Inicializa parámetros del formulario cuando es la primera vez que se lanza el listado
                control.setRunAction("list");
                this.setTitleKey("NewsSearch");
                this.setHelpKey("NewsSearchHelp");
                requestáttributes.put("page", "/NewsSelecConditions.jsp");
                return "EDIT";
    }




    public Integer getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(Integer firstYear) {
        this.firstYear = firstYear;
    }

    public Integer getFromDay() {
        return fromDay;
    }

    public void setFromDay(Integer fromDay) {
        this.fromDay = fromDay;
    }

    public Integer getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(Integer fromMonth) {
        this.fromMonth = fromMonth;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getLastYear() {
        if (this.lastYear == null) {
            this.lastYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        return this.lastYear;
    }

    public void setLastYear(Integer lastYear) {
        this.lastYear = lastYear;
    }

    public Integer getToDay() {
        return toDay;
    }

    public void setToDay(Integer toDay) {
        this.toDay = toDay;
    }

    public Integer getToMonth() {
        return toMonth;
    }

    public void setToMonth(Integer toMonth) {
        this.toMonth = toMonth;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getHelpKey() {
        return helpKey;
    }

    public void setHelpKey(String helpKey) {
        this.helpKey = helpKey;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public Map validate(HttpServletRequest request) {
        Map<String,String> errors = new HashMap<String,String>();


        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern("dd/MM/yyyy");
        try {
            firstDate = sdf.parse(String.valueOf(fromDay)
                    + "/" + String.valueOf(fromMonth)
                    + "/" + String.valueOf(fromYear));
        } catch (ParseException ex) {
            errors.put("Fecha inicial", "Fecha inicial err�nea.");
        }

        try {
            lastDate = sdf.parse(String.valueOf(toDay)
                    + "/" + String.valueOf(toMonth)
                    + "/" + String.valueOf(toYear));
        } catch (ParseException ex) {
            errors.put("Fecha final", "Fecha final err�nea.");
        }

        if (lastDate.before(firstDate)) {
            errors.put("Fechas", "Fecha final no puede ser anterior a fecha inicial.");
//                this.setRunaction("list");
                this.setTitleKey("NewsSearch");
                this.setHelpKey("NewsSearchHelp");
                request.setAttribute("nscFormBean", this);

        }

        return errors;
    }

    public void setRequest(Map map) {
        this.requestáttributes = map;
    }
}
