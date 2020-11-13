package com.study.springboot.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.study.springboot.jpa.vo.Member;

@SpringBootApplication
public class StartApplication {

	public static void main(String[] args) {
	    Member member = new Member();
	    member.setAge(10);
	    member.setName("MemberA");
	    
	    System.out.println(member.getName());
	    
		SpringApplication.run(StartApplication.class, args);
	}

}
