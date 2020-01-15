package JsonClass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class RBE {
    @JsonProperty
    private List<String> groupName;

    @JsonProperty
    private String agency;

    @JsonProperty
    private String client;

    @JsonProperty
    private String locality;
    @JsonProperty
    private int showInputs;


    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }



    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        agency = agency;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        client = client;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        locality = locality;
    }
    public int getShowInputs() {
        return showInputs;
    }

    public void setShowInputs(int showInputs) {
        showInputs = showInputs;
    }

    @JsonIgnore

    public RBE() {
        this.groupName = Arrays.asList(new String[]{"RBE1"});
        this.showInputs = 1;
        this.agency = null;
        this.client = null;
        this.locality = "Local";

    }


}
