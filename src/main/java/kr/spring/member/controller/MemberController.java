package kr.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;
import kr.spring.util.FileUtil;
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
	/*	=====================
	 * 	회원 로그인
	 *	=====================*/
	//로그인 폼 호출
	@GetMapping("/member/login")
	public String formLogin() {
		return "memberLogin";
	}
	//전송된 데이터 처리
	@PostMapping("/member/login")
	public String submit(@Valid MemberVO memberVO, BindingResult result, HttpSession session, HttpServletResponse response) {
		log.debug("<<회원로그인>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		//id와 passwd 필드만 유효성 체크
		if(result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formLogin();
		}
		
		//로그인 체크(id,비밀번호 일치 여부 체크)
		MemberVO member = null;
		try {
			member = memberService.selectCheckMember(memberVO.getId());
			boolean check = false;
			
			if(member != null) {
				//비밀번호 일치 여부 체크
				check = member.isCheckedPassword(memberVO.getPasswd());
			}
			if(check) { //인증 정송
				//====자동로그인 체크 시작====//
				
				
				//====자동로그인 체크 끝====//
				
				//인증 성공, 로그인 처리
				session.setAttribute("user", member);		//저장해야 될 데이터가 많으면 자바빈 처리
				log.debug("<<인증 성공>>");
				log.debug("<<id>> : " + member.getId());
				log.debug("<<mem_num>> : " + member.getMem_num());
				log.debug("<<auth>> : " + member.getAuth());
				log.debug("<<au_id>> : " + member.getAu_id());
				
				if(member.getAuth()==9) { //관리자는 관리자 메인으로 이동
					return "redirect:/main/admin";
				}else {	//일반 사용자는 사용자 메인으로 이동
					return "redirect:/main/main";
				}
			}
			//인증 실패
			throw new AuthCheckException();
			
		}catch(AuthCheckException e){
			log.debug("<<인증실패>>");
			//인증 실패로 로그인 폼 호출
			if(member != null && member.getAuth()==1) {	//정지회원 메시지 표시
				result.reject("noAuthority");
			}else {
				result.reject("invalidIdOrPassword");
			}
			return formLogin();
		}
	}
	/*	=====================
	 * 	로 그 아 웃
	 *	=====================*/
	@RequestMapping("/member/logout")
	public String processLogout(HttpSession session, HttpServletResponse response) {
		//로그아웃
		session.invalidate();
		//=========자동 로그인 처리=========//

		
		//=========자동 로그인 처리=========//
		
		return "redirect:/main/main";
	}
	/*	=====================
	 * 	M yP  a g e
	 *	=====================*/
	@RequestMapping("/member/myPage")
	public String process(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<mem_num>>");
		//회원 정보
		MemberVO member = memberService.selelctMember(user.getMem_num());
		
		log.debug("<<회원 상세 정보>> : " + member);
		
		model.addAttribute("member",member);
		
		return "myPage";
	}
	
	/*	=====================
	 * 	프로필 사진 출력
	 *	=====================*/
	//프로필 사진 출력(로그인 전용)
	@RequestMapping("/member/photoView")
	public String getProfile(HttpSession session, HttpServletRequest request, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<프로필 사진 읽기>> : " + user);
		
		if(user == null ) {	//로그인이 되지 않은 경우
			getBasicProfileImage(request, model);
		}else {	//로그인 된 경우
			MemberVO memberVO = memberService.selelctMember(user.getMem_num());
			viewProfile(memberVO, request, model);
		}
		//빈의 이름이 imageView인 ImageView 객체 호출
		return "imageView";
	}
	//프로필 사진 출력(회원번호 지정)
	@RequestMapping("/member/viewProfile")
	public String getProfileByMem_num(@RequestParam int mem_num, HttpServletRequest request, Model model) {

		MemberVO memberVO = memberService.selelctMember(mem_num);
		
		viewProfile(memberVO, request, model);
		
		return "imageView";
	}
	//프로필 사진 처리를 위한 공통 코드
	public void viewProfile(MemberVO memberVO, HttpServletRequest request, Model model) {
		if(memberVO == null || memberVO.getPhoto_name() == null) {
			//업로드한 프로필 사진 정보가 없어서 기본 이미지 표시
			getBasicProfileImage(request, model);
		}else {	//업로드한 이미지 읽기
			model.addAttribute("imageFile", memberVO.getPhoto());
			model.addAttribute("filename", memberVO.getPhoto_name());
		}
	}
	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request, Model model) {
		//bytes 형식을 String으로 변환
		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/image_bundle/face.png"));
		model.addAttribute("imageFile", readbyte);
		model.addAttribute("filename", "face.png");
	}
}
