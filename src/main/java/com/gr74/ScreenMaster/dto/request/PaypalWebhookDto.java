package com.gr74.ScreenMaster.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PaypalWebhookDto {

    private String id;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("resource_type")
    private String resourceType;

    private String summary;

    private Resource resource;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Resource {
        private String id;
        private String state;
        private Amount amount;

        @JsonProperty("parent_payment")
        private String parentPayment;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Amount {
        private String total;
        private String currency;

        // getters & setters
    }
    @Override
    public String toString() {
        return "PaypalWebhookDto{" +
                "id='" + id + '\'' +
                ", eventType='" + eventType + '\'' +
                ", resource='" + resource + '\'' +
                '}';
    }
}
