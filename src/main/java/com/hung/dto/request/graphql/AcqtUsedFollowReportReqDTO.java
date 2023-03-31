package com.hung.dto.request.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcqtUsedFollowReportReqDTO {
    private String store;
    private String date;

}
