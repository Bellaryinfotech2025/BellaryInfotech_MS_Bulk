package com.bellaryinfotech.bulk.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "order_fabrication_details")
public class OrderFabricationDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "building_name", length = 250)
    private String buildingName;
    
    @Column(name = "drawing_no", length = 250)
    private String drawingNo;
    
    @Column(name = "drawing_description", length = 250)
    private String drawingDescription;
    
    @Column(name = "order_number", length = 250)
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
    
    @Column(name = "erection_mkd", length = 250)
    private String erectionMkd;
    
    @Column(name = "item_no", length = 250)
    private String itemNo;
    
    @Column(name = "section", length = 250)
    private String section;
    
    @Column(name = "length")
    private BigDecimal length;
    
    @Column(name = "length_uom", length = 250)
    private String lengthUom;
    
    @Column(name = "quantity")
    private BigDecimal quantity;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    @Column(name = "unit_price_uom", length = 250)
    private String unitPriceUom;
    
    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;
    
    @Column(name = "total_quantity_uom", length = 250)
    private String totalQuantityUom;
    
    @Column(name = "original_quantity")
    private BigDecimal originalQuantity;
    
    @Column(name = "repeated_qty")
    private BigDecimal repeatedQty;
    
    @Column(name = "remark", length = 250)
    private String remark;
    
    @Column(name = "tenant_id")
    private Integer tenantId;
    
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    
    // Change from Long to Integer to match existing database column type
    @Column(name = "created_by")
    private Integer createdBy;
    
    @Column(name = "last_update_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;
    
    // Change from Long to Integer to match existing database column type
    @Column(name = "last_updated_by")
    private Integer lastUpdatedBy;
    
    @Column(name = "org_id")
    private Long orgId;
    
    // Default constructor
    public OrderFabricationDetails() {
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getDrawingDescription() {
        return drawingDescription;
    }

    public void setDrawingDescription(String drawingDescription) {
        this.drawingDescription = drawingDescription;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrigLineNumber() {
        return origLineNumber;
    }

    public void setOrigLineNumber(Long origLineNumber) {
        this.origLineNumber = origLineNumber;
    }

    public Long getOrigLineId() {
        return origLineId;
    }

    public void setOrigLineId(Long origLineId) {
        this.origLineId = origLineId;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getErectionMkd() {
        return erectionMkd;
    }

    public void setErectionMkd(String erectionMkd) {
        this.erectionMkd = erectionMkd;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public String getLengthUom() {
        return lengthUom;
    }

    public void setLengthUom(String lengthUom) {
        this.lengthUom = lengthUom;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceUom() {
        return unitPriceUom;
    }

    public void setUnitPriceUom(String unitPriceUom) {
        this.unitPriceUom = unitPriceUom;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalQuantityUom() {
        return totalQuantityUom;
    }

    public void setTotalQuantityUom(String totalQuantityUom) {
        this.totalQuantityUom = totalQuantityUom;
    }

    public BigDecimal getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public BigDecimal getRepeatedQty() {
        return repeatedQty;
    }

    public void setRepeatedQty(BigDecimal repeatedQty) {
        this.repeatedQty = repeatedQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    // Updated getter and setter for createdBy
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    // Updated getter and setter for lastUpdatedBy
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}