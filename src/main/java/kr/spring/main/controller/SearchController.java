package kr.spring.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SearchController {
	@RequestMapping("/get.do")
	@ResponseBody
	public String get() throws IOException{
		
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")).build();
		
		Request request = new Request("GET", "/movie_search/_search?q=prdtYear:2019");
		Response response = restClient.performRequest(request);
		
		restClient.close();
		
		return EntityUtils.toString(response.getEntity());
	}
	
	@RequestMapping("/put.do")
	@ResponseBody
	public String put() throws IOException{
		RestClient restClient = RestClient.builder(new HttpHost("localhost",9200,"http"), new HttpHost("localhost", 9201, "http")).build();
		
		Request request = new Request("put", "/movie_execute/_doc/1");
		
		Map<String, String>map = new HashMap<String, String>();
		map.put("message", "elastic");
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(map);
		
		request.setJsonEntity(body);
		
		Response response = restClient.performRequest(request);
		
		restClient.close();
		
		return EntityUtils.toString(response.getEntity());
	}
	
	@RequestMapping("/post.do")
	@ResponseBody
	public String post() throws IOException{
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")).build();
		
		Request request = new Request("POST", "/movie2/_doc/1");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("movieCd", "3");
		map.put("movieNm","극한직업");
		map.put("movieNmEn","Extreme job");
		map.put("prdtYear","2019");
		map.put("openDt","2019-01-23");
		map.put("typeNm","장편");
		map.put("prdtStatNm", "기타");
		map.put("nationAlt", "한국");
		map.put("genreAlt", "코미디");
		map.put("repNationNm", "한국");
		map.put("repGenreNm", "코미디");
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(map);
		
		request.setJsonEntity(body);
		
		Response response = restClient.performRequest(request);
		
		restClient.close();
		
		return EntityUtils.toString(response.getEntity());
	}
	@RequestMapping("/post2.do")
	@ResponseBody
	public String post2() throws IOException {
		
		RestClient restClient = RestClient.builder(
			    new HttpHost("localhost", 9200, "http"),
			    new HttpHost("localhost", 9201, "http")).build();
		Request request = new Request("POST", "/movie_search/_search");
		
		String body = "{"
				    + "  \"query\":{"
				    + "    \"match\": {"
				    + "      \"movieNm\": \"극한직업\""
				    + "    }"
				    + "  }"
				    + "}";
		
		request.setJsonEntity(body);
		
		Response response = restClient.performRequest(request);

		restClient.close();
		
		return EntityUtils.toString(response.getEntity());
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete() throws IOException {
		
		RestClient restClient = RestClient.builder(
			    new HttpHost("localhost", 9200, "http"),
			    new HttpHost("localhost", 9201, "http")).build();
		
		Request request = new Request("delete", "/movie_execute/_doc/1");
		
		Response response = restClient.performRequest(request);

		restClient.close();
		
		return EntityUtils.toString(response.getEntity());
	}
}
