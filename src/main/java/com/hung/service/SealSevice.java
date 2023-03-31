package com.hung.service;

import com.hung.dto.DailyLimitClosingReportReq;
import com.hung.dto.request.BaoCaoTonKhoDTO;
import com.hung.dto.request.ListGTCG;
import com.hung.dto.request.ListGTCG1;
import com.hung.dto.request.PhieuXuatKhoDTO;
import com.hung.utils.ExcelHelper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SealSevice {
    private final static Logger log = LoggerFactory.getLogger(SealSevice.class);

    public byte[] exportBillDelivery(List<PhieuXuatKhoDTO> dtos) throws JRException {
        log.debug("Start exportBillDelivery ATQT info {}", dtos);

        if (dtos != null && dtos.size() > 0) {

            try {
                List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
                Map<String, Object> parameters = new HashMap<>();
                for (int i = 0; i < dtos.size(); i++) {
                    JasperReport jasperReport = getJasperReport("reports/PhieuXuatKho.jrxml");
                    parameters.put("logo", new ClassPathResource("images/logo.png").getInputStream());
                    parameters.put("coCode", dtos.get(i).getCoCode());
                    parameters.put("coName", dtos.get(i).getCoName());
                    parameters.put("ngay", dtos.get(i).getNgay());
                    parameters.put("maButToan", dtos.get(i).getMaButToan());
                    parameters.put("maKhoChuyen", dtos.get(i).getMaKhoChuyen());
                    parameters.put("maKhoNhan", dtos.get(i).getMaKhoNhan());
                    parameters.put("maGd", dtos.get(i).getMaGd());

                    List<ListGTCG> feeDataSource1 = dtos.get(i).getList();

                    List<ListGTCG1> feeDataSource = new ArrayList<>();
                    for(ListGTCG listGTCG: feeDataSource1){
                        for(ListGTCG1 listGTCG1: listGTCG.getSubList()){
                            feeDataSource.add(listGTCG1);
                        }
                    }

                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(feeDataSource);
                    parameters.put("feeDataSource", dataSource);
                    parameters.put("nguoiXuatKho", dtos.get(i).getNguoiXuatKho());
                    parameters.put("kiemSoat", dtos.get(i).getKiemSoat());
                    parameters.put("nguoiNhan", dtos.get(i).getNguoiNhan());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
                    jasperPrintList.add(jasperPrint);
                }
                JRPdfExporter exporter = new JRPdfExporter();
                ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
                exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutputStream));

                SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
                reportConfig.setSizePageToContent(true);
                reportConfig.setForceLineBreakPolicy(false);
                exporter.exportReport();
                return pdfOutputStream.toByteArray();
            } catch (JRException | IOException e) {
                throw new JRException(e.getMessage());
            }
        } else {
            return null;
        }
    }

    public byte[] inventoryNote(BaoCaoTonKhoDTO dtos) throws JRException {
        log.debug("Start inventoryNote ACQT info {}", dtos);
        try {

            JasperReport jasperReport = getJasperReport("reports/TonKhoACQT.jrxml");
            // khai báo các parameter
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logo", new ClassPathResource("images/logo.png").getInputStream());
            parameters.put("coCode", dtos.getCoCode());
            parameters.put("coName", dtos.getCoName());
            parameters.put("ngayTao", dtos.getNgayTao());
            parameters.put("khoBaoCao", dtos.getKhoBaoCao());


            List<BaoCaoTonKhoDTO.GTCG> feeDataSource = dtos.getGtcgs();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(feeDataSource);
            parameters.put("feeDataSource", dataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException | IOException e) {
            throw new JRException(e.getMessage());
        }

    }

    private static JasperReport getJasperReport(String path) throws IOException, JRException {
        JasperReport jasperReport = null;
        ClassPathResource classPathResource = new ClassPathResource(path);
        if (classPathResource.isReadable()) {
            try (InputStream inputStream = classPathResource.getInputStream()) {
                jasperReport = JasperCompileManager.compileReport(inputStream);
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        }
        return jasperReport;
    }
    public ByteArrayOutputStream exportDailyLimitClosingReportExcel(DailyLimitClosingReportReq req) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (req != null) {
            Resource inputResource;
            Map<String, Object> data = new HashMap<>();
            if (req.getType().equals("BC_DONG_HM_THEO_NGAY")) {
                inputResource = new ClassPathResource("templates/EXCEL/BC_DONG_HM_THEO_NGAY.xlsx");
                data.put("fromDate", req.getFromDate());
                data.put("toDate", req.getToDate());
            } else if (req.getType().equals("BC_THE_DONG_THEO_NGAY")) {
                inputResource = new ClassPathResource("templates/EXCEL/BC_THE_DONG_THEO_NGAY.xlsx");
            } else { //title:HAN_MUC_CAN_DONG
                inputResource = new ClassPathResource("templates/EXCEL/HAN_MUC_CAN_DONG.xlsx");
            }
            data.put("data", req.getBaocaos());
            data.put("currentTime", req.getCurrentTime());
            outputStream = ExcelHelper.createAndDownloadDocument(inputResource, data);
        }
        return outputStream;
    }
}
