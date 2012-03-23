package com.cpuz.domain;

import java.util.List;

public class NewsPieceStatusTypeFactory {

	public NewsPieceStatusType getAuthorized() {
		return NewsPieceStatusType.AUTHORIZED;
	}

	public NewsPieceStatusType getDisabled() {
		return NewsPieceStatusType.DISABLED;
	}
	public NewsPieceStatusType getRevised() {
		return NewsPieceStatusType.REVISED;
	}

	public List<NewsPieceStatusType> getTypes() {
		return NewsPieceStatusType.list();
	}
}