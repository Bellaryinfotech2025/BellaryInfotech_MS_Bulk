package com.bellaryinfotech.bulk.repo;
 
 

import com.bellaryinfotech.bulk.model.OrderFabricationImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderFabricationImportRepository extends JpaRepository<OrderFabricationImport, Long> {
    // Add a method to find the most recent records
    List<OrderFabricationImport> findTop100ByOrderByIfaceIdDesc();
}

