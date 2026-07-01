package kr.hhplus.be.server.payment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.server.payment.infrastructure.entity.PaymentEntity;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
