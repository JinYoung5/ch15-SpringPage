package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.board.dao.BoardMapper;
import kr.spring.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<BoardVO> selectList(Map<String, Object> map) {
		return boardMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return boardMapper.selectRowCount(map);
	}

	@Override
	public void insertBoard(BoardVO board) {
		boardMapper.insertBoard(board);
	}

	@Override
	public BoardVO selectBoard(int board_num) {
		return boardMapper.selectBoard(board_num);
	}

	@Override
	public void updateHit(int board_num) {
		boardMapper.updateHit(board_num);
	}

	@Override
	public void updateBoard(BoardVO board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBoard(int board_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFile(int board_num) {
		boardMapper.deleteFile(board_num);
	}

}
