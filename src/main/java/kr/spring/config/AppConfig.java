package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

//자바코드 기반 설정 클래스
@Configuration
public class AppConfig implements WebMvcConfigurer{
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		//tilesdef.xml의 경로와 파일명 지정				자바코드 기초 설정
		configurer.setDefinitions(new String[] {"/WEB-INF/tiles-def/main.xml"});	//여러개 만들수있어서 String 배열형태로 지정
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