package com.hung.controller;

import com.hung.dto.DailyLimitClosingReportReq;
import com.hung.dto.StudentDTO;
import com.hung.dto.request.BaoCaoTonKhoDTO;
import com.hung.dto.request.PhieuXuatKhoDTO;
import com.hung.service.SealSevice;
import com.hung.service.StudentService;
import com.hung.utils.ExcelHelper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {
    @Autowired
    SealSevice sealSevice;

    private final StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<String> send(@RequestBody StudentDTO studentDTO) {
        String result = studentService.save(studentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public String send1() {
        return "cc";
    }

    @PostMapping("/export-phieu-xuat-kho")
    public ResponseEntity<byte[]> exportBillDelivery(@RequestBody List<PhieuXuatKhoDTO> reqDTO) throws JRException {
        byte[] byteArr = sealSevice.exportBillDelivery(reqDTO);
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename("Reverse").build();
        headers.setContentDisposition(contentDisposition);
        MediaType mediaType = MediaType.parseMediaType("application/pdf;charset=utf-8");
        headers.setContentType(mediaType);
        return ResponseEntity.ok().headers(headers).body(byteArr);
    }
    /*
    * [
    {
        "coName": "CN SO GIAO DICH",
        "coCode": "VN0010002",
        "ngay": "20-03-2023",
        "maButToan": "SEATELLER20_ACQTO_20032023_1681982376",
        "maKhoChuyen": "HNA.002",
        "maKhoNhan": "H29TELLER.001",
        "nguoiXuatKho": "SEATELLER20",
        "kiemSoat": "",
        "nguoiNhan": "",
        "list": [
            {
                "subList": [
                    {
                        "loaiGTCG": "STK",
                        "start": "AC0013449",
                        "end": "AC0013453",
                        "trangThai": null
                    },
                    {
                        "loaiGTCG": "STK",
                        "start": "AC0013454",
                        "end": "AC0013457",
                        "trangThai": null
                    },
                    {
                        "loaiGTCG": "SO LUONG STK",
                        "start": "",
                        "end": "13",
                        "trangThai": null
                    }
                ]
            },
            {
                "subList": [
                    {
                        "loaiGTCG": "BMB",
                        "start": "AC0013449",
                        "end": "AC0013453",
                        "trangThai": null
                    },
                    {
                        "loaiGTCG": "BMB",
                        "start": "AC0013454",
                        "end": "AC0013457",
                        "trangThai": null
                    },
                    {
                        "loaiGTCG": "SO LUONG BMB",
                        "start": "",
                        "end": "3",
                        "trangThai": null
                    }
                ]
            }
        ]
    }
]
*
    * */

    @PostMapping("/export-license")
    public ResponseEntity<byte[]> export(@RequestBody BaoCaoTonKhoDTO reqDTO) throws JRException {
        byte[] byteArr = sealSevice.inventoryNote(reqDTO);
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename("Reverse").build();
        headers.setContentDisposition(contentDisposition);
        MediaType mediaType = MediaType.parseMediaType("application/pdf;charset=utf-8");
        headers.setContentType(mediaType);
        return ResponseEntity.ok().headers(headers).body(byteArr);
    }


    @PostMapping("/export-daily-limit-closing-report-excel") //báo cáo đóng hạn mức theo ngày, bc thẻ đóng theo ngày, hạn mức cần đóng
    public ResponseEntity<byte[]> exportDailyLimitClosingReportExcel(@RequestBody DailyLimitClosingReportReq reqDTO) throws JRException, IOException {
        ByteArrayOutputStream outputStream = sealSevice.exportDailyLimitClosingReportExcel(reqDTO);
        String fileNameExcel = reqDTO.getType();
        byte[] byteArr = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(fileNameExcel +".xlsx").build();
        headers.setContentDisposition(contentDisposition);
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        headers.setContentType(mediaType);
        return ResponseEntity.ok().headers(headers).body(byteArr);
    }
/*
* {
    "type": "HAN_MUC_CAN_DONG",
    "fromDate": "01/01/2022",
    "toDate": "01/01/2023",
    "currentTime": "01/01/2022 16:55:22",
    "baocaos": [
        {
            "stt": "test",
            "maKH": "test",
            "tenKH": "test",
            "tenTrenThe": "test",
            "soTaiKhoan": "test",
            "loaiThe": "test",
            "soThe": "test",
            "ngayDongTheT24": "test",
            "ngayDongTheW4": "test",
            "chiNhanhMoThe": "test",
            "nvDongThe": "test",
            "tenNVDongThe": "test",
            "maCNDongThe": "test",
            "tenCNDongThe": "test",
            "maHanMuc": "test",
            "giaTriHanMuc": "test",
            "tinhTrangDong": "test",
            "ngayDongHanMuc": "test",
            "cnDongHanMuc": "test",
            "nvDongHanMuc": "test",
            "nvDuyetDongHanMuc": "test",
            "ghiChuTrenHanMuc": "test",
            "ghiChu": "test",
            "ngayDuKienDongHanMuc": "test",
            "tinhTrangTaiKhoan": "tinhTrangTaiKhoan",
            "theChinhPhu": "theChinhPhu",
            "contrStatus": "contrStatus BC_THE_DONG_THEO_NGAY"
        },
        {
            "stt": "test",
            "maKH": "test",
            "tenKH": "test",
            "tenTrenThe": "test",
            "soTaiKhoan": "test",
            "loaiThe": "test",
            "soThe": "test",
            "ngayDongTheT24": "test",
            "ngayDongTheW4": "test",
            "chiNhanhMoThe": "test",
            "nvDongThe": "test",
            "tenNVDongThe": "test",
            "maCNDongThe": "test",
            "tenCNDongThe": "test",
            "maHanMuc": "test",
            "giaTriHanMuc": "test",
            "tinhTrangDong": "test",
            "ngayDongHanMuc": "test",
            "cnDongHanMuc": "test",
            "nvDongHanMuc": "test",
            "nvDuyetDongHanMuc": "test",
            "ghiChuTrenHanMuc": "test",
            "ghiChu": "test",
            "ngayDuKienDongHanMuc": "test",
            "tinhTrangTaiKhoan": "tinhTrangTaiKhoan",
            "theChinhPhu": "theChinhPhu",
            "contrStatus": "contrStatus BC_THE_DONG_THEO_NGAY HAN_MUC_CAN_DONG"
        }
    ]
}
* */

}
