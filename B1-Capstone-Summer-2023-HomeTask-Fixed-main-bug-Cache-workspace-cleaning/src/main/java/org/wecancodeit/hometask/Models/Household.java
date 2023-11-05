package org.wecancodeit.hometask.Models;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Household {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    private double householdBank;
    private String imageUrl;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @OneToOne
    private User user;

    @OneToMany(mappedBy="household")
    private Collection<HouseholdMember> members;

    @OneToMany(mappedBy="household")
    private Collection<Reward> rewards;

    @OneToMany(mappedBy="household")
    private Collection<Task> tasks;


    public Household() {
    }

    public Household(String name, User user, double householdBank, byte[] profilePicture) {
        this.name = name;
        this.user = user;
        this.householdBank = householdBank;
        this.profilePicture = profilePicture;
    }

    

    


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getHouseholdBank() {
        return householdBank;
    }

    public void setHouseholdBank(double householdBank) {
        this.householdBank = householdBank;
    }

    public String getName() {
        return name;
    }


    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Collection<HouseholdMember> getMembers() {
        return members;
    }

    
    public Collection<Reward> getRewards() {
        return rewards;
    }


    public Collection<Task> getTasks() {
        return tasks;
    }

    


    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        Household other = (Household) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Household [name=" + name + ", user=" + user + "]";
    }


    




}
