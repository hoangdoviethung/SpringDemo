package com.hung.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hung.dto.request.graphql.OutputEntryByBatchResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SealImportantDTO extends SeatellerBaseDTO {
        private String transactionT24; // số bút toán T24
        private Long total; // tổng số lượng
        private String receivingBranch; // chi nhánh nhận
        private String receivingWarehouse; // kho nhận
        private String outWarehouse; // kho xuất
        private String statusInau; // trạng thái T24 trả về
        private String type; // loại hình
        private String exportTransactionId; // số bút toán xuất
        private String msgError; // lỗi bên T24 trả về
        private String receivingAccountingEntry; // bút toán PR của kho nhận
        private String outAccountingEntry; // bút toán PR của kho xuất
        private String versionOfs; // version để buildOFS (FE sẽ chuyền vào)
        private String bookNumber; // số sổ (riêng cho trường hợp hủy/ mất)
        private String extra;
        private List<SealImportantDetailDTO> sealImportantDetailDTOList;
        private List<OutputEntryByBatchResDTO> outputEntryByBatchList;

        // trường businessType gồm: Teller_Nhap, PGD_Nhap, Xuat-PGD,Xuat_Teller, Teller_Xuat, Xuat_Lo, Huy_Mat
}
