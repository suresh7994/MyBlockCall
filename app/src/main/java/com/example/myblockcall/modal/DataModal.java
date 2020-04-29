package com.example.myblockcall.modal;

public class DataModal {
    String dataName;
    String dataNumber;

    public DataModal(String dataName, String dataNumber) {
        this.dataName = dataName;
        this.dataNumber = dataNumber;
    }

    public DataModal() {
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(String dataNumber) {
        this.dataNumber = dataNumber;
    }
}
