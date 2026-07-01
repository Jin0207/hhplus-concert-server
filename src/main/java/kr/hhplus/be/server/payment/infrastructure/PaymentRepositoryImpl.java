package kr.hhplus.be.server.payment.infrastructure;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.payment.domain.model.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import kr.hhplus.be.server.payment.infrastructure.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        return jpaRepository.save(PaymentEntity.from(payment)).toModel();
    }
}
