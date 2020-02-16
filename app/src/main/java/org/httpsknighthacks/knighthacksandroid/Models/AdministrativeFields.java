package org.httpsknighthacks.knighthacksandroid.Models;

public class AdministrativeFields {
    private boolean accepted;
    private String authCode;
    private String foodGroup;
    private boolean loggedIn;
    private int pointCount;
    private String pointsGroup;
    private String privateUuid;
    private String publicUuid;
    private String registrationApplication;
    private boolean rsvp;
    private boolean signedIn;
    private boolean travelReimbursementApproval;

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }

    public String getPointsGroup() {
        return pointsGroup;
    }

    public void setPointsGroup(String pointsGroup) {
        this.pointsGroup = pointsGroup;
    }

    public String getPrivateUuid() {
        return privateUuid;
    }

    public void setPrivateUuid(String privateUuid) {
        this.privateUuid = privateUuid;
    }

    public String getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(String publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getRegistrationApplication() {
        return registrationApplication;
    }

    public void setRegistrationApplication(String registrationApplication) {
        this.registrationApplication = registrationApplication;
    }

    public boolean isRsvp() {
        return rsvp;
    }

    public void setRsvp(boolean rsvp) {
        this.rsvp = rsvp;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    public boolean isTravelReimbursementApproval() {
        return travelReimbursementApproval;
    }

    public void setTravelReimbursementApproval(boolean travelReimbursementApproval) {
        this.travelReimbursementApproval = travelReimbursementApproval;
    }
}
