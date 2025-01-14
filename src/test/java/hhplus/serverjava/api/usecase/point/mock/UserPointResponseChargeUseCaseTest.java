package hhplus.serverjava.api.usecase.point.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hhplus.serverjava.api.user.response.UserPointResponse;
import hhplus.serverjava.api.user.usecase.UserPointChargeUseCase;
import hhplus.serverjava.domain.user.componenets.UserReader;
import hhplus.serverjava.domain.user.componenets.UserStore;
import hhplus.serverjava.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserPointResponseChargeUseCaseTest {

	@Mock
	UserStore userStore;
	@Mock
	UserReader userReader;

	@InjectMocks
	UserPointChargeUseCase userPointChargeUseCase;

	@DisplayName("포인트 충전 테스트")
	@Test
	void test() {
		//given
		Long userId = 1L;
		Long point = 200L;
		User user = new User(userId, point);

		when(userReader.findUser(userId)).thenReturn(user);

		//when
		UserPointResponse result = userPointChargeUseCase.charge(userId, 1000L);

		//then
		assertNotNull(result);
		assertEquals(result.getPoint(), point + 1000L);
	}
}
