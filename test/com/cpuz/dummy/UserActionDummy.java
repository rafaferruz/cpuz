package com.cpuz.dummy;

import com.cpuz.actions.admin.UserAction;

/**
 *
 * Extiende UserAction para sobreescribir métodos de Struts
 */
public class UserActionDummy extends UserAction{
	
	public String getText(String str){
		return str;
	}
}
