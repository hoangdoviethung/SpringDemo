package com.hung.dto.request;


import com.hung.enums.BusinessTypeEnum;
import com.hung.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SeatellerBaseDTO {
    @NotEmpty
    private String transactionId;
    private String customerId;
    @NotNull
    private BusinessTypeEnum businessType;
    @NotEmpty
    private String priority;
    private StatusEnum status;
    @NotEmpty
    private String inputter;
    private String authoriser;
    @NotNull
    private String timeSlaInputter;
    private String timeSlaAuthoriser;
}
