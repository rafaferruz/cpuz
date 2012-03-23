package com.cpuz.domain;

import java.util.List;

public class NewsPieceScopeTypeFactory {

	public NewsPieceScopeType getConfidencial() {
		return NewsPieceScopeType.CONFIDENCIAL;
	}

	public NewsPieceScopeType getGlobal() {
		return NewsPieceScopeType.GLOBAL;
	}
	public NewsPieceScopeType getVecinal() {
		return NewsPieceScopeType.VECINAL;
	}

	public List<NewsPieceScopeType> getTypes() {
		return NewsPieceScopeType.list();
	}
}