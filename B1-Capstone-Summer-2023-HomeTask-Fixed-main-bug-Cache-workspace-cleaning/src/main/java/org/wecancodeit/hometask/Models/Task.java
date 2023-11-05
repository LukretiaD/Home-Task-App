package org.wecancodeit.hometask.Models;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String TaskName;
    private String TaskDescription;
    private long LinkedTaskID;// keep root task ID if its a sub task

    @ManyToOne
    private HouseholdMember member;

    @ManyToOne
    private Reward reward;

    @ManyToOne
    private Household household;
    
    private boolean IsOneTime; // identfy task is repeating or not - lets keep it null until
    private boolean RootTask; // this will true task is main task else false
    private Double TimeDuration; // if task is for only one day and it has hour value
    private LocalDate StartDate; // if TimeDuration has a value start and end will same date
    private LocalDate EndDate;
    private boolean isDurationActive; // if task have betweendays
    private boolean isCompleted; // true after completed
    private boolean isPending;
    private long RewardedValue; // keep task against rewarded amount lets keep null if not needed
    private LocalDate CreatedDate; // always getdate function will run when inserting a record


    
    public Task() {
    }

    public Task(String taskName, String taskDescription, Reward reward, long linkedTaskID, HouseholdMember member, Household household, boolean isOneTime, boolean rootTask, Double timeDuration, LocalDate startDate,
            LocalDate endDate, boolean isDurationActive, boolean isCompleted, boolean isPending, long rewardedValue,
            LocalDate createdDate) {
        this.TaskName = taskName;
        this.TaskDescription = taskDescription;
        this.LinkedTaskID = linkedTaskID;
        this.member = member;
        this.household = household;
        this.IsOneTime = isOneTime;
        this.RootTask = rootTask;
        this.TimeDuration = timeDuration;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.isDurationActive = isDurationActive;
        this.isCompleted = isCompleted;
        this.RewardedValue = rewardedValue;
        this.CreatedDate = createdDate;
        this.reward = reward;
        this.isPending = isPending;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean isPending) {
        this.isPending = isPending;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public long getLinkedTaskID() {
        return LinkedTaskID;
    }

    public void setLinkedTaskID(long linkedTaskID) {
        LinkedTaskID = linkedTaskID;
    }

    public HouseholdMember getMember() {
        return member;
    }

    public void setMember(HouseholdMember member) {
        this.member = member;
    }


    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public boolean isIsOneTime() {
        return IsOneTime;
    }

    public void setIsOneTime(boolean isOneTime) {
        IsOneTime = isOneTime;
    }

    public boolean isRootTask() {
        return RootTask;
    }

    public void setRootTask(boolean rootTask) {
        RootTask = rootTask;
    }

    public Double getTimeDuration() {
        return TimeDuration;
    }

    public void setTimeDuration(Double timeDuration) {
        TimeDuration = timeDuration;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public boolean isDurationActive() {
        return isDurationActive;
    }

    public void setDurationActive(boolean isDurationActive) {
        this.isDurationActive = isDurationActive;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public long getRewardedValue() {
        return RewardedValue;
    }

    public void setRewardedValue(long rewardedValue) {
        RewardedValue = rewardedValue;
    }

    public LocalDate getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        CreatedDate = createdDate;
    }

    public Reward getReward() {
        return reward;
    }

 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((TaskName == null) ? 0 : TaskName.hashCode());
        result = prime * result + ((TaskDescription == null) ? 0 : TaskDescription.hashCode());
        result = prime * result + (int) (LinkedTaskID ^ (LinkedTaskID >>> 32));
        result = prime * result + ((member == null) ? 0 : member.hashCode());
        result = prime * result + ((reward == null) ? 0 : reward.hashCode());
        result = prime * result + ((household == null) ? 0 : household.hashCode());
        result = prime * result + (IsOneTime ? 1231 : 1237);
        result = prime * result + (RootTask ? 1231 : 1237);
        result = prime * result + ((TimeDuration == null) ? 0 : TimeDuration.hashCode());
        result = prime * result + ((StartDate == null) ? 0 : StartDate.hashCode());
        result = prime * result + ((EndDate == null) ? 0 : EndDate.hashCode());
        result = prime * result + (isDurationActive ? 1231 : 1237);
        result = prime * result + (isCompleted ? 1231 : 1237);
        result = prime * result + (int) (RewardedValue ^ (RewardedValue >>> 32));
        result = prime * result + ((CreatedDate == null) ? 0 : CreatedDate.hashCode());
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
        Task other = (Task) obj;
        if (TaskName == null) {
            if (other.TaskName != null)
                return false;
        } else if (!TaskName.equals(other.TaskName))
            return false;
        if (TaskDescription == null) {
            if (other.TaskDescription != null)
                return false;
        } else if (!TaskDescription.equals(other.TaskDescription))
            return false;
        if (LinkedTaskID != other.LinkedTaskID)
            return false;
        if (member == null) {
            if (other.member != null)
                return false;
        } else if (!member.equals(other.member))
            return false;
        if (reward == null) {
            if (other.reward != null)
                return false;
        } else if (!reward.equals(other.reward))
            return false;
        if (household == null) {
            if (other.household != null)
                return false;
        } else if (!household.equals(other.household))
            return false;
        if (IsOneTime != other.IsOneTime)
            return false;
        if (RootTask != other.RootTask)
            return false;
        if (TimeDuration == null) {
            if (other.TimeDuration != null)
                return false;
        } else if (!TimeDuration.equals(other.TimeDuration))
            return false;
        if (StartDate == null) {
            if (other.StartDate != null)
                return false;
        } else if (!StartDate.equals(other.StartDate))
            return false;
        if (EndDate == null) {
            if (other.EndDate != null)
                return false;
        } else if (!EndDate.equals(other.EndDate))
            return false;
        if (isDurationActive != other.isDurationActive)
            return false;
        if (isCompleted != other.isCompleted)
            return false;
        if (RewardedValue != other.RewardedValue)
            return false;
        if (CreatedDate == null) {
            if (other.CreatedDate != null)
                return false;
        } else if (!CreatedDate.equals(other.CreatedDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Task [TaskName=" + TaskName + ", TaskDescription=" + TaskDescription + ", LinkedTaskID=" + LinkedTaskID
                + ", member=" + member + ", household=" + household + ", IsOneTime=" + IsOneTime + ", RootTask="
                + RootTask + ", TimeDuration=" + TimeDuration + ", StartDate=" + StartDate + ", EndDate=" + EndDate
                + ", isDurationActive=" + isDurationActive + ", isCompleted=" + isCompleted + ", RewardedValue="
                + RewardedValue + ", CreatedDate=" + CreatedDate + "]";
    }

 
  
}

