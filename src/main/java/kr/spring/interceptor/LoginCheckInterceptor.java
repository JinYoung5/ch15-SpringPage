package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그처리
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		log.debug("<<LoginCheckInterceptor 진입>>");
		
		HttpSession session = request.getSession();
		//로그인 여부 검사
		if(session.getAttribute("user")==null) {
			//로그인이 되지 않은 상태
			response.sendRedirect(request.getContextPath()+"/member/login");
			
			return false;	//요청한 페이지가 호출되지 않음
		}
		
		return true;	//if문으로 들어가지않으면 요청한 페이지 호출
	}
}
