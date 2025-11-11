package com.br.miguelalves.tech_events_api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.miguelalves.tech_events_api.domain.coupon.Coupon;
import com.br.miguelalves.tech_events_api.domain.coupon.CouponRequestDTO;
import com.br.miguelalves.tech_events_api.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/event/{eventId}}")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable UUID eventId,
            @RequestBody CouponRequestDTO data) {
        Coupon coupon = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
