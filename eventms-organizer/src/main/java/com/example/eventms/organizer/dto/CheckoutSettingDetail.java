package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
@Getter
@Setter
public class CheckoutSettingDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String countryCode;

    private String currencyCode;

    @Schema(description = "0->Default; 1->Offline; 2->Payments system")
    private Integer checkoutMethod;

    @Schema(description = "Left empty if checkout method is not offline")
    private String offlineNote;

    private LocalDateTime created;

    private LocalDateTime changed;
}
