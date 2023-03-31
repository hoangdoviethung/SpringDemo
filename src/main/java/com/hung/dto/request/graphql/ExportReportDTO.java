package com.hung.dto.request.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExportReportDTO {

    private String typeQuery;
    private String codeWarehouse;
    private String codeOwner;
    private String bookNumber;
	private String type;
    private String coCode;
    private String coName;
    private String createDate;
    private String department;
    private String storeCode;
    private String dateReport;
    private String inputter;
    private String authoriser;
    private List<FlowUseACQT> lstFlowUseACQT;
    private List<DailyInventory> lstDailyInventory;
    private List<Whitelist> lstWhitelist;
    private List<ByReferenceByWarehouse> lstByReferenceByWarehouse;
    private List<WarehouseByUser> lstWarehouseByUser;
    
    @Getter
    @Setter
    public static class FlowUseACQT {
    	private String storeCode;
        private String storeName;
        private String typeGTGT;
        private String importBilletFromSerial;
        private String importBilletToSerial;
        private String importBilletQuantity;
        private String exportBilletFromSerial;
        private String exportBilletToSerial;
        private String exportBilletQuantity;
        private String createBilletFromSerial;
        private String createBilletToSerial;
        private String createBilletQuantity;
        private String cancelBilletFromSerial;
        private String cancelBilletToSerial;
        private String cancelBilletQuantity;
        private String lostBilletFromSerial;
        private String lostBilletToSerial;
        private String lostBilletQuantity;
    }

    @Getter
    @Setter
    public static class DailyInventory {
        private String storeCode;
        private String storeName;
        private String typeGTGT;
        private String status;
        private String billetFromSerial;
        private String billetToSerial;
        private String billetQuantity;
    }

    @Getter
    @Setter
    public static class Whitelist {
        private String storeCode;
        private String typeGTGT;
        private String billetFromSerial;
        private String billetToSerial;
    }

    @Getter
    @Setter
    public static class ByReferenceByWarehouse {
        private String numberGTGT;
        private String store;
        private String typeGTGT;
        private String status;
        private String owner;
        private String numberEntries;
        private String company;
        private String receiveDate;
        private String amount;
    }
    @Getter
    @Setter
    public static class WarehouseByUser {
        private String storeCode;
        private String storeName;
        private String storeType;
        private String status;
        private String owner;
        private String typeGTGT;
    }
}
