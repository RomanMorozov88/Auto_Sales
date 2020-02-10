package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Объявление.
 */
public class Advertisement {

    @JsonProperty("adId")
    private int adId;
    @JsonProperty("adShortName")
    private String adShortName;
    @JsonProperty("adDescription")
    private String adDescription;
    @JsonProperty("adPhoto")
    private String adPhoto;
    @JsonProperty("adCar")
    private Car adCar;
    @JsonProperty("adCreator")
    private Owner adCreator;
    @JsonProperty("adStatus")
    private boolean adStatus;
    @JsonProperty("adTime")
    private LocalDateTime adTime;

    public Advertisement() {
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getAdShortName() {
        return adShortName;
    }

    public void setAdShortName(String adShortName) {
        this.adShortName = adShortName;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public String getAdPhoto() {
        return adPhoto;
    }

    public void setAdPhoto(String adPhoto) {
        this.adPhoto = adPhoto;
    }

    public Car getAdCar() {
        return adCar;
    }

    public void setAdCar(Car adCar) {
        this.adCar = adCar;
    }

    public Owner getAdCreator() {
        return adCreator;
    }

    public void setAdCreator(Owner adCreator) {
        this.adCreator = adCreator;
    }

    public boolean isAdStatus() {
        return adStatus;
    }

    public void setAdStatus(boolean adStatus) {
        this.adStatus = adStatus;
    }

    public LocalDateTime getAdTime() {
        return adTime;
    }

    public void setAdTime(LocalDateTime adTime) {
        this.adTime = adTime;
    }
}