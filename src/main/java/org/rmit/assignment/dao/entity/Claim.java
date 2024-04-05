package org.rmit.assignment.dao.entity;


import java.sql.Date;

public class Claim {
    private String id;

    private String customerId;

    private Customer customer;

    private String customerName;

    private String customerType;

    private Date examDate;

    private String listDocuments;

    private Double claimAmount;

    private String status;

    private BankingInfo bankingInfo;

    private String bankingInfoId;

    private String bankingName;

    private String bankingAccountNumber;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getListDocuments() {
        return listDocuments;
    }

    public String getBankingName() {
        return bankingName;
    }

    public void setBankingName(String bankingName) {
        this.bankingName = bankingName;
    }

    public String getBankingAccountNumber() {
        return bankingAccountNumber;
    }

    public void setBankingAccountNumber(String bankingAccountNumber) {
        this.bankingAccountNumber = bankingAccountNumber;
    }



    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", examDate=" + examDate +
                ", listDocuments=" + listDocuments +
                ", claimAmount=" + claimAmount +
                ", status='" + status + '\'' +
                ", bankingInfoId='" + bankingInfoId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }



    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankingInfo getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(BankingInfo bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public String getBankingInfoId() {
        return bankingInfoId;
    }

    public void setBankingInfoId(String bankingInfoId) {
        this.bankingInfoId = bankingInfoId;
    }

    public void setListDocuments(String listDocuments) {
        this.listDocuments = listDocuments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
