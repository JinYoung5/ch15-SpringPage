package kr.spring.board.service;

import java.util.List;
import java.util.Map;


import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

public interface BoardService {
	//부모글
	public List<BoardVO> selectList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	public void insertBoard(BoardVO board);
	public BoardVO selectBoard(int board_num);
	public void updateHit(int board_num);
	public void updateBoard(BoardVO board);
	public void deleteBoard(int board_num);
	public void deleteFile(int board_num);
	//좋아요
	public BoardFavVO selectFav(BoardFavVO fav);				//토글형태라 selectFav,selectFavcount 부분 같이
	public int selectFavCount(int board_num);					//목록에도 좋아요 수 나타내기위해 JOIN ->xml에 명시했으니 xml로 이동
	public void insertFav(BoardFavVO fav);						//insertFav,deleteFav 작업 같이
	public void deleteFav(BoardFavVO boardFav);
	//댓글
	public List<BoardReplyVO> selectListReply(Map<String, Object> map);
	public int selectRowCountReply(Map<String, Object> map);
	public BoardReplyVO selectReply(int re_num);						//한건의 데이터 불러오기
	public void insertReply(BoardReplyVO boardReply);					//등록
	public void updateReply(BoardReplyVO boardReply);
	public void deleteReply(int re_num);
}
