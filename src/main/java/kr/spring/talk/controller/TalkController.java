package kr.spring.talk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.talk.service.TalkService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TalkController {
	@Autowired
	private TalkService talkService;
	@Autowired
	private MemberService memberService;
	
	/*=======================
	 * 채팅방 목록
	 *=======================*/
	@RequestMapping("/talk/talkList")
	public String chatList(@RequestParam(value="pageNum",defaultValue="1") int currentPage, String keyword, HttpSession session, Model model) {
		
		return "talkList";
	}
	
	
	/*=======================
	 * 채팅방 생성
	 *=======================*/
	//채팅방 생성 폼 호출
	@GetMapping("/talk/talkRoomWrite")
	public String talkRoomWrite() {
		
		return "talkRoomWrite";
	}
	//전송된 데이터 처리
	
	//채팅 회원 검색
	@RequestMapping("/talk/memberSearchAjax")
	@ResponseBody
	public Map<String, Object> memberSearchAjax(@RequestParam String id, HttpSession session){
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {	//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else {	//로그인 된 경우
			List<MemberVO> member = memberService.selectSearchMember(id);
			
			mapJson.put("result", "success");
			mapJson.put("member", member);
			
		}
		
		
		return mapJson;
	}
	
	/*=======================
	 * 채팅 메세지 처리
	 *=======================*/
}
