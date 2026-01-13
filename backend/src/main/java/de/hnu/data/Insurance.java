package de.hnu.data;

import de.hnu.data.enums.Coverage;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    private String id;

    private String name;
    private String policyName;
    private String policyNumber;
    private Boolean passengerDriverInsurance;

    @Enumerated(EnumType.STRING)
    private Coverage coverage;

    public Insurance() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public Boolean getPassengerDriverInsurance() {
        return passengerDriverInsurance;
    }

    public void setPassengerDriverInsurance(Boolean passengerDriverInsurance) {
        this.passengerDriverInsurance = passengerDriverInsurance;
    }
}