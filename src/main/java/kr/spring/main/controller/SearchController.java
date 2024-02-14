package kr.spring.main.controller;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
