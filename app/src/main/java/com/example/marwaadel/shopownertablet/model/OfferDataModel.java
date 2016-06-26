package com.example.marwaadel.shopownertablet.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "DayEndOffer",
        "DayStartOffer",
        "Description",
        "DiscountAfter",
        "DiscountBefore",
        "MonthEndOffer",
        "MonthStartOffer",
        "Title",
        "YearEndOffer",
        "YearStartOffer",
        "offerImage",
        "Amount"
})
public class OfferDataModel implements Serializable {

    public String getDayEndOffer() {
        return DayEndOffer;
    }

    public String getDayStartOffer() {
        return DayStartOffer;
    }

    public String getDescription() {
        return Description;
    }

    public String getDiscountAfter() {
        return DiscountAfter;
    }

    public String getDiscountBefore() {
        return DiscountBefore;
    }

    public String getMonthEndOffer() {
        return MonthEndOffer;
    }

    public String getMonthStartOffer() {
        return MonthStartOffer;
    }

    public String getTitle() {
        return Title;
    }

    public String getYearEndOffer() {
        return YearEndOffer;
    }

    public String getYearStartOffer() {
        return YearStartOffer;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public String getStatus() {
        return status;
    }
    public String getAmount() {
        return Amount;
    }
    @JsonProperty("DayEndOffer")
    public String DayEndOffer;
    @JsonProperty("DayStartOffer")
    public String DayStartOffer;
    @JsonProperty("Description")
    public String Description;
    @JsonProperty("DiscountAfter")
    public String DiscountAfter;
    @JsonProperty("DiscountBefore")
    public String DiscountBefore;
    @JsonProperty("MonthEndOffer")
    public String MonthEndOffer;
    @JsonProperty("MonthStartOffer")
    public String MonthStartOffer;
    @JsonProperty("Title")
    public String Title;
    @JsonProperty("YearEndOffer")
    public String YearEndOffer;
    @JsonProperty("YearStartOffer")
    public String YearStartOffer;
    @JsonProperty("offerImage")
    public String offerImage;
    @JsonProperty("status")
    public String status;
    @JsonProperty("Amount")
    public String Amount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public OfferDataModel() {
    }

    /**
     *
     * @param MonthStartOffer
     * @param Description
     * @param DayEndOffer
     * @param DayStartOffer
     * @param YearEndOffer
     * @param YearStartOffer
     * @param MonthEndOffer
     * @param offerImage
     * @param Title
     * @param DiscountBefore
     * @param DiscountAfter
     * @param Amount
     */
    public OfferDataModel(String DayEndOffer, String DayStartOffer, String Description, String DiscountAfter, String DiscountBefore, String MonthEndOffer, String MonthStartOffer, String Title, String YearEndOffer, String YearStartOffer, String offerImage,String Amount) {
        this.DayEndOffer = DayEndOffer;
        this.DayStartOffer = DayStartOffer;
        this.Description = Description;
        this.DiscountAfter = DiscountAfter;
        this.DiscountBefore = DiscountBefore;
        this.MonthEndOffer = MonthEndOffer;
        this.MonthStartOffer = MonthStartOffer;
        this.Title = Title;
        this.YearEndOffer = YearEndOffer;
        this.YearStartOffer = YearStartOffer;
        this.offerImage = offerImage;
        this.Amount = Amount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}