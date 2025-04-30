package com.bellaryinfotech.bulk.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class OrderFabricationDetailsDTO {
    private Long id;
    private String buildingName;
    private String drawingNo;
    private String drawingDescription;
    private String orderNumber;
    private Long orderId;
    private Long origLineNumber;
    private Long origLineId;
    private Long lineNumber;
    private Long lineId;
    private String erectionMkd;
    private String itemNo;
    private String section;
    private BigDecimal length;
    private String lengthUom;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String unitPriceUom;
    private BigDecimal totalQuantity;
    private String totalQuantityUom;
    private BigDecimal originalQuantity;
    private BigDecimal repeatedQty;
    private String remark;
    private Integer tenantId;
    private Date creationDate;
    // Changed from Long to Integer
    private Integer createdBy;
    private Date lastUpdateDate;
    // Changed from Long to Integer
    private Integer lastUpdatedBy;
    private Long orgId;
    
    // Default constructor
    public OrderFabricationDetailsDTO() {
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