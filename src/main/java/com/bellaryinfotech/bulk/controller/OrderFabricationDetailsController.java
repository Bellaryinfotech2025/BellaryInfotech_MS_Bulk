package com.bellaryinfotech.bulk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bellaryinfotech.bulk.DTO.OrderFabricationDetailsDTO;
import com.bellaryinfotech.bulk.service.OrderFabricationDetailsService;

@RestController
@RequestMapping("/api/V3.0")
@CrossOrigin(origins = "*")
public class OrderFabricationDetailsController {
    
    private static final Logger log = LoggerFactory.getLogger(OrderFabricationDetailsController.class);
    
    @Autowired
    private OrderFabricationDetailsService fabricationDetailsService;
    
    @GetMapping(value = "/getfabrication", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFabricationDetails(
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderNumber", required = false) String orderNumber,
            @RequestParam(value = "drawingNo", required = false) String drawingNo,
            @RequestParam(value = "buildingName", required = false) String buildingName) {
        
        log.info("GET request to fetch all fabrication details with filters");
        return fabricationDetailsService.getAllFabricationDetails(page, size, search, orderNumber, drawingNo, buildingName);
    }
    
    @GetMapping(value = "/getfabrication/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFabricationDetailsById(@PathVariable("id") Long id) {
        log.info("GET request to fetch fabrication details by id: {}", id);
        return fabricationDetailsService.getFabricationDetailsById(id);
    }
    
    @PostMapping(value = "/getfabrication", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFabricationDetails(@RequestBody Map<String, Object> frontendData) {
        try {
            log.info("POST request to create fabrication details from frontend data");
            
            // Convert frontend data to DTO
            OrderFabricationDetailsDTO detailsDTO = fabricationDetailsService.mapFromFrontend(frontendData);
            
            return fabricationDetailsService.createFabricationDetails(detailsDTO);
        } catch (Exception e) {
            log.error("Failed to create fabrication details from frontend data", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "Failed to create fabrication details: " + e.getMessage()
            ));
        }
    }
    
    @PostMapping(value = "/getfabrication/batch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMultipleFabricationDetails(@RequestBody List<Map<String, Object>> frontendDataList) {
        try {
            log.info("POST request to create multiple fabrication details from frontend data, count: {}", frontendDataList.size());
            
            // Convert frontend data list to DTO list
            List<OrderFabricationDetailsDTO> detailsDTOList = new ArrayList<>();
            for (Map<String, Object> frontendData : frontendDataList) {
                detailsDTOList.add(fabricationDetailsService.mapFromFrontend(frontendData));
            }
            
            return fabricationDetailsService.createMultipleFabricationDetails(detailsDTOList);
        } catch (Exception e) {
            log.error("Failed to create multiple fabrication details from frontend data", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "Failed to create multiple fabrication details: " + e.getMessage()
            ));
        }
    }
    
    @PutMapping(value = "/getfabrication/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFabricationDetails(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> frontendData) {
        
        try {
            log.info("PUT request to update fabrication details with id: {}", id);
            
            // Convert frontend data to DTO
            OrderFabricationDetailsDTO detailsDTO = fabricationDetailsService.mapFromFrontend(frontendData);
            
            return fabricationDetailsService.updateFabricationDetails(id, detailsDTO);
        } catch (Exception e) {
            log.error("Failed to update fabrication details with id: {}", id, e);
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "Failed to update fabrication details: " + e.getMessage()
            ));
        }
    }
    
    @DeleteMapping(value = "/getfabrication/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFabricationDetails(@PathVariable("id") Long id) {
        log.info("DELETE request to delete fabrication details with id: {}", id);
        return fabricationDetailsService.deleteFabricationDetails(id);
    }
    
    @GetMapping(value = "/getfabrication/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLatestFabricationDetails() {
        log.info("GET request to fetch latest fabrication details");
        return fabricationDetailsService.getLatestFabricationDetails();
    }
}