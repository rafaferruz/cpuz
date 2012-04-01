/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.actions;

import com.cpuz.DAO.query.entities.NewsPieceComposition;
import com.cpuz.DAO.query.models.NewsPieceCompositionModel;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.st2.beans.NewsSelecConditions;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsSearchedAction extends ActionSupport implements RequestAware, Serializable {

    private ControlParams control = new ControlParams();
    private List<NewsPieceComposition> dataList = new ArrayList<NewsPieceComposition>();
    private NewsPieceComposition dataEdit = new NewsPieceComposition();
    private NewsPieceCompositionModel dataModel = new NewsPieceCompositionModel();
    private Map<Integer, String> mapStatus = new HashMap<Integer, String>();
    private Map<String, Object> requestáttributes = new HashMap<String, Object>();
    private NewsSelecConditions dataConditions = new NewsSelecConditions();

    public NewsSearchedAction() {
        super();
    }

    @Override
    public String execute() throws Exception {
        return "error";
    }

    public String NewsSearched_list() throws Exception {
        if (control.getRunAction().equals("list_selec")) {
            dataList = dataModel.getQuerySearchingWordsInJoinedTables(dataConditions.getWords());
        }
/*        dataList = dataModel.getRecords("SELECT * FROM sections "
                + " LIMIT " + control.getRecChunk().toString()
                + " OFFSET " + control.getRecStart().toString(), "", "");
*/
        control.setRecCount(dataList.size());
        control.setRunAction("list");
        requestáttributes.put("page", "/NewsSearchedList.jsp");
        return "LIST";
    }

    public String Section_nav_first() throws Exception {
        return "LIST";
    }

    public String Section_nav_prev() throws Exception {
        return "LIST";
    }

    public String Section_nav_next() throws Exception {
        return "LIST";
    }

    public String Section_nav_last() throws Exception {
        return "LIST";
    }

    @Override
    public void validate() {
        super.validate();
    }

    public ControlParams getControl() {
        return control;
    }

    public void setControl(ControlParams control) {
        this.control = control;
    }

    public Map<Integer, String> getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(HashMap<Integer, String> mapStatus) {
        this.mapStatus = mapStatus;
    }

    public NewsPieceComposition getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(NewsPieceComposition dataEdit) {
        this.dataEdit = dataEdit;
    }

    public List<NewsPieceComposition> getDataList() {
        return dataList;
    }

    public void setDataList(List<NewsPieceComposition> dataList) {
        this.dataList = dataList;
    }

    public void setdataModel(NewsPieceCompositionModel dataModel) {
        this.dataModel = dataModel;
    }

    public void initMapStatus() {
        //Prepara tipos de status para radio element
        mapStatus.put(0, this.getText("received"));
        mapStatus.put(1, this.getText("waiting"));
        mapStatus.put(2, this.getText("authorized"));
    }

    public void setRequest(Map map) {
        this.requestáttributes = map;
    }
}
