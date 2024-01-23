package kr.spring.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PageUtil;
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
	
	
	/*=======================
	 * 부모글 좋아요 읽기
	 *======================= */
	@RequestMapping("/board/getFav")
	@ResponseBody	//Json 형태로 해야되니 responsebody
	public Map<String, Object> getFav(BoardFavVO fav, HttpSession session){
		log.debug("<<게시판 좋아요 BoardFavVO>> : " + fav);
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			mapJson.put("status", "noFav");
		}else {
			//로그인된 회원번호 세팅
			fav.setMem_num(user.getMem_num());
			BoardFavVO boardFav = boardService.selectFav(fav);
			
			if(boardFav != null) {
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}
		}
		//좋아요수
		mapJson.put("count", boardService.selectFavCount(fav.getBoard_num()));
		
		return mapJson;
	}
	
	
	/*=======================
	 * 부모글 좋아요 등록/삭제
	 *======================= */
	@RequestMapping("/board/writeFav")
	@ResponseBody
	public Map<String, Object> writeFav(BoardFavVO fav, HttpSession session){
		log.debug("<<부모글 좋아요 등록/삭제 BoardFavVO>> : " + fav);
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			mapJson.put("result", "logout");
		}else {
			//로그인된 회원번호 세팅
			fav.setMem_num(user.getMem_num());
			
			//이전에 좋아요를 등록했는지 여부 확인
			BoardFavVO boardFavVO = boardService.selectFav(fav);
			if(boardFavVO != null) {	//좋아요를 이미 등록
				boardService.deleteFav(fav);	//좋아요 삭제
				mapJson.put("status", "noFav");
			}else{	//좋아요 미등록
				boardService.insertFav(fav); //좋아요 등록
				mapJson.put("status", "yesFav");
			}
			mapJson.put("result", "success");
			mapJson.put("count", boardService.selectFavCount(fav.getBoard_num()));
		}
		
		return mapJson;
	}
	
	
	/*=======================
	 * 댓글 등록
	 *======================= */
	@RequestMapping("/board/writeReply")
	@ResponseBody
	public Map<String, String> writeReply(BoardReplyVO boardReplyVO, HttpSession session, HttpServletRequest request){
		log.debug("<<댓글 등록 BroadReplyVO>> : " + boardReplyVO);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인 안 됨
			mapJson.put("result", "logout");
		}else {
			//회원번호 등록
			boardReplyVO.setMem_num(user.getMem_num());
			//ip등록
			boardReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 등록
			boardService.insertReply(boardReplyVO);
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
	
	
	/*=======================
	 * 댓글 등록
	 *======================= */
	@RequestMapping("/board/listReply")
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(value="pageNum",defaultValue="1") int currentPage, @RequestParam(value="rowCount",defaultValue="10") int rowCount, @RequestParam int board_num,HttpSession session){
		log.debug("<<댓글 목록 board_num>> : " + board_num);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_num", board_num);
		
		//전체 레코드수
		int count = boardService.selectRowCountReply(map);
		//페이지 처리
		PageUtil page = new PageUtil(currentPage, count, rowCount);
		List<BoardReplyVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = boardService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("count", count);
		mapJson.put("list", list);
		
		//로그인한 회원정보 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user != null) {
			mapJson.put("user_num", user.getMem_num());		//로그인한 회원번호가 필요해 넣어줌
		}
		
		return mapJson;
	}
	
	
	/*=======================
	 * 댓글 수정
	 *======================= */
	@RequestMapping("/board/updateReply")
	@ResponseBody
	public Map<String, String> modifyReply(BoardReplyVO boardReplyVO, HttpSession session, HttpServletRequest request){		//Json방식으로 해야되니 map<,>
		
		log.debug("<<댓글 수정 BoardReplyVO>> : " + boardReplyVO);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		BoardReplyVO db_reply = boardService.selectReply(boardReplyVO.getRe_num());
		if(user == null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(user != null && user.getMem_num() == db_reply.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 일치
			
			//ip등록
			boardReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 수정
			boardService.updateReply(boardReplyVO);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
	
	/*=======================
	 * 댓글 삭제
	 *======================= */
	@RequestMapping("/board/deleteReply")
	@ResponseBody			//map형태로 보내주면 json방식으로 자동 완성됨
	public Map<String, String> deleteReply(@RequestParam int re_num, HttpSession session){
		log.debug("<<댓글 삭제 re_num>> : " + re_num);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		BoardReplyVO db_reply = boardService.selectReply(re_num);
		if(user==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(user != null && user.getMem_num() == db_reply.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			boardService.deleteReply(re_num);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
}
