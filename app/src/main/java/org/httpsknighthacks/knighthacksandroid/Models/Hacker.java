package org.httpsknighthacks.knighthacksandroid.Models;

public class Hacker {
    private Boolean accepted;
    private String email;
    private String name;
    private String privateUuid;
    private String pronouns;
    private Boolean rsvp;
    private Boolean signedIn;


    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivateUuid() {
        return privateUuid;
    }

    public void setPrivateUuid(String privateUuid) {
        this.privateUuid = privateUuid;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public Boolean getRsvp() {
        return rsvp;
    }

    public void setRsvp(Boolean rsvp) {
        this.rsvp = rsvp;
    }

    public Boolean getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(Boolean signedIn) {
        this.signedIn = signedIn;
    }
}
