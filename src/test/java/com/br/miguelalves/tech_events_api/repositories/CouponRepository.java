
package com.br.miguelalves.tech_events_api.repositories;
import com.br.miguelalves.tech_events_api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {}
