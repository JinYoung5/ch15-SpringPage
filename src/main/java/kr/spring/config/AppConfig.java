package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import kr.spring.interceptor.LoginCheckInterceptor;

//자바코드 기반 설정 클래스
@Configuration
public class AppConfig implements WebMvcConfigurer{
	//생성한다음 가져다가 쓸 수 있게 변수 생성
	private LoginCheckInterceptor loginCheck;
	
	@Bean
	public LoginCheckInterceptor interceptor2() {
		loginCheck = new LoginCheckInterceptor();
		
		return loginCheck;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//LoginCheckInterceptor 설정		Controller에 설정해야될 거를 appconfig에 명시해서 편리하게함(로그인했는지 안했는지 체크)
		registry.addInterceptor(loginCheck)
				.addPathPatterns("/member/myPage")
				.addPathPatterns("/board/write")
				.addPathPatterns("/board/update")
				.addPathPatterns("/board/delete")
				.addPathPatterns("/talk/talkRoomWirte")
				.addPathPatterns("/talk/talkList")
				.addPathPatterns("/talk/talkDetail");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		//tilesdef.xml의 경로와 파일명 지정				자바코드 기초 설정
		configurer.setDefinitions(
				new String[] {
						"/WEB-INF/tiles-def/main.xml",
						"/WEB-INF/tiles-def/member.xml",
						"/WEB-INF/tiles-def/board.xml",
						"/WEB-INF/tiles-def/talk.xml"
						});	//여러개 만들수있어서 String 배열형태로 지정
		configurer.setCheckRefresh(true);
		
		return configurer;
	}
	@Bean		//반환한 객체를 컨테이너에 등록 어노테이션
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		
		return tilesViewResolver;
	}
}

//include 해서 조합된 페이지를 만듦. tilesViewResolver : 요청해서 낚아채줌,  TilesView : 조합된 페이지를 처리해주는형태