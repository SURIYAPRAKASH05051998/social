package com.petal.social.util.model;

public class DaoParameter {
	private Object value;
	private int type;

	public DaoParameter() {
		super();
	}

	public DaoParameter(Object value, int type) {
		super();
		this.value = value;
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

