package hhplus.serverjava.domain.seat.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hhplus.serverjava.domain.seat.entity.Seat;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
	// @checkstyle:off
	@Query("select s from Seat s where s.concertOption.concert.id = :concertId " +
		"and s.concertOption.concertAt = :targetDate " +
		"and s.status = :state")
	List<Seat> findAvailableSeats(@Param("concertId") Long concertId, @Param("targetDate") LocalDateTime targetDate,
		@Param("state") Seat.State state);

	@Query("select s from Seat s where s.concertOption.id = :concertOptionId " +
		"and s.concertOption.concertAt = :targetDate " +
		"and s.status = :state " +
		"and s.seatNum = :seatNum")
	Optional<Seat> findAvailableSeat(@Param("concertOptionId") Long concertOptionId,
		@Param("targetDate") LocalDateTime targetDate,
		@Param("state") Seat.State state, @Param("seatNum") int seatNum);
}
