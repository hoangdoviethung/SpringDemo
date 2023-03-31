package com.hung.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhieuXuatKhoDTO {
    private String coName;
    private String coCode;
    private String ngay;
    private String maButToan;
    private String maKhoChuyen;
    private String maKhoNhan;
    private List<ListGTCG> list;
    private String nguoiXuatKho;
    private String kiemSoat;
    private String nguoiNhan;
    private String maGd;
}
