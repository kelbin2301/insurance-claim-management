package org.rmit.assignment.dao.entity;


import java.util.List;
import java.util.Objects;

public class Customer {

    private String id;

    private String fullName;

    private String customerType;

    private InsuranceCard insuranceCard;

    private List<Customer> dependents;

    private Integer claimCount;

    private Double totalClaimAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public List<Customer> getDependents() {
        return dependents;
    }

    public void setDependents(List<Customer> dependents) {
        this.dependents = dependents;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", customerType='" + customerType + '\'' +
                '}';
    }

    public Double getTotalClaimAmount() {
        return totalClaimAmount;
    }

    public void setTotalClaimAmount(Double totalClaimAmount) {
        this.totalClaimAmount = totalClaimAmount;
    }


    public Integer getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(Integer claimCount) {
        this.claimCount = claimCount;
    }
}
