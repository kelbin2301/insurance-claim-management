package org.rmit.assignment.dao.entity;

public class BankingInfo {
    private String id;

    private String bankName;

    private String accountNumber;

    @Override
    public String toString() {
        return "BankingInfo{" +
                "id='" + id + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    private String accountName;
}
