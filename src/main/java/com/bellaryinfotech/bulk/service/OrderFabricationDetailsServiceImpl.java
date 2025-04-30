package com.bellaryinfotech.bulk.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bellaryinfotech.bulk.DTO.OrderFabricationDetailsDTO;
import com.bellaryinfotech.bulk.dao.OrderFabricationDetailsDAO;
import com.bellaryinfotech.bulk.model.OrderFabricationDetails;

@Service
public class OrderFabricationDetailsServiceImpl implements OrderFabricationDetailsService {
    
    private static final Logger log = LoggerFactory.getLogger(OrderFabricationDetailsServiceImpl.class);
    
    @Autowired
    private OrderFabricationDetailsDAO fabricationDetailsDAO;
    
    @Override
    public ResponseEntity<?> getAllFabricationDetails(Integer page, Integer size, String search, 
                                                     String orderNumber, String drawingNo, String buildingName) {
        try {
            log.info("Fetching fabrication details with filters: page={}, size={}, search={}, orderNumber={}, drawingNo={}, buildingName={}",
                    page, size, search, orderNumber, drawingNo, buildingName);
            
            // Default values if not provided
            int pageNo = (page != null) ? page : 0;
            int pageSize = (size != null) ? size : 10;
            
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            
            Page<OrderFabricationDetails> detailsPage = fabricationDetailsDAO.findByFilters(search, orderNumber, drawingNo, buildingName, pageable);
            
            // Convert entities to DTOs
            List<OrderFabricationDetailsDTO> detailsDTOs = detailsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", detailsDTOs);
            response.put("currentPage", detailsPage.getNumber());
            response.put("totalItems", detailsPage.getTotalElements());
            response.put("totalPages", detailsPage.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> getFabricationDetailsById(Long id) {
        try {
            log.info("Fetching fabrication details by id: {}", id);
            
            Optional<OrderFabricationDetails> detailsOpt = fabricationDetailsDAO.findById(id);
            
            if (detailsOpt.isPresent()) {
                OrderFabricationDetailsDTO detailsDTO = convertToDTO(detailsOpt.get());
                return ResponseEntity.ok(detailsDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "error",
                            "message", "Fabrication details not found with id: " + id
                        ));
            }
        } catch (Exception e) {
            log.error("Failed to retrieve fabrication details by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> createFabricationDetails(OrderFabricationDetailsDTO detailsDTO) {
        try {
            log.info("Creating new fabrication details");
            
            OrderFabricationDetails details = convertToEntity(detailsDTO);
            
            // Set creation date and last update date
            Date now = new Date();
            details.setCreationDate(now);
            details.setLastUpdateDate(now);
            
            OrderFabricationDetails savedDetails = fabricationDetailsDAO.save(details);
            OrderFabricationDetailsDTO savedDTO = convertToDTO(savedDetails);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                        "status", "success",
                        "message", "Fabrication details created successfully",
                        "data", savedDTO
                    ));
        } catch (Exception e) {
            log.error("Failed to create fabrication details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to create fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> updateFabricationDetails(Long id, OrderFabricationDetailsDTO detailsDTO) {
        try {
            log.info("Updating fabrication details with id: {}", id);
            
            Optional<OrderFabricationDetails> existingDetailsOpt = fabricationDetailsDAO.findById(id);
            
            if (existingDetailsOpt.isPresent()) {
                OrderFabricationDetails existingDetails = existingDetailsOpt.get();
                
                // Update fields from DTO to entity
                updateEntityFromDTO(detailsDTO, existingDetails);
                
                // Update last update date
                existingDetails.setLastUpdateDate(new Date());
                
                OrderFabricationDetails updatedDetails = fabricationDetailsDAO.save(existingDetails);
                OrderFabricationDetailsDTO updatedDTO = convertToDTO(updatedDetails);
                
                return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Fabrication details updated successfully",
                    "data", updatedDTO
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "error",
                            "message", "Fabrication details not found with id: " + id
                        ));
            }
        } catch (Exception e) {
            log.error("Failed to update fabrication details with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to update fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> deleteFabricationDetails(Long id) {
        try {
            log.info("Deleting fabrication details with id: {}", id);
            
            Optional<OrderFabricationDetails> existingDetailsOpt = fabricationDetailsDAO.findById(id);
            
            if (existingDetailsOpt.isPresent()) {
                fabricationDetailsDAO.deleteById(id);
                
                return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Fabrication details deleted successfully"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "status", "error",
                            "message", "Fabrication details not found with id: " + id
                        ));
            }
        } catch (Exception e) {
            log.error("Failed to delete fabrication details with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to delete fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> getLatestFabricationDetails() {
        try {
            log.info("Fetching latest fabrication details");
            
            List<OrderFabricationDetails> latestDetails = fabricationDetailsDAO.findLatest();
            
            // Convert entities to DTOs
            List<OrderFabricationDetailsDTO> detailsDTOs = latestDetails.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", detailsDTOs);
            response.put("totalItems", detailsDTOs.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve latest fabrication details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve latest fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    @Override
    public ResponseEntity<?> createMultipleFabricationDetails(List<OrderFabricationDetailsDTO> detailsDTOList) {
        try {
            log.info("Creating multiple fabrication details, count: {}", detailsDTOList.size());
            
            List<OrderFabricationDetails> detailsList = detailsDTOList.stream()
                    .map(dto -> {
                        OrderFabricationDetails entity = convertToEntity(dto);
                        
                        // Set creation date and last update date
                        Date now = new Date();
                        entity.setCreationDate(now);
                        entity.setLastUpdateDate(now);
                        
                        return entity;
                    })
                    .collect(Collectors.toList());
            
            List<OrderFabricationDetails> savedDetailsList = fabricationDetailsDAO.saveAll(detailsList);
            
            List<OrderFabricationDetailsDTO> savedDTOList = savedDetailsList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                        "status", "success",
                        "message", "Multiple fabrication details created successfully",
                        "recordsCreated", savedDTOList.size(),
                        "data", savedDTOList
                    ));
        } catch (Exception e) {
            log.error("Failed to create multiple fabrication details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to create multiple fabrication details: " + e.getMessage()
                    ));
        }
    }
    
    // Helper method to convert entity to DTO
    private OrderFabricationDetailsDTO convertToDTO(OrderFabricationDetails details) {
        OrderFabricationDetailsDTO dto = new OrderFabricationDetailsDTO();
        
        dto.setId(details.getId());
        dto.setBuildingName(details.getBuildingName());
        dto.setDrawingNo(details.getDrawingNo());
        dto.setDrawingDescription(details.getDrawingDescription());
        dto.setOrderNumber(details.getOrderNumber());
        dto.setOrderId(details.getOrderId());
        dto.setOrigLineNumber(details.getOrigLineNumber());
        dto.setOrigLineId(details.getOrigLineId());
        dto.setLineNumber(details.getLineNumber());
        dto.setLineId(details.getLineId());
        dto.setErectionMkd(details.getErectionMkd());
        dto.setItemNo(details.getItemNo());
        dto.setSection(details.getSection());
        dto.setLength(details.getLength());
        dto.setLengthUom(details.getLengthUom());
        dto.setQuantity(details.getQuantity());
        dto.setUnitPrice(details.getUnitPrice());
        dto.setUnitPriceUom(details.getUnitPriceUom());
        dto.setTotalQuantity(details.getTotalQuantity());
        dto.setTotalQuantityUom(details.getTotalQuantityUom());
        dto.setOriginalQuantity(details.getOriginalQuantity());
        dto.setRepeatedQty(details.getRepeatedQty());
        dto.setRemark(details.getRemark());
        dto.setTenantId(details.getTenantId());
        dto.setCreationDate(details.getCreationDate());
        dto.setCreatedBy(details.getCreatedBy());
        dto.setLastUpdateDate(details.getLastUpdateDate());
        dto.setLastUpdatedBy(details.getLastUpdatedBy());
        dto.setOrgId(details.getOrgId());
        
        return dto;
    }
    
    // Helper method to convert DTO to entity
    private OrderFabricationDetails convertToEntity(OrderFabricationDetailsDTO dto) {
        OrderFabricationDetails entity = new OrderFabricationDetails();
        
        entity.setId(dto.getId());
        entity.setBuildingName(dto.getBuildingName());
        entity.setDrawingNo(dto.getDrawingNo());
        entity.setDrawingDescription(dto.getDrawingDescription());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setOrderId(dto.getOrderId());
        entity.setOrigLineNumber(dto.getOrigLineNumber());
        entity.setOrigLineId(dto.getOrigLineId());
        entity.setLineNumber(dto.getLineNumber());
        entity.setLineId(dto.getLineId());
        entity.setErectionMkd(dto.getErectionMkd());
        entity.setItemNo(dto.getItemNo());
        entity.setSection(dto.getSection());
        entity.setLength(dto.getLength());
        entity.setLengthUom(dto.getLengthUom());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setUnitPriceUom(dto.getUnitPriceUom());
        entity.setTotalQuantity(dto.getTotalQuantity());
        entity.setTotalQuantityUom(dto.getTotalQuantityUom());
        entity.setOriginalQuantity(dto.getOriginalQuantity());
        entity.setRepeatedQty(dto.getRepeatedQty());
        entity.setRemark(dto.getRemark());
        entity.setTenantId(dto.getTenantId());
        entity.setCreationDate(dto.getCreationDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        entity.setOrgId(dto.getOrgId());
        
        return entity;
    }
    
    // Helper method to update entity from DTO
    private void updateEntityFromDTO(OrderFabricationDetailsDTO dto, OrderFabricationDetails entity) {
        if (dto.getBuildingName() != null) entity.setBuildingName(dto.getBuildingName());
        if (dto.getDrawingNo() != null) entity.setDrawingNo(dto.getDrawingNo());
        if (dto.getDrawingDescription() != null) entity.setDrawingDescription(dto.getDrawingDescription());
        if (dto.getOrderNumber() != null) entity.setOrderNumber(dto.getOrderNumber());
        if (dto.getOrderId() != null) entity.setOrderId(dto.getOrderId());
        if (dto.getOrigLineNumber() != null) entity.setOrigLineNumber(dto.getOrigLineNumber());
        if (dto.getOrigLineId() != null) entity.setOrigLineId(dto.getOrigLineId());
        if (dto.getLineNumber() != null) entity.setLineNumber(dto.getLineNumber());
        if (dto.getLineId() != null) entity.setLineId(dto.getLineId());
        if (dto.getErectionMkd() != null) entity.setErectionMkd(dto.getErectionMkd());
        if (dto.getItemNo() != null) entity.setItemNo(dto.getItemNo());
        if (dto.getSection() != null) entity.setSection(dto.getSection());
        if (dto.getLength() != null) entity.setLength(dto.getLength());
        if (dto.getLengthUom() != null) entity.setLengthUom(dto.getLengthUom());
        if (dto.getQuantity() != null) entity.setQuantity(dto.getQuantity());
        if (dto.getUnitPrice() != null) entity.setUnitPrice(dto.getUnitPrice());
        if (dto.getUnitPriceUom() != null) entity.setUnitPriceUom(dto.getUnitPriceUom());
        if (dto.getTotalQuantity() != null) entity.setTotalQuantity(dto.getTotalQuantity());
        if (dto.getTotalQuantityUom() != null) entity.setTotalQuantityUom(dto.getTotalQuantityUom());
        if (dto.getOriginalQuantity() != null) entity.setOriginalQuantity(dto.getOriginalQuantity());
        if (dto.getRepeatedQty() != null) entity.setRepeatedQty(dto.getRepeatedQty());
        if (dto.getRemark() != null) entity.setRemark(dto.getRemark());
        if (dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getLastUpdatedBy() != null) entity.setLastUpdatedBy(dto.getLastUpdatedBy());
        if (dto.getOrgId() != null) entity.setOrgId(dto.getOrgId());
    }
    
    // Helper method to map frontend data to DTO
    @Override
    public OrderFabricationDetailsDTO mapFromFrontend(Map<String, Object> frontendData) {
        OrderFabricationDetailsDTO dto = new OrderFabricationDetailsDTO();
        
        // Map fields from frontend to DTO
        if (frontendData.get("id") != null && !frontendData.get("id").toString().isEmpty()) {
            try {
                dto.setId(Long.valueOf(frontendData.get("id").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid id format: {}", frontendData.get("id"));
            }
        }
        
        dto.setOrderNumber(getStringValue(frontendData, "orderNumber"));
        
        if (frontendData.get("origLineNo") != null && !frontendData.get("origLineNo").toString().isEmpty()) {
            try {
                dto.setOrigLineNumber(Long.valueOf(frontendData.get("origLineNo").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid origLineNo format: {}", frontendData.get("origLineNo"));
            }
        }
        
        if (frontendData.get("lineNo") != null && !frontendData.get("lineNo").toString().isEmpty()) {
            try {
                dto.setLineNumber(Long.valueOf(frontendData.get("lineNo").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid lineNo format: {}", frontendData.get("lineNo"));
            }
        }
        
        dto.setDrawingNo(getStringValue(frontendData, "drawingNo"));
        dto.setDrawingDescription(getStringValue(frontendData, "description"));
        dto.setErectionMkd(getStringValue(frontendData, "erectionMkd"));
        dto.setItemNo(getStringValue(frontendData, "itemNo"));
        dto.setSection(getStringValue(frontendData, "section"));
        
        if (frontendData.get("length") != null && !frontendData.get("length").toString().isEmpty()) {
            try {
                dto.setLength(new BigDecimal(frontendData.get("length").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid length format: {}", frontendData.get("length"));
            }
        }
        
        if (frontendData.get("qty") != null && !frontendData.get("qty").toString().isEmpty()) {
            try {
                dto.setQuantity(new BigDecimal(frontendData.get("qty").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid qty format: {}", frontendData.get("qty"));
            }
        }
        
        if (frontendData.get("unit") != null && !frontendData.get("unit").toString().isEmpty()) {
            try {
                dto.setUnitPrice(new BigDecimal(frontendData.get("unit").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid unit format: {}", frontendData.get("unit"));
            }
        }
        
        if (frontendData.get("totalWt") != null && !frontendData.get("totalWt").toString().isEmpty()) {
            try {
                dto.setTotalQuantity(new BigDecimal(frontendData.get("totalWt").toString()));
            } catch (NumberFormatException e) {
                log.warn("Invalid totalWt format: {}", frontendData.get("totalWt"));
            }
        }
        
        dto.setRemark(getStringValue(frontendData, "remarks"));
        
        // Set default values for dates
        dto.setCreationDate(new Date());
        dto.setLastUpdateDate(new Date());
        
        // Set default values for user IDs
        dto.setCreatedBy(1); // Default user ID
        dto.setLastUpdatedBy(1); // Default user ID
        
        return dto;
    }
    
    private String getStringValue(Map<String, Object> data, String key) {
        return data.get(key) != null ? data.get(key).toString() : null;
    }
}