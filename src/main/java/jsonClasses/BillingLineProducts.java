package jsonClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class BillingLineProducts {
    @JsonProperty
    private String name;
    @JsonProperty
    private Boolean hasCommission;
    private List<String> buyingAreaIds;
    private Boolean isDisabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasCommission() {
        return hasCommission;
    }

    public void setHasCommission(Boolean hasCommission) {
        this.hasCommission = hasCommission;
    }

    public List<String> getBuyingAreaIds() {
        return buyingAreaIds;
    }

    public void setBuyingAreaIds(List<String> buyingAreaIds) {
        this.buyingAreaIds = buyingAreaIds;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        isDisabled = isDisabled;
    }

    public BillingLineProducts(){

        this.name="testproducts";
        this.hasCommission=true;
        this.buyingAreaIds=Arrays.asList(new String[]{"8798f1f5-cd1b-455e-b44a-aacb00845536"});
        this.isDisabled=false;

    }
}



/*{

        "name": "testproduct",
        "hasCommission": true,
        "buyingAreaIds": [
        "8798f1f5-cd1b-455e-b44a-aacb00845536"
        ],
        "isDisabled": false
        }*/