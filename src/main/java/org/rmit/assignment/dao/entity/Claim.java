package org.rmit.assignment.dao.entity;


import java.sql.Date;
import java.util.List;

public class Claim {
    private String id;

    private String customerId;

    private Date examDate;

    private String listDocuments;

    private Double claimAmount;

    private String status;

    private BankingInfo bankingInfo;

    private String bankingInfoId;

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
}
