package com.bellaryinfotech.bulk.service;

 

import com.bellaryinfotech.bulk.model.OrderFabricationImport;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ExcelImportService {
    List<OrderFabricationImport> importExcelToDatabase(MultipartFile file) throws Exception;
}
