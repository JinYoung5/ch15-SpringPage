package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardVO;

@Mapper
public interface BoardMapper {
	//부모글
	public List<BoardVO> selectList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	public void insertBoard(BoardVO board);
	public BoardVO selectBoard(int board_num);
	@Update("UPDATE spboard SET hit=hit+1 WHERE board_num=#{board_num}")
	public void updateHit(int board_num);
	public void updateBoard(BoardVO board);
	public void deleteBoard(int board_num);
									//filename='' 비어있게하면 파일지움
	@Update("UPDATE spboard SET filename='' WHERE board_num=#{board_num}")
	public void deleteFile(int board_num);
	//좋아요
	//댓글
	
}
