package com.bellaryinfotech.bulk.model;

 

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_fabrication_import")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFabricationImport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iface_id")
    private Long ifaceId;
    
    @Column(name = "iface_status")
    private String ifaceStatus;
    
    @Column(name = "error_message")
    private String errorMessage;
    
    @Column(name = "building_name")
    private String buildingName;
    
    @Column(name = "drawing_no")
    private String drawingNo;
    
    @Column(name = "drawing_description")
    private String drawingDescription;
    
    @Column(name = "order_number")
    private String orderNumber;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "orig_line_number")
    private Long origLineNumber;
    
    @Column(name = "orig_line_id")
    private Long origLineId;
    
    @Column(name = "line_number")
    private Long lineNumber;
    
    @Column(name = "line_id")
    private Long lineId;
    
    @Column(name = "erection_mkd")
    private String erectionMkd;
    
    @Column(name = "item_no")
    private String itemNo;
    
    @Column(name = "section")
    private String section;
    
    @Column(name = "length")
    private BigDecimal length;
    
    @Column(name = "length_uom")
    private String lengthUom;
    
    @Column(name = "quantity")
    private BigDecimal quantity;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "unit_price_uom")
    private String unitPriceUom;
    
    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;
    
    @Column(name = "total_quantity_uom")
    private String totalQuantityUom;
    
    @Column(name = "original_quantity")
    private BigDecimal originalQuantity;
    
    @Column(name = "repeated_qty")
    private BigDecimal repeatedQty;
    
    @Column(name = "remark")
    private String remark;
    
    @Column(name = "tenant_id")
    private Long tenantId;
    
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;
    
    @Column(name = "org_id")
    private Long orgId;
    
    
    @Column(name = "batch_name")
    private String batchName;
    
    
    
    public void setIfaceStatus(String ifaceStatus) {
        this.ifaceStatus = ifaceStatus;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
        
        this.updateBatchName();
    }
    
    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
        
        this.updateBatchName();
    }
    
    // Helper method here to update batch_name
    private void updateBatchName() {
        if (this.buildingName != null || this.drawingNo != null) {
            String building = this.buildingName != null ? this.buildingName : "";
            String drawing = this.drawingNo != null ? this.drawingNo : "";
            this.batchName = building + ((!building.isEmpty() && !drawing.isEmpty()) ? " " : "") + drawing;
        }
    }
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    
    public String getBatchName() {
        return this.batchName;
    }
    
    public void setDrawingDescription(String drawingDescription) {
        this.drawingDescription = drawingDescription;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public void setOrigLineNumber(Long origLineNumber) {
        this.origLineNumber = origLineNumber;
    }
    
    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public void setErectionMkd(String erectionMkd) {
        this.erectionMkd = erectionMkd;
    }
    
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
    public void setLength(BigDecimal length) {
        this.length = length;
    }
    
    public void setLengthUom(String lengthUom) {
        this.lengthUom = lengthUom;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public void setUnitPriceUom(String unitPriceUom) {
        this.unitPriceUom = unitPriceUom;
    }
    
    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    public void setTotalQuantityUom(String totalQuantityUom) {
        this.totalQuantityUom = totalQuantityUom;
    }
    
    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }
    
    public void setRepeatedQty(BigDecimal repeatedQty) {
        this.repeatedQty = repeatedQty;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
   
    public void setOrigLineNo(String origLineNo) {
        
        try {
            this.origLineNumber = Long.parseLong(origLineNo);
        } catch (NumberFormatException e) {
            
            this.origLineNumber = null;
        }
    }
    
    public void setLineNo(String lineNo) {
       
        try {
            this.lineNumber = Long.parseLong(lineNo);
        } catch (NumberFormatException e) {
           
            this.lineNumber = null;
        }
    }
    
    public void setDescription(String description) {
        this.drawingDescription = description;
    }
    
    public void setQty(BigDecimal qty) {
        this.quantity = qty;
    }
    
    public void setUnit(BigDecimal unit) {
        this.unitPrice = unit;
    }
    
    public void setTotalWt(BigDecimal totalWt) {
        this.totalQuantity = totalWt;
    }
    
    public void setQtyReqd(BigDecimal qtyReqd) {
        this.originalQuantity = qtyReqd;
    }
    
    public void setErecMkdWt(BigDecimal erecMkdWt) {
        
        this.repeatedQty = erecMkdWt;
    }
    
    public void setRemarks(String remarks) {
        this.remark = remarks;
    }

	public String getBuildingName() {
		 
		return null;
	}

	public String getDrawingNo() {
		 
		return null;
	}
}

