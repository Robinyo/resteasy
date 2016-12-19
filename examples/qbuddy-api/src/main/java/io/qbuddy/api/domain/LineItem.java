package io.qbuddy.api.domain;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "productId", "productName" })
@ToString(of = "productId")
// @Slf4J
public class LineItem {

    private final String productId;
    private final String productName;
    private final MonetaryAmount productPrice;
    private Long quantity;
    private final BigDecimal taxRate;
    private String state;

}

// @ToString(callSuper = true, exclude = "taxRate")

// public enum state { Open, Ready, Complete }
// static final String STATE_OPEN = "Open";
// static final String STATE_READY = "Ready";
// static final String STATE_COMPLETE = "Complete";