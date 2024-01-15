package kr.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	/*=================================
	 *	게시판 글 목록 
	 *=================================*/
	@RequestMapping("/board/list")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue="1") int currentPage, String keyfield, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelAndView mav = new ModelAndView();
		
		return mav; 
	}
}
