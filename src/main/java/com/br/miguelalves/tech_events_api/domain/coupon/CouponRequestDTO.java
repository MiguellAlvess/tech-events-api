package com.br.miguelalves.tech_events_api.domain.coupon;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
