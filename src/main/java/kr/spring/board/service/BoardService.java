package kr.spring.board.service;

import java.util.List;
import java.util.Map;

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
	//좋아요
	//댓글
}
