package kr.spring.member.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	//회원관리 - 사용자
	public int selectMem_num();
	public void insertMember(MemberVO member);
	public void insertMember_detail(MemberVO member);
	public MemberVO selectCheckMember(String id);
	public MemberVO selelctMember(int mem_num);			//한 건의 데이터 불러오기
	public void updateMember(MemberVO member);
	public void updateMember_detail(MemberVO member);
	public void updatePassword(MemberVO member);
	public void deleteMember(int mem_num);
	public void deleteMember_detail(int mem_num);
	//회원관리 - 관리자
}
