package com.downloadExcel.Controller;

import com.downloadExcel.Service.ExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        ByteArrayInputStream in = excelReportService.generateExcelReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=EmployeeReport_17_02_2025.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
}
