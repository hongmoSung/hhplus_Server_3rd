package hhplus.serverjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerJavaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerJavaApplication.class, args);

		// 메모리 사용량 출력
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : " + heapSize / (1024 * 1024) + " MB");
	}
}


