package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ComponentScan 어노테이션 - 기본생성자가 있는 컴포넌트(클래스)를 호출 (순서에 상관없이 모두 불러옴)
public class Mission3Application {

	public static void main(String[] args) {
		SpringApplication.run(Mission3Application.class, args);
	}

}
