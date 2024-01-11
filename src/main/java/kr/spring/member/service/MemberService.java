package kr.spring.member.service;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	//회원관리 - 사용자
	public void insertMember(MemberVO member);			//detail,mem_num,vo 를 트랜잭션으로 한번에 처리
	public MemberVO selectCheckMember(String id);
	public MemberVO selelctMember(int mem_num);			//한 건의 데이터 불러오기
	public void updateMember(MemberVO member);
	public void updatePassword(MemberVO member);
	public void deleteMember(int mem_num);
	//회원관리 - 관리자
}


//Service에서 트랜잭션 처리