package org.httpsknighthacks.knighthacksandroid.Models;

public class AdministrativeFields {
    private String foodGroup;
    private int pointsCount;
    private String pointsGroup;
    private String publicUuid;

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointCount) {
        this.pointsCount = pointCount;
    }

    public String getPointsGroup() {
        return pointsGroup;
    }

    public void setPointsGroup(String pointsGroup) {
        this.pointsGroup = pointsGroup;
    }

    public String getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(String publicUuid) {
        this.publicUuid = publicUuid;
    }

}
