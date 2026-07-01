package kr.hhplus.be.server.payment.application;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.payment.domain.model.Payment;
import kr.hhplus.be.server.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment create(Long reservationId, Long userId, long amount) {
        Payment payment = Payment.create(reservationId, userId, amount).success();
        return paymentRepository.save(payment);
    }
}
