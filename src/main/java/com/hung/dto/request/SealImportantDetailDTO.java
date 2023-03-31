package com.hung.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SealImportantDetailDTO {
    private String typeSeal; // loại ACQT
    private String fromSeal; // từ số
    private String toSeal;   // đến số
    private String quantity; // số lượng
    private String transactionId; // mã giao dịch seateller
    private String status; // trạng thái ACQT
//    private String amount; //Số lượng
//    private String totalAmount; //Tổng số lượng

}
