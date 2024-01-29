package kr.spring.talk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.talk.dao.TalkMapper;
import kr.spring.talk.vo.TalkMemberVO;
import kr.spring.talk.vo.TalkRoomVO;
import kr.spring.talk.vo.TalkVO;
@Service
@Transactional
public class TalkServiceImpl implements TalkService{
	@Autowired
	private TalkMapper talkMapper;
	
	@Override
	public List<TalkRoomVO> selectTalkRoomList(Map<String, Object> map) {
		return talkMapper.selectTalkRoomList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return talkMapper.selectRowCount(map);
	}

	@Override
	public void insertTalkRoom(TalkRoomVO talkRoomVO) {
		//기본키 생성
		talkRoomVO.setTalkroom_num(talkMapper.selectTalkRoomNum());
		//채팅방 생성
		talkMapper.insertTalkRoom(talkRoomVO);
		
		//입장 메세지 처리
		talkRoomVO.getTalkVO().setTalk_num(talkMapper.selectTalkNum());
		talkRoomVO.getTalkVO().setTalkroom_num(talkRoomVO.getTalkroom_num());
		talkMapper.insertTalk(talkRoomVO.getTalkVO());
		
		//채팅방 멤버 생성
		for(int mem_num : talkRoomVO.getMembers()) {
			talkMapper.insertTalkRoomMember(talkRoomVO.getTalkroom_num(), talkRoomVO.getBasic_name(), mem_num);
		}
	}

	@Override
	public List<TalkMemberVO> selectTalkMember(int talkroom_num) {
		return talkMapper.selectTalkMember(talkroom_num);
	}

	@Override
	public void insertTalk(TalkVO talkVO) {
		talkVO.setTalk_num(talkMapper.selectTalkNum());
		talkMapper.insertTalk(talkVO);
		//채팅 멤버가 읽지 않은 채팅 정보 저장
		for(TalkMemberVO vo : talkMapper.selectTalkMember(talkVO.getTalkroom_num())) {
			talkMapper.insertTalkRead(talkVO.getTalkroom_num(), talkVO.getTalk_num(), vo.getMem_num());
		}
	}

	@Override
	public List<TalkVO> selectTalkDetail(Map<String, Integer> map) {
		//읽은 채팅 기록 삭제
		talkMapper.deleteTalkRead(map);
		return talkMapper.selectTalkDetail(map);
	}

}
