package jsonClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.codemodel.JArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Billinglines {
@JsonProperty
    private String orderId;

    @JsonProperty
    private String plannerId;

    @JsonProperty
    private String productId;

    @JsonProperty
    private String startMonth;

    @JsonProperty
    private int durationInMonths;

    @JsonProperty("billingLineBuyingAreaRevenues")
    private List<String> billingLineBuyingAreaRevenues;

    @JsonProperty("monthBuyingAreaRevenues")
    private List<String> monthBuyingAreaRevenues;
    @JsonProperty
    private String date;
    @JsonProperty
    private double value;

    @JsonProperty
    private int revenue;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlannerId() {
        return plannerId;
    }

    public void setPlannerId(String plannerId) {
        this.plannerId = plannerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public List<String> getBillingLineBuyingAreaRevenues() {
        return billingLineBuyingAreaRevenues;
    }

    public void setBillingLineBuyingAreaRevenues(List<String> billingLineBuyingAreaRevenues) {
        this.billingLineBuyingAreaRevenues = billingLineBuyingAreaRevenues;
    }

    public List<String> getMonthBuyingAreaRevenues() {
        return monthBuyingAreaRevenues;
    }

    public void setMonthBuyingAreaRevenues(List<String> monthBuyingAreaRevenues) {
        this.monthBuyingAreaRevenues = monthBuyingAreaRevenues;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }



    @JsonIgnore

     public Billinglines() {
        this.orderId = "b1375915-6c3d-4df5-aac2-aaf400e3ebab";
        this.plannerId = "90C44CAE-2A4A-4B5D-A5EC-AA9500A6C839";
        this.productId = "3B50FC9A-26B1-42C7-A7B4-AACB0084B9E4";
        this.startMonth = "2019-11-30";
        this.durationInMonths = 2;
        BuyingAreaRevenues rev= new BuyingAreaRevenues();

        this.billingLineBuyingAreaRevenues = Arrays.asList(new String[]{"8798f1f5-cd1b-455e-b44a-aacb00845536"});
                //this.Arrays.asList(new String[]{"date","2019-11-30"},new String[]{"value","444"}));
        //this.monthBuyingAreaRevenues = Arrays.asList(new String[]{"date","2019-11-30","value","444"}));
        //this.monthBuyingAreaRevenues = Arrays.asList(new String[]{"value","444"});
        this.revenue=444;

        }
        public class BuyingAreaRevenues
        {
            @JsonProperty("billingLineBuyingAreaRevenueIds")
            private List<String> billingLineBuyingAreaRevenueIds;

            public List<String> getBillingLineBuyingAreaRevenueIds() {
                return billingLineBuyingAreaRevenueIds;
            }

            public void setBillingLineBuyingAreaRevenueIds(List<String> billingLineBuyingAreaRevenueIds) {
                this.billingLineBuyingAreaRevenueIds = billingLineBuyingAreaRevenueIds;
            }
        }
       /* public  BuyingAreaRevenues()
        {
            this.billingLineBuyingAreaRevenueIds = Arrays.asList(new String[]{"8798f1f5-cd1b-455e-b44a-aacb00845536"});

        }*/

    /*{
        "orderId": "string",
            "plannerId": "string",
            "productId": "string",
            "startMonth": "2019-11-19T12:34:12.944Z",
            "durationInMonths": 0,
            "billingLineBuyingAreaRevenues": [
        {
            "id": "string",
                "monthBuyingAreaRevenues": [
            {
                "date": "2019-11-19T12:34:12.944Z",
                    "value": 0
            }
      ]
        }
  ],
        "revenue": 0
    }*/




}
