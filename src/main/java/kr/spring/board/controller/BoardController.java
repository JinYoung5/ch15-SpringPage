package kr.spring.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;

	/*=================================
	 *	게시판 글 등록 
	 *=================================*/
	//자바빈(VO) 초기화
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	
	//등록 폼 호출
	@GetMapping("/board/write")	

	public String form() {
		return "boardWrite";		//타일스 설정
	}
	
	//전송된 데이터 처리
	@PostMapping("/board/write")
																			//컨텍스트 경로 필요하기에 httpservlet 필요, 세션에 저장
	public String submit(@Valid BoardVO boardVO, BindingResult result, HttpServletRequest request, HttpSession session, Model model
			) throws IllegalStateException, IOException {
		log.debug("<<게시판 글 저장>> : " + boardVO);

		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}

		//회원 번호 세팅
		MemberVO vo = (MemberVO)session.getAttribute("user");
		boardVO.setMem_num(vo.getMem_num());
		//ip세팅
		boardVO.setIp(request.getRemoteAddr());
		//파일 업로드
		boardVO.setFilename(FileUtil.createFile(request, boardVO.getUpload()));
		//글쓰기
		boardService.insertBoard(boardVO);

		//뷰에 표시할 메시지
		model.addAttribute("message", "글쓰기가 완료되었습니다.");
		model.addAttribute("url", request.getContextPath()+"/board/list");

		return "common/resultAlert";
	}

	/*=================================
	 *	게시판 글 목록 
	 *=================================*/
	@RequestMapping("/board/list")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue="1") int currentPage, @RequestParam(value="order",defaultValue="1") int order, String keyfield, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수
		int count = boardService.selectRowCount(map);
		log.debug("<<count>> : " + count);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield, keyword, currentPage, count, 20,10,"list");
		
		List<BoardVO> list = null;
		if(count > 0) {
			map.put("order", order);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = boardService.selectList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("page", page.getPage());

		return mav; 

	}
}
