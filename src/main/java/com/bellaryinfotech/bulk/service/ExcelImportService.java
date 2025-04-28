package com.bellaryinfotech.bulk.service;


import org.springframework.web.multipart.MultipartFile;

public interface ExcelImportService {
    int importExcelToDatabase(MultipartFile file) throws Exception;
}

