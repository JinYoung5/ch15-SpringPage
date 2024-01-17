package kr.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardAjaxController {
	@Autowired
	private BoardService boardService;

	/*=======================
	 * 부모글 업로드 파일 삭제
	 *======================= */
	@RequestMapping("/board/deleteFile")
	@ResponseBody
	public Map<String, String> processFile(int board_num, HttpSession session, HttpServletRequest request){
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			mapJson.put("result", "logout");
		}else {
			BoardVO vo = boardService.selectBoard(board_num);
			
			boardService.deleteFile(board_num);
			FileUtil.removeFile(request, vo.getFilename());
			
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
}
