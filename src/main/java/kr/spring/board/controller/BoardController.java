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
import kr.spring.util.StringUtil;
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
		
		//페이지 처리																		//keyfield,keyword와 같이 "&order=" + order 명시
		PageUtil page = new PageUtil(keyfield, keyword, currentPage, count, 20,10,"list","&order=" + order);
		
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
	
	/*=================================
	 *	게시판 글 상세 
	 *=================================*/
	@RequestMapping("/board/detail")
	public ModelAndView process(@RequestParam int board_num) {
		log.debug("<<게시판 글 상세 board_num>> : " + board_num);
		
		//해당 글의 조회수 증가
		boardService.updateHit(board_num);
		
		BoardVO board = boardService.selectBoard(board_num);
		//제목에 태그를 허용하지 않음
		board.setTitle(StringUtil.useNoHtml(board.getTitle()));
								//타일스 설정명,속성명,속성값
		return new ModelAndView("boardView","board",board);
	}
	
	/*=================================
	 *	파일 다운로드 
	 *=================================*/
	@RequestMapping("/board/file")
	public ModelAndView download(@RequestParam int board_num, HttpServletRequest request) {
		BoardVO board = boardService.selectBoard(board_num);
		
		//파일을 절대경로에서 읽어들여 byte[]로 변환			게시판에 사진이 많이 저장되면 느려질 수 있기에 특정 경로에 사진 저장되게 (upload ) 만듦
		byte[] downloadFile = FileUtil.getBytes(request.getServletContext().getRealPath("/upload") + "/" + board.getFilename());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("downloadView");
		mav.addObject("downloadFile", downloadFile);
		mav.addObject("filename", board.getFilename());
		
		return mav;
	}
	
	/*=================================
	 *	게시판 글 수정 
	 *=================================*/
	//수정 폼 호출
	@GetMapping("/board/update")
	public String formUpdate(@RequestParam int board_num, Model model) {
		BoardVO boardVO = boardService.selectBoard(board_num);
		
		model.addAttribute("boardVO",boardVO);
		
			//definition 타입 설정명
		return "boardModify";
	}
	
	//수정 폼에서 전송된 데이터 처리
	@PostMapping("/board/update")
	public String submitUpdate(@Valid BoardVO boardVO, BindingResult result, HttpServletRequest request, Model model) throws IllegalStateException, IOException {
		
		log.debug("<<글 수정>> : " + boardVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력 되지 않아 유효성 체크에 걸리면 파일 정보를 일어버리기 때문에(파일이 안보임) 폼을 호출할 때 다시 세팅 해주어야 함
			BoardVO vo = boardService.selectBoard(boardVO.getBoard_num());
			boardVO.setFilename(vo.getFilename());
			
			return "boardModify";
		}
		
		//DB에 저장된 파일 정보 구하기						이전 글을 가져오기위해 명시
		BoardVO db_board = boardService.selectBoard(boardVO.getBoard_num());
			
		//파일명 세팅						에러뜨기떄문에 마우스 올려서 add throws declaration 해야됨
		boardVO.setFilename(FileUtil.createFile(request, boardVO.getUpload()));
		//ip 세팅
		boardVO.setIp(request.getRemoteAddr());
		
		//글 수정
		boardService.updateBoard(boardVO);
		
		//전송된 파일이 있을 경우 이전 파일 삭제
		if(boardVO.getUpload() != null && !boardVO.getUpload().isEmpty()) {
			//수정된 파일 삭제 처리
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 수정 완료!!");
		model.addAttribute("url", request.getContextPath() + "/board/detail?board_num=" + boardVO.getBoard_num());
		
		return "common/resultAlert";
	}
	
	/*=================================
	 *	게시판 글 삭제 
	 *=================================*/
	@RequestMapping("/board/delete")
	public String submitDelete(@RequestParam int board_num, HttpServletRequest request) {
		log.debug("<<게시판 글 삭제 board_num>> : " + board_num);
		
		//DB에 저장된 파일 정보 구하기  -> 안해도 되긴하지만 쓰레기가 쌓이기 때문에 하는게 좋음
		BoardVO db_board = boardService.selectBoard(board_num);
		
		//글 삭제
		boardService.deleteBoard(board_num);
		
		if(db_board.getFilename() != null) {
			//파일 삭제
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		return "redirect:/board/list";
	}
}
