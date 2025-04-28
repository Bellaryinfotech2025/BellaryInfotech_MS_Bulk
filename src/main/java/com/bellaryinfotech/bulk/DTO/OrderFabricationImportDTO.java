package com.bellaryinfotech.bulk.DTO;

 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFabricationImportDTO {
    private Long ifaceId;
    private String ifaceStatus;
    private String errorMessage;
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
    private Long tenantId;
    private Date creationDate;
    private Long createdBy;
    private Date lastUpdateDate;
    private Long lastUpdatedBy;
    private Long orgId;
}

