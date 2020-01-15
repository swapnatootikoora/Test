package jsonClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class billingLineBuyingAreas {
    @JsonProperty
    private String id;

    @JsonProperty
    private String parentId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String endDate;

    @JsonProperty
    private String availableForBillingLine;

    @JsonProperty
    private Boolean isDisabled;



    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAvailableForBillingLine() {
        return availableForBillingLine;
    }

    public void setAvailableForBillingLine(String availableForBillingLine) {
        this.availableForBillingLine = availableForBillingLine;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }





    @JsonIgnore

    public billingLineBuyingAreas() {
        this.parentId = "b1375915-6c3d-4df5-aac2-aaf400e3ebab";
        this.name = "test";
        this.endDate = "2019-11-16T05:40:29.283Z";
        this.availableForBillingLine = "true";
        this.isDisabled = false;

    }



}


/*
{
  "id": "string",
  "parentId": "string",
  "name": "string",
  "endDate": "2019-12-01T07:15:54.692Z",
  "availableForBillingLine": true,
  "isDisabled": true
}
 */