package kr.spring.talk.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TalkRoomVO {
	private int talkroom_num;
	private String basic_name;
	private String talkroom_date;
	
	private int[] members;	//채팅멤버
	private int mem_num;
	private int room_cnt;	//읽지 않은 메세지수
	
	private TalkVO talkVO;
	private TalkMemberVO talkMemberVO;
}

