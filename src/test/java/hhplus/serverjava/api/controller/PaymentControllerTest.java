package hhplus.serverjava.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.serverjava.api.payment.request.PostPayRequest;
import hhplus.serverjava.api.util.jwt.JwtService;
import hhplus.serverjava.domain.reservation.components.ReservationStore;
import hhplus.serverjava.domain.reservation.entity.Reservation;
import hhplus.serverjava.domain.user.componenets.UserStore;
import hhplus.serverjava.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStore userStore;

    @Autowired
    private ReservationStore reservationStore;

    @Autowired
    private JwtService jwtService;

    @DisplayName("결제 테스트 (결제 성공)")
    @Test
    void paymentTest() throws Exception {
        //given
        User user = User.builder()
                .name("testUser")
                .point(5000000L)
                .updatedAt(LocalDateTime.now())
                .build();
        userStore.save(user);

        String jwt = jwtService.createJwt(user.getId());

        Reservation reservation = Reservation.builder()
                .seatNum(10)
                .reservedPrice(5000)
                .user(user)
                .concertAt(LocalDateTime.now().plusDays(5))
                .concertName("IU")
                .concertArtist("IUUUUUU")
                .build();
        reservationStore.save(reservation);

        PostPayRequest request = new PostPayRequest(reservation.getId(), reservation.getReservedPrice());

        mockMvc.perform(post("/api/payment")
                        .header("Authorization", jwt)   // 토큰 추가
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true)) // isSuccess
                .andExpect(jsonPath("$.code").value(200))       // code
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다.")) // successMessage
                .andExpect(jsonPath("$.result.reservationId").value(reservation.getId()))   // 예약 ID 검증
                .andExpect(jsonPath("$.result.payAmount").value(reservation.getReservedPrice()));   // 예약 금액 검증
    }

    @DisplayName("결제 테스트 (결제 실패 - 잔액 부족)")
    @Test
    void paymentFailTest() throws Exception{
        //given
        User user = User.builder()
                .name("testUser")
                .point(50L)
                .updatedAt(LocalDateTime.now())
                .build();
        userStore.save(user);

        String jwt = jwtService.createJwt(user.getId());

        Reservation reservation = Reservation.builder()
                .seatNum(10)
                .reservedPrice(5000)
                .user(user)
                .concertAt(LocalDateTime.now().plusDays(5))
                .concertName("IU")
                .concertArtist("IUUUUUU")
                .build();
        reservationStore.save(reservation);

        PostPayRequest request = new PostPayRequest(reservation.getId(), reservation.getReservedPrice());

        mockMvc.perform(post("/api/payment")
                        .header("Authorization", jwt)   // 토큰 추가
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(false))        // isSuccess
                .andExpect(jsonPath("$.code").value(404))               // code
                .andExpect(jsonPath("$.message").value("포인트가 부족합니다.")); // failMessage

    }
}
