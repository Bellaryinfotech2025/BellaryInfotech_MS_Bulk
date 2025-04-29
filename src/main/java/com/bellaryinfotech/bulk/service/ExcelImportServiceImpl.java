package com.bellaryinfotech.bulk.service;
 

import com.bellaryinfotech.bulk.model.OrderFabricationImport;
import com.bellaryinfotech.bulk.repo.OrderFabricationImportRepository;
import com.bellaryinfotech.bulk.service.ExcelImportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {

    private static final Logger log = LoggerFactory.getLogger(ExcelImportServiceImpl.class);

    @Autowired
    private OrderFabricationImportRepository repository;

    // Define column mappings for Excel headers to entity fields
    private static final Map<String, String> COLUMN_MAPPINGS = new HashMap<>();
    
    static {
        // Map Excel column names to entity field names (case insensitive)
        COLUMN_MAPPINGS.put("BUILDING_NAME", "buildingName");
        COLUMN_MAPPINGS.put("DRAWING_NO", "drawingNo");
        COLUMN_MAPPINGS.put("DRAWING_DESCRIPTION", "drawingDescription");
        COLUMN_MAPPINGS.put("ORDER_NUMBER", "orderNumber");
        COLUMN_MAPPINGS.put("ORIG_LINE_NUMBER", "origLineNumber");
        COLUMN_MAPPINGS.put("LINE_NUMBER", "lineNumber");
        COLUMN_MAPPINGS.put("ERECTION_MKD", "erectionMkd");
        COLUMN_MAPPINGS.put("ITEM_NO", "itemNo");
        COLUMN_MAPPINGS.put("SECTION", "section");
        COLUMN_MAPPINGS.put("LENGTH", "length");
        COLUMN_MAPPINGS.put("LENGTH_UOM", "lengthUom");
        COLUMN_MAPPINGS.put("QUANTITY", "quantity");
        COLUMN_MAPPINGS.put("UNIT_PRICE", "unitPrice");
        COLUMN_MAPPINGS.put("UNIT_PRICE_UOM", "unitPriceUom");
        COLUMN_MAPPINGS.put("TOTAL_QUANTITY", "totalQuantity");
        COLUMN_MAPPINGS.put("TOTAL_QUANTITY_UOM", "totalQuantityUom");
        COLUMN_MAPPINGS.put("ORIGINAL_QUANTITY", "originalQuantity");
        COLUMN_MAPPINGS.put("REPEATED_QTY", "repeatedQty");
        COLUMN_MAPPINGS.put("REMARK", "remark");
        
        // Alternative column names and variations
        COLUMN_MAPPINGS.put("BUILDING NAME", "buildingName");
        COLUMN_MAPPINGS.put("DRAWING NUMBER", "drawingNo");
        COLUMN_MAPPINGS.put("DRAWING NO", "drawingNo");
        COLUMN_MAPPINGS.put("DRAWING DESC", "drawingDescription");
        COLUMN_MAPPINGS.put("DESCRIPTION", "drawingDescription");
        COLUMN_MAPPINGS.put("ORDER NO", "orderNumber");
        COLUMN_MAPPINGS.put("ORIGINAL LINE NUMBER", "origLineNumber");
        COLUMN_MAPPINGS.put("ORIG LINE NO", "origLineNumber");
        COLUMN_MAPPINGS.put("LINE NO", "lineNumber");
        COLUMN_MAPPINGS.put("ERECTION MARK", "erectionMkd");
        COLUMN_MAPPINGS.put("ERECTION MKD.", "erectionMkd");
        COLUMN_MAPPINGS.put("ITEM NUMBER", "itemNo");
        COLUMN_MAPPINGS.put("ITEM NO.", "itemNo");
        COLUMN_MAPPINGS.put("QTY", "quantity");
        COLUMN_MAPPINGS.put("PRICE", "unitPrice");
        COLUMN_MAPPINGS.put("UNIT", "unitPrice");
        COLUMN_MAPPINGS.put("TOTAL QTY", "totalQuantity");
        COLUMN_MAPPINGS.put("TOTAL WT.", "totalQuantity");
        COLUMN_MAPPINGS.put("ORIGINAL QTY", "originalQuantity");
        COLUMN_MAPPINGS.put("QTY. REQD", "originalQuantity");
        COLUMN_MAPPINGS.put("REPEATED QUANTITY", "repeatedQty");
        COLUMN_MAPPINGS.put("EREC. MKD. WT.", "repeatedQty");
        COLUMN_MAPPINGS.put("REMARKS", "remark");
    }

    @Override
    public List<OrderFabricationImport> importExcelToDatabase(MultipartFile file) throws Exception {
        List<OrderFabricationImport> importList = new ArrayList<>();
        
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            log.info("Excel file opened successfully. Number of sheets: {}", workbook.getNumberOfSheets());
            
            // Process the first sheet (or you can loop through all sheets if needed)
            Sheet sheet = workbook.getSheetAt(0);
            log.info("Processing sheet: {} with {} rows", sheet.getSheetName(), sheet.getLastRowNum() + 1);
            
            // Get column headers and their indices
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                log.warn("Sheet has no header row, skipping");
                return importList;
            }
            
            Map<Integer, String> columnIndexToFieldMap = mapHeadersToFields(headerRow);
            log.info("Mapped {} columns in sheet {}", columnIndexToFieldMap.size(), sheet.getSheetName());
            
            if (columnIndexToFieldMap.isEmpty()) {
                log.error("No valid column mappings found. Check your Excel headers.");
                throw new Exception("No valid column mappings found. Check your Excel headers.");
            }
            
            // Process data rows
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }
                
                // Create a new entity for each row
                OrderFabricationImport entity = new OrderFabricationImport();
                entity.setIfaceStatus("NEW");
                entity.setCreationDate(new Date());
                entity.setLastUpdateDate(new Date());
                
                // Process row data
                boolean hasData = processRowData(row, columnIndexToFieldMap, entity);
                
                if (hasData) {
                    // Set batch_name as a combination of building_name and drawing_no
                    setBatchName(entity);
                    log.debug("Adding entity to import list: {}", entity);
                    importList.add(entity);
                }
            }
            
            // Save all records to database
            if (!importList.isEmpty()) {
                log.info("Saving {} records to database", importList.size());
                importList = repository.saveAll(importList);
                log.info("Successfully saved {} records", importList.size());
            } else {
                log.warn("No valid records found to import");
            }
            
            return importList;
        } catch (Exception e) {
            log.error("Error importing Excel file: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    // Helper method to set batch_name
    private void setBatchName(OrderFabricationImport entity) {
        String buildingName = entity.getBuildingName();
        String drawingNo = entity.getDrawingNo();
        
        if (buildingName != null || drawingNo != null) {
            String building = buildingName != null ? buildingName : "";
            String drawing = drawingNo != null ? drawingNo : "";
            String batchName = building + ((!building.isEmpty() && !drawing.isEmpty()) ? " " : "") + drawing;
            entity.setBatchName(batchName);
            log.debug("Set batch_name to: {}", batchName);
        }
    }
    
    private Map<Integer, String> mapHeadersToFields(Row headerRow) {
        Map<Integer, String> columnIndexToFieldMap = new HashMap<>();
        
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String headerValue = cell.getStringCellValue().trim().toUpperCase();
                log.info("Found header: '{}' at index {}", headerValue, i);
                
                // Check if this header is in our mapping
                String fieldName = COLUMN_MAPPINGS.get(headerValue);
                if (fieldName != null) {
                    columnIndexToFieldMap.put(i, fieldName);
                    log.info("Mapped header '{}' to field '{}'", headerValue, fieldName);
                } else {
                    log.warn("Unmapped header found: '{}'", headerValue);
                }
            }
        }
        
        return columnIndexToFieldMap;
    }
    
    private boolean processRowData(Row row, Map<Integer, String> columnIndexToFieldMap, OrderFabricationImport entity) {
        boolean hasData = false;
        
        for (Map.Entry<Integer, String> entry : columnIndexToFieldMap.entrySet()) {
            int columnIndex = entry.getKey();
            String fieldName = entry.getValue();
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            
            if (cell != null) {
                try {
                    log.debug("Processing cell at column {} for field {}, cell type: {}", 
                             columnIndex, fieldName, cell.getCellType());
                    
                    boolean valueSet = setFieldValue(entity, fieldName, cell);
                    if (valueSet) {
                        hasData = true;
                    }
                } catch (Exception e) {
                    log.error("Error setting field {} from column {}: {}", fieldName, columnIndex, e.getMessage(), e);
                    entity.setErrorMessage("Error in column " + columnIndex + ": " + e.getMessage());
                    entity.setIfaceStatus("ERROR");
                }
            }
        }
        
        return hasData;
    }
    
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                return false;
            }
        }
        return true;
    }
    
    private boolean setFieldValue(OrderFabricationImport entity, String fieldName, Cell cell) {
        try {
            String stringValue = getCellValueAsString(cell);
            BigDecimal numericValue = getCellValueAsBigDecimal(cell);
            Long longValue = null;
            
            if (numericValue != null) {
                try {
                    longValue = numericValue.longValue();
                } catch (Exception e) {
                    log.warn("Could not convert numeric value to Long: {}", e.getMessage());
                }
            }
            
            switch (fieldName) {
                case "buildingName":
                    if (stringValue != null) {
                        entity.setBuildingName(stringValue);
                        return true;
                    }
                    break;
                case "drawingNo":
                    if (stringValue != null) {
                        entity.setDrawingNo(stringValue);
                        return true;
                    }
                    break;
                case "drawingDescription":
                    if (stringValue != null) {
                        entity.setDrawingDescription(stringValue);
                        return true;
                    }
                    break;
                case "orderNumber":
                    if (stringValue != null) {
                        entity.setOrderNumber(stringValue);
                        return true;
                    }
                    break;
                case "origLineNumber":
                    if (longValue != null) {
                        entity.setOrigLineNumber(longValue);
                        return true;
                    } else if (stringValue != null) {
                        try {
                            entity.setOrigLineNumber(Long.parseLong(stringValue));
                            return true;
                        } catch (NumberFormatException e) {
                            entity.setOrigLineNumber(null);
                            return false;
                        }
                    }
                    break;
                case "lineNumber":
                    if (longValue != null) {
                        entity.setLineNumber(longValue);
                        return true;
                    } else if (stringValue != null) {
                        try {
                            entity.setLineNumber(Long.parseLong(stringValue));
                            return true;
                        } catch (NumberFormatException e) {
                            entity.setLineNumber(null);
                            return false;
                        }
                    }
                    break;
                case "erectionMkd":
                    if (stringValue != null) {
                        entity.setErectionMkd(stringValue);
                        return true;
                    }
                    break;
                case "itemNo":
                    if (stringValue != null) {
                        entity.setItemNo(stringValue);
                        return true;
                    }
                    break;
                case "section":
                    if (stringValue != null) {
                        entity.setSection(stringValue);
                        return true;
                    }
                    break;
                case "length":
                    if (numericValue != null) {
                        entity.setLength(numericValue);
                        return true;
                    }
                    break;
                case "lengthUom":
                    if (stringValue != null) {
                        entity.setLengthUom(stringValue);
                        return true;
                    }
                    break;
                case "quantity":
                    if (numericValue != null) {
                        entity.setQuantity(numericValue);
                        return true;
                    }
                    break;
                case "unitPrice":
                    if (numericValue != null) {
                        entity.setUnitPrice(numericValue);
                        return true;
                    }
                    break;
                case "unitPriceUom":
                    if (stringValue != null) {
                        entity.setUnitPriceUom(stringValue);
                        return true;
                    }
                    break;
                case "totalQuantity":
                    if (numericValue != null) {
                        entity.setTotalQuantity(numericValue);
                        return true;
                    }
                    break;
                case "totalQuantityUom":
                    if (stringValue != null) {
                        entity.setTotalQuantityUom(stringValue);
                        return true;
                    }
                    break;
                case "originalQuantity":
                    if (numericValue != null) {
                        entity.setOriginalQuantity(numericValue);
                        return true;
                    }
                    break;
                case "repeatedQty":
                    if (numericValue != null) {
                        entity.setRepeatedQty(numericValue);
                        return true;
                    }
                    break;
                case "remark":
                    if (stringValue != null) {
                        entity.setRemark(stringValue);
                        return true;
                    }
                    break;
                default:
                    log.warn("Unknown field name: {}", fieldName);
                    return false;
            }
            
            return false;
        } catch (Exception e) {
            log.error("Error setting field {}: {}", fieldName, e.getMessage(), e);
            entity.setErrorMessage("Error setting field " + fieldName + ": " + e.getMessage());
            entity.setIfaceStatus("ERROR");
            return false;
        }
    }
    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        try {
            switch (cell.getCellType()) {
                case STRING:
                    String value = cell.getStringCellValue().trim();
                    return value.isEmpty() ? null : value;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getLocalDateTimeCellValue().toString();
                    }
                    // Format numeric values to avoid scientific notation
                    return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        String formulaValue = cell.getStringCellValue().trim();
                        return formulaValue.isEmpty() ? null : formulaValue;
                    } catch (Exception e) {
                        try {
                            return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                        } catch (Exception ex) {
                            log.warn("Could not evaluate formula in cell: {}", e.getMessage());
                            return null;
                        }
                    }
                case BLANK:
                    return null;
                case ERROR:
                    log.warn("Error in cell: {}", cell.getErrorCellValue());
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            log.error("Error getting cell value as string: {}", e.getMessage(), e);
            return null;
        }
    }
    
    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    double value = cell.getNumericCellValue();
                    log.debug("Numeric cell value: {}", value);
                    return BigDecimal.valueOf(value);
                case STRING:
                    try {
                        String stringValue = cell.getStringCellValue().trim();
                        log.debug("String cell value for conversion to BigDecimal: '{}'", stringValue);
                        if (stringValue.isEmpty()) {
                            return null;
                        }
                        // Remove any non-numeric characters except decimal point
                        stringValue = stringValue.replaceAll("[^\\d.-]", "");
                        if (stringValue.isEmpty()) {
                            return null;
                        }
                        return new BigDecimal(stringValue);
                    } catch (NumberFormatException e) {
                        log.warn("Could not parse '{}' as BigDecimal: {}", cell.getStringCellValue(), e.getMessage());
                        return null;
                    }
                case FORMULA:
                    try {
                        double formulaValue = cell.getNumericCellValue();
                        log.debug("Formula cell evaluated to: {}", formulaValue);
                        return BigDecimal.valueOf(formulaValue);
                    } catch (Exception e) {
                        log.warn("Could not evaluate formula as numeric: {}", e.getMessage());
                        return null;
                    }
                case BOOLEAN:
                    boolean boolValue = cell.getBooleanCellValue();
                    return boolValue ? BigDecimal.ONE : BigDecimal.ZERO;
                default:
                    return null;
            }
        } catch (Exception e) {
            log.error("Error getting cell value as BigDecimal: {}", e.getMessage(), e);
            return null;
        }
    }
}

