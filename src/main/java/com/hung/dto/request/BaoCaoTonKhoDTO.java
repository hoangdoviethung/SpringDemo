package com.hung.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaoCaoTonKhoDTO {
    private String coName;
    private String coCode;
    private String ngayTao;
    private String khoBaoCao;
    private List<GTCG> gtcgs;
    private String nguoiLapBieu;
    private String nguoiPheDuyet;

    @Getter
    @Setter
    public static class GTCG {
        private String maKho;
        private String tenKho;
        private String loaiGTCG;
        private String trangThai;
        private String tu;
        private String den;
        private String soLuong;
    }
}