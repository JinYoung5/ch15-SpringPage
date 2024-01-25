package kr.spring.talk.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TalkVO {
	private int talk_num;
	private int talkroom_num;	//수신그룹
	private int mem_num;		//발신자
	private String message;
	private String chat_date;
	
	private int read_count;		//읽지 않은 메세지수
	private String id;
}
