package hhplus.serverjava.domain.reservation.infrastructure;

import hhplus.serverjava.api.util.exceptions.BaseException;
import hhplus.serverjava.domain.reservation.entity.Reservation;
import hhplus.serverjava.domain.reservation.repository.ReservationReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.serverjava.api.util.response.BaseResponseStatus.*;

@Repository
@RequiredArgsConstructor
public class ReservationCoreReaderRepository implements ReservationReaderRepository {

    private final ReservationJPARepository repository;

    @Override
    public Reservation findReservation(Long reservationId) {
        return repository.findById(reservationId)
                .orElseThrow(() -> new BaseException(INVALID_RESERVATION));
    }
    @Override
    public List<Reservation> findExpiredReservaions(LocalDateTime now) {
        return repository.findExpiredReservations(now);
    }
}
