package com.bellaryinfotech.bulk.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.bulk.model.OrderFabricationDetails;

@Repository
public interface OrderFabricationDetailsRepository extends JpaRepository<OrderFabricationDetails, Long> {
    
    Page<OrderFabricationDetails> findByOrderNumberContainingIgnoreCase(String orderNumber, Pageable pageable);
    
    @Query("SELECT o FROM OrderFabricationDetails o WHERE " +
           "(:search IS NULL OR o.orderNumber LIKE %:search% OR o.drawingNo LIKE %:search% OR o.buildingName LIKE %:search%) AND " +
           "(:orderNumber IS NULL OR o.orderNumber LIKE %:orderNumber%) AND " +
           "(:drawingNo IS NULL OR o.drawingNo LIKE %:drawingNo%) AND " +
           "(:buildingName IS NULL OR o.buildingName LIKE %:buildingName%)")
    Page<OrderFabricationDetails> findByFilters(
            @Param("search") String search,
            @Param("orderNumber") String orderNumber,
            @Param("drawingNo") String drawingNo,
            @Param("buildingName") String buildingName,
            Pageable pageable);
    
    List<OrderFabricationDetails> findByOrderId(Long orderId);
    
    List<OrderFabricationDetails> findTop100ByOrderByIdDesc();
}