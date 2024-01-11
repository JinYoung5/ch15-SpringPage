package kr.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j	//코드를 줄여줌
@Controller
public class MemberController {
	//MemberService를 주입받아야됨
	@Autowired
	private MemberService memberService;
	
	/*	=====================
	 * 	자바빈 초기화(커스텀 태그를 사용하기위해)
	 *	=====================*/
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	/*	=====================
	 * 	회원 가입
	 *	=====================*/
	//회원가입 폼 호출
	@GetMapping("/member/registerUser")
	public String form() {
		return "memberRegister";	//경로가 없으면 : 타일스 설정명 , 경로가 있으면 jsp호출
	}
}
