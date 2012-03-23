/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.st2.beans;

import com.cpuz.tools.Const;
import java.io.Serializable;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ListControlParams implements Serializable {

    private String runAction = "";
    private Integer id = 0;
    private Integer recStart = 0;
    private Integer recChunk = Const.LIMIT_SQL_RECORDS_SELECTED_MANAGEMENT;
    private Integer recCount = 0;

    public ListControlParams() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecChunk() {
        return recChunk;
    }

    public void setRecChunk(Integer recChunk) {
        this.recChunk = recChunk;
    }

    public Integer getRecCount() {
        return recCount;
    }

    public void setRecCount(Integer recCount) {
        this.recCount = recCount;
    }

    public Integer getRecStart() {
        return recStart;
    }

    public void setRecStart(Integer recStart) {
        this.recStart = recStart;
    }

    public String getRunAction() {
        return runAction;
    }

    public void setRunAction(String runAction) {
        this.runAction = runAction;
    }

    public void doNavigation() throws Exception {
        if (getRunAction().equals("nav_first")) {
            nav_first();
        } else if (getRunAction().equals("nav_prev")) {
            nav_prev();
        } else if (getRunAction().equals("nav_next")) {
            nav_next();
        } else if (getRunAction().equals("nav_last")) {
            nav_last();
        }
    }

    private void nav_first() throws Exception {
        this.setRecStart(0);
    }

    private void nav_prev() throws Exception {
        this.setRecStart(Math.max(0, (this.getRecStart() - this.getRecChunk())));
    }

    private void nav_next() throws Exception {
        this.setRecStart(this.getRecStart() + this.getRecChunk());
    }

    private void nav_last() throws Exception {
        this.setRecStart(Math.max(0, (this.getRecCount() - this.getRecChunk())));
    }
}
