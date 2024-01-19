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
import kr.spring.board.vo.BoardFavVO;
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
}
