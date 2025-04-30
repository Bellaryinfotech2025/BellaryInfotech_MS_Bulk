package com.bellaryinfotech.bulk.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.bulk.model.OrderFabricationDetails;
import com.bellaryinfotech.bulk.repo.OrderFabricationDetailsRepository;

@Repository
public class OrderFabricationDetailsDAOImpl implements OrderFabricationDetailsDAO {
    
    @Autowired
    private OrderFabricationDetailsRepository repository;
    
    @Override
    public OrderFabricationDetails save(OrderFabricationDetails details) {
        return repository.save(details);
    }
    
    @Override
    public List<OrderFabricationDetails> saveAll(List<OrderFabricationDetails> detailsList) {
        return repository.saveAll(detailsList);
    }
    
    @Override
    public Optional<OrderFabricationDetails> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public List<OrderFabricationDetails> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Page<OrderFabricationDetails> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    @Override
    public Page<OrderFabricationDetails> findByFilters(String search, String orderNumber, String drawingNo, String buildingName, Pageable pageable) {
        return repository.findByFilters(search, orderNumber, drawingNo, buildingName, pageable);
    }
    
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public List<OrderFabricationDetails> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }
    
    @Override
    public List<OrderFabricationDetails> findLatest() {
        return repository.findTop100ByOrderByIdDesc();
    }
}