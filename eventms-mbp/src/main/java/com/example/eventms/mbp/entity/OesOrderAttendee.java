package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 
 * </p>
 *
 * @author vicendy04
 * @since 2025-04
 */
@Getter
@Setter
@ToString
@TableName("oes_order_attendee")
@Schema(name = "OesOrderAttendee", description = "")
public class OesOrderAttendee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    @Schema(description = "Order serial number")
    private String orderSn;

    private Long ticketId;

    private String ticketName;

    private String ticketPic;

    private BigDecimal ticketPrice;

    private Integer ticketQuantity;

    private Long ticketStockId;

    @Schema(description = "Actual payment amount")
    private BigDecimal realAmount;

    @Schema(description = "Coupon discount amount")
    private BigDecimal couponAmount;

    @Schema(description = "Same information as Order owner")
    private String billReceiverEmail;

    @Schema(description = "Same information as Order owner")
    private String receiverName;

    private Integer refunded;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;
}
