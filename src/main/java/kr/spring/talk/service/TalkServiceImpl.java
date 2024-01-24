package kr.spring.talk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.talk.dao.TalkMapper;
import kr.spring.talk.vo.TalkMemberVO;
import kr.spring.talk.vo.TalkRoomVO;
@Service
@Transactional
public class TalkServiceImpl implements TalkService{
	@Autowired
	private TalkMapper talkMapper;
	
	@Override
	public List<TalkRoomVO> selectTalkRoomList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertTalkRoom(TalkRoomVO talkRoomVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TalkMemberVO> selectTalkMember(int talkroom_num) {
		// TODO Auto-generated method stub
		return null;
	}

}
