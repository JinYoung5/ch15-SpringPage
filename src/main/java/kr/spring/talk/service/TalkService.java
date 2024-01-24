package kr.spring.talk.service;

import java.util.List;
import java.util.Map;


import kr.spring.talk.vo.TalkMemberVO;
import kr.spring.talk.vo.TalkRoomVO;

public interface TalkService {
	//채팅방목록
	public List<TalkRoomVO> selectTalkRoomList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	//채팅방 생성
	public void insertTalkRoom(TalkRoomVO talkRoomVO);
	//채팅 멤버 읽기
	public List<TalkMemberVO> selectTalkMember(int talkroom_num);
}
