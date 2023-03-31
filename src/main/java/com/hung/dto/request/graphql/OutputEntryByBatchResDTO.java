package com.hung.dto.request.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputEntryByBatchResDTO {

    @JsonProperty("recId")
    private String recId;

    @JsonProperty("portsId")
    private String portsId;


    @JsonProperty("portsOfs")
    private String portsOfs;

    @JsonProperty("description")
    private String description;

}
