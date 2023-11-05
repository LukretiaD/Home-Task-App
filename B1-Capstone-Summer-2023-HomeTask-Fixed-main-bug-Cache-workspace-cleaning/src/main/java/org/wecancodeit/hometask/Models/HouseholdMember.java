package org.wecancodeit.hometask.Models;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class HouseholdMember {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private double cashAmount;
    private double amountPaid;
    private double amountRemaining;
    private double amountUsed;
    private String imageUrl;


    @ManyToOne
    private Household household;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Task> tasks;

    public HouseholdMember() {
    }

    public HouseholdMember(String name, Household household, double cashAmount, double amountPaid,
            double amountRemaining, String imageUrl) {
        this.name = name;
        this.household = household;
        this.cashAmount = cashAmount;
        this.amountPaid = amountPaid;
        this.amountRemaining = amountRemaining;
        this.imageUrl = imageUrl;
    }


    
    public double getAmountUsed() {
        return amountUsed;
    }

    public void setAmountUsed(double amountUsed) {
        this.amountUsed = amountUsed;
    }

    public double getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(double amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHousehold(Household household) {
        this.household = household;
    }

    public Household getHousehold() {
        return household;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    // Function for remaining amount
    public double calculateRemainingCash() {
        amountRemaining = cashAmount - amountUsed;
        return amountRemaining;
    }

    public void moneyUsed(double amount) {
        amountUsed += amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HouseholdMember other = (HouseholdMember) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "HouseholdMember [name=" + name + "]";
    }



}
