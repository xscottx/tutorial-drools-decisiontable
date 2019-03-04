package com.vo.drools.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private CustomerLifeStage lifestage;
    private CustomerAssets assets;
    private CustomerLocation location;
    private List<CustomerNeed> customerNeeds = new ArrayList<>();

    public Customer() {
        super();
    }

    public void setLifeStage(CustomerLifeStage lifestage) { this.lifestage = lifestage; }

    public CustomerLifeStage getLifeStage() { return lifestage; }

    public void addNeed(CustomerNeed need) { customerNeeds.add(need); }

    public List<CustomerNeed> getNeeds() {
        return customerNeeds;
    }

    public void setAssets(CustomerAssets assets) {
        this.assets = assets;
    }

    public CustomerAssets getAssets() {
        return assets;
    }

    public void setLocation(CustomerLocation location) {
        this.location = location;
    }

    public CustomerLocation getLocation() {
        return location;
    }


    public enum CustomerNeed {
        SAVINGACCOUNT,
        LIFEINSURANCE,
        DIGITALBANKING,
        STUDENTLOAN,
        MORTAGE
    }

    public enum CustomerLifeStage {
        GETTINGSTARTED,
        CAREERFOCUSED,
        ADVICEFAMILY,
        EMPTYNESTER,
        GOLDENYEARS,
        BUSINESS
    }

    public enum CustomerAssets {
        TO50K,
        FROM50KTO150K,
        FROM150KTO300K,
        OVER300K
    }

    public enum CustomerLocation {
        PLANO,
        LEWISVILLE
    }
}
