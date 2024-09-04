package com.volkmatrix.common.enums;

public enum TimeZone {
	GMT("GMT"), IST("Asia/Kolkata");

    private final String value;

    private TimeZone(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;

    }
}