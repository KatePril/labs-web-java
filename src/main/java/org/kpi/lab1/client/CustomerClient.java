package org.kpi.lab1.client;

import org.kpi.lab1.dto.customer.CustomerInfoDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CustomerClient {
    private final RestClient restClient;

    public CustomerClient(RestClient supplierRestClient) {
        this.restClient = supplierRestClient;
    }

    public CustomerInfoDto getCustomerInfo(String sku) {
        try {
            return restClient.get()
                    .uri("/client/info/{sku}", sku)
                    .retrieve()
                    .body(CustomerInfoDto.class);
        } catch (Exception e) {

            System.err.println("Error fetching client info: " + e.getMessage());
            return null;
        }
    }
}
