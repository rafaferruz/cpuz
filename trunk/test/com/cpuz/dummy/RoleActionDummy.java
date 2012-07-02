package com.cpuz.dummy;

import com.cpuz.actions.admin.RoleAction;

/**
 *
 * Extiende RoleAction para sobreescribir métodos de Struts
 */
public class RoleActionDummy extends RoleAction{
	
	public String getText(String str){
		return str;
	}
}
