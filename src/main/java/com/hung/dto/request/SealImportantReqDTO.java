package com.hung.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SealImportantReqDTO extends SeatellerBaseDTO {
    private List<SealImportantDTO> lists;
}
