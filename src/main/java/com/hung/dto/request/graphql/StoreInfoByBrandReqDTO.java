package com.hung.dto.request.graphql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoByBrandReqDTO {
    private String company;
    private String linkStore;
    private String owner;
    private String store;
    private String typeQuery;
}
