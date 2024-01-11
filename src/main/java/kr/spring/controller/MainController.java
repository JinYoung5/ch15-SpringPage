package kr.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j												//lombok 사용시 Slf4j 어노테이션 사용가능함. lombok기능을 이용해서 로그기능 지정
@Controller
public class MainController {
	@RequestMapping("/")
	public String init(HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		//관리자로 로그인하면 관리자 메인으로 이동 처리
		if(user != null && user.getAuth() == 9) {
			return "redirect:/main/admin";
		}
		
		return "redirect:/main/main";
	}
	
	
	@RequestMapping("/main/main")
	public String main(Model model) {				//Model을 사용해서 request에 데이터 저장하기위해 Model 추가함
		
		log.debug("<<메인 실행>>");
		
		return "main";								//Tiles 설정명
	}
}
