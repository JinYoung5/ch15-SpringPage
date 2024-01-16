package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter										//lombok에서 자동으로 getter, setter, ToString을 처리해줌
@Setter
//ToString이 재정의 될 때 photo의 값을 반환하지 않음
@ToString(exclude = {"photo"})				//byte[] 배열은 ToString 해주면 나중에 출력할때 byte를 모두 출력해서 처리속도가 느려짐 exclude처리해야됨
public class MemberVO {
	private int mem_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String id;
	private String nick_name;
	private int auth;
	private String auto;
	private String au_id;
	@NotBlank
	private String name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String passwd;
	@NotBlank
	private String phone;
	@Email
	@NotBlank
	private String email;
	@Size(min=5,max=5)
	private String zipcode;
	@NotBlank
	private String address1;
	@NotEmpty
	private String address2;
	private byte[] photo;					//MyBatis 에서는 multipartfile 로 명시 안됨. byte[] 배열로 처리  table에 저장할 때 byte형식
	private String Photo_name;
	private Date reg_date;
	private Date modify_date;
	private String now_passwd;
	
	/* =======================
	 *	비밀번호 일치 여부 체크
	 * ======================= */
	public boolean isCheckedPassword(String userPasswd) {
		if(auth > 1 && passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
	/* =======================
	 *	이미지 BLOB 처리
	 * ======================= */
	//(주의)폼에서 파일업로드 파라미터 네임은 반드시 upload로 지정해야 함
	public void setUpload(MultipartFile upload) throws IOException {
		//MultipartFile -> byte[]
		setPhoto(upload.getBytes());
		//파일명 지정
		setPhoto_name(upload.getOriginalFilename());
	}
	
}
