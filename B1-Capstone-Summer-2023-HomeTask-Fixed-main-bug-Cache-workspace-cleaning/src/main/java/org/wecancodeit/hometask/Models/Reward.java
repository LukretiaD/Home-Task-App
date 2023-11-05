package org.wecancodeit.hometask.Models;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/*
Basic Set up for the model for rewards
 */

@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double cashAmount;
    private boolean other;
    private String otherReward;

    @OneToMany(mappedBy = "reward")
    private Collection<Task> tasks;

    @ManyToOne
    private Household household;

    public Reward() {
    }

    public Reward(String name, double cashAmount, boolean other, String otherReward, Household household) {
        this.name = name;
        this.cashAmount = cashAmount;
        this.other = other;
        this.otherReward = otherReward;
        this.household = household;
    }

    public Household getHousehold() {
        return household;
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
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getOtherReward() {
        return otherReward;
    }

    public void setOtherReward(String otherReward) {
        this.otherReward = otherReward;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(cashAmount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (other ? 1231 : 1237);
        result = prime * result + ((otherReward == null) ? 0 : otherReward.hashCode());
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
        Reward other = (Reward) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(cashAmount) != Double.doubleToLongBits(other.cashAmount))
            return false;
        if (this.other != other.other)
            return false;
        if (otherReward == null) {
            if (other.otherReward != null)
                return false;
        } else if (!otherReward.equals(other.otherReward))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reward [cashAmount=" + cashAmount + ", other=" + other + ", otherReward=" + otherReward + "]";
    }

}
