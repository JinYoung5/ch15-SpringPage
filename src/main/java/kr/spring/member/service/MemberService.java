package kr.spring.member.service;


import java.util.List;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	//회원관리 - 사용자
	public void insertMember(MemberVO member);			//detail,mem_num,vo 를 트랜잭션으로 한번에 처리
	public MemberVO selectCheckMember(String id);
	public MemberVO selelctMember(int mem_num);			//한 건의 데이터 불러오기
	public void updateMember(MemberVO member);
	public void updatePassword(MemberVO member);
	public void deleteMember(int mem_num);
	//자동 로그인
	public void updateAu_id(String au_id, int mem_num);
	public void selectAu_id(String au_id);
	public void deleteAu_id(int mem_num);
	//프로필 이미지 업데이트
	public void updateProfile(MemberVO member);
	
	//채팅 회원이름 검색
	public List<MemberVO> selectSearchMember(String id);
	
	
	//회원관리 - 관리자
}


//Service에서 트랜잭션 처리