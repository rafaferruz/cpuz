package com.cpuz.domain;

public class UserStatusTypeFactory {

	public UserStatusType getWaiting() {
		return UserStatusType.WAITING;
	}

	public UserStatusType getReceived() {
		return UserStatusType.RECEIVED;
	}

	public UserStatusType getAuthorized() {
		return UserStatusType.AUTHORIZED;
	}

}
