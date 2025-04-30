package com.bellaryinfotech.bulk.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.bellaryinfotech.bulk.DTO.OrderFabricationDetailsDTO;

public interface OrderFabricationDetailsService {
    
    ResponseEntity<?> getAllFabricationDetails(Integer page, Integer size, String search, 
                                              String orderNumber, String drawingNo, String buildingName);
    
    ResponseEntity<?> getFabricationDetailsById(Long id);
    
    ResponseEntity<?> createFabricationDetails(OrderFabricationDetailsDTO detailsDTO);
    
    ResponseEntity<?> updateFabricationDetails(Long id, OrderFabricationDetailsDTO detailsDTO);
    
    ResponseEntity<?> deleteFabricationDetails(Long id);
    
    ResponseEntity<?> getLatestFabricationDetails();
    
    ResponseEntity<?> createMultipleFabricationDetails(List<OrderFabricationDetailsDTO> detailsDTOList);
    
    OrderFabricationDetailsDTO mapFromFrontend(Map<String, Object> frontendData);
}