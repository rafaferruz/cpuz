/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.ztaghandlers;

import java.util.Map;
import java.util.SortedMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ShowCCTC implements Tag {

    private PageContext pageContext;
    private Tag parent;
    private Object composition;
    private SortedMap itemComposition;
    private Result component;
    private Integer index;

    public ShowCCTC() {
        super();
    }

    public void setPageContext(PageContext pc) {
        this.pageContext = pc;
    }

    public void setParent(Tag t) {
        this.parent = t;
    }

    public Tag getParent() {
        return parent;
    }

    public int doStartTag() throws JspException {
        return Tag.SKIP_BODY;
    }

    public int doEndTag() throws JspException {

        if (((SortedMap[])component.getRows()).length>0){
        itemComposition=((Result) composition).getRows()[index];
        itemComposition.put("nco_body_abstract",((SortedMap[])component.getRows())[0].get("inb_body"));
        }

        return Tag.EVAL_PAGE;

    }

    public void release() {
        return;
    }


    public Object getComponent() {
        return component;
    }

    public void setComponent(Object component) {
        this.component = (Result) component;
    }

    public Object getComposition() {
        return composition;
    }

    public void setComposition(Object composition) {
        this.composition = composition;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


}
