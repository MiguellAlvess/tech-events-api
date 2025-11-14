
package com.br.miguelalves.tech_events_api.repositories;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.miguelalves.tech_events_api.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
       List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
}
