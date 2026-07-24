package com.fintech.omnipe.Api.Object;

import java.util.ArrayList;

public class PackageDetails {
    int packageId;
    String packageName;
    double packageCost;
    double commission;
    boolean isDefault;
    boolean isActive;
    ArrayList<PackageService> services;

    public int getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getPackageCost() {
        return packageCost;
    }

    public double getCommission() {
        return commission;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isActive() {
        return isActive;
    }

    public ArrayList<PackageService> getServices() {
        return services;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
