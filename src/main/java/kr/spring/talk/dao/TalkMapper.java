package kr.spring.talk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.spring.talk.vo.TalkMemberVO;
import kr.spring.talk.vo.TalkRoomVO;

@Mapper
public interface TalkMapper {
	//채팅방목록
	public List<TalkRoomVO> selectTalkRoomList(Map<String, Object> map);
	public int selectRowCount(Map<String, Object> map);
	//채팅방 번호 생성
	public int selectTalkRoomNum();
	//채팅방 생성
	public void insertTalkRoom(TalkRoomVO talkRoomVO);
	//채팅방 멤버 등록
	public void insertTalkRoomMember(@Param(value="talkroom_num") int talkroom_num, @Param(value="room_name") String room_name, @Param(value="mem_num") int mem_num);
	//채팅 멤버 읽기
	public List<TalkMemberVO> selectTalkMember(int talkroom_num);
	
	//채팅 메세지 번호 생성
	//채팅 메세지 등록
	//읽지 않은 채팅 기록 저장
	//채팅 메세지 읽기
	//읽은 채팅 기록 삭제
}
