package hhplus.serverjava.api.usecase.payment;

import hhplus.serverjava.api.dto.response.payment.PostPayRes;
import hhplus.serverjava.domain.payment.components.PaymentStore;
import hhplus.serverjava.domain.payment.entity.Payment;
import hhplus.serverjava.domain.reservation.components.ReservationReader;
import hhplus.serverjava.domain.reservation.entity.Reservation;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Server
@Transactional
@RequiredArgsConstructor
public class PaymentUseCase {

    private final PaymentStore paymentStore;
    private final ReservationReader reservationReader;

    public PostPayRes execute(Long reservationId) {
        Reservation reservation = findReservation(reservationId);

        Payment payment = Payment.builder()
                .reservation(reservation)
                .payAmount((long) reservation.getReservedPrice())
                .build();

        return new PostPayRes();
    }

    private Reservation findReservation(Long reservationId) {
        return reservationReader.findReservation(reservationId);
    }
}
