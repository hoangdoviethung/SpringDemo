package com.hung.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhieuXuatKhoList {
    private String coName;
    private String coCode;
    private String ngay;
    private List<PhieuXuatKhoDTO> phieuXuatKho;
}
