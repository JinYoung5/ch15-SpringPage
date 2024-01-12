package kr.spring.member.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	//회원관리 - 사용자
	@Select("SELECT spmember_seq.nextval FROM dual")
	public int selectMem_num();
	@Insert("INSERT INTO spmember(mem_num,id,nick_name) VALUES (#{mem_num},#{id},#{nick_name})")
	public void insertMember(MemberVO member);
	public void insertMember_detail(MemberVO member);	//길어서 xml에 표기
	public MemberVO selectCheckMember(String id);		//길어서 xml에 표기
	public MemberVO selelctMember(int mem_num);			//한 건의 데이터 불러오기
	public void updateMember(MemberVO member);
	public void updateMember_detail(MemberVO member);
	public void updatePassword(MemberVO member);
	public void deleteMember(int mem_num);
	public void deleteMember_detail(int mem_num);
	//회원관리 - 관리자
}
