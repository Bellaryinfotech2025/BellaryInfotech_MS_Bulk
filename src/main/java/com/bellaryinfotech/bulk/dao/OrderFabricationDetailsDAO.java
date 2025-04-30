package com.bellaryinfotech.bulk.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bellaryinfotech.bulk.model.OrderFabricationDetails;

public interface OrderFabricationDetailsDAO {
    
    OrderFabricationDetails save(OrderFabricationDetails details);
    
    List<OrderFabricationDetails> saveAll(List<OrderFabricationDetails> detailsList);
    
    Optional<OrderFabricationDetails> findById(Long id);
    
    List<OrderFabricationDetails> findAll();
    
    Page<OrderFabricationDetails> findAll(Pageable pageable);
    
    Page<OrderFabricationDetails> findByFilters(String search, String orderNumber, String drawingNo, String buildingName, Pageable pageable);
    
    void deleteById(Long id);
    
    List<OrderFabricationDetails> findByOrderId(Long orderId);
    
    List<OrderFabricationDetails> findLatest();
}