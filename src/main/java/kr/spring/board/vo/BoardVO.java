package kr.spring.board.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private int board_num;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private int hit;
	private Date reg_date;
	private Date modify_date;
	private String ip;
	private int mem_num;
	
	private String id;
	private String nick_name;
	
	private int re_cnt;		//댓글 개수
	private int fav_cnt;	//좋아요 개수
	
}
