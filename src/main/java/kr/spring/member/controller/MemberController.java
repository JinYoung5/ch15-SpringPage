package kr.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	//전송된 회원 데이터 처리
	@PostMapping("/member/registerUser")
	public String submit(@Valid MemberVO memberVO,BindingResult result, Model model, HttpServletRequest request) {
		log.debug("<<회원가입>> : " + memberVO);
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원가입
		memberService.insertMember(memberVO);
		
		model.addAttribute("accessTitle","회원가입");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다.");
											//Context를 불러와야됨
		model.addAttribute("accessUrl", request.getContextPath()+"/main/main");
		
		return "common/resultView";
	}
}
