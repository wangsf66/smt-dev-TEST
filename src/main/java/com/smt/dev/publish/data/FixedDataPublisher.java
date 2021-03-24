package com.smt.dev.publish.data;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.spring.eureka.cloud.feign.APIServer;
import com.ibs.spring.eureka.cloud.feign.RestTemplateWrapper;
import com.smt.dev.publish.util.FixedResourceInfo;

/**
 * 固定数据发布器
 * @author DougLei
 */
@RestController
@RequestMapping("/fixed_data")
public class FixedDataPublisher extends FixedResourceInfo{
	
	@Autowired
	private RestTemplateWrapper restTemplate;
	
	@Autowired
	private FixedDataQueryService queryService;
	
	/**
	 * 发布数据
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public Response publish(HttpServletRequest request) {
		String tablenames = request.getParameter("tablenames");
		String[] fixedTablenames = null;
		if("all".equalsIgnoreCase(tablenames))
			fixedTablenames = resourceNames.split(",");
		else
			fixedTablenames = tablenames.split(",");
		
		for (String tablename : fixedTablenames) {
			if(isFixedResource(tablename))
				callInsertData(tablename + suffix, queryService.query(tablename));
		}
		ResponseContext.addData(tablenames);
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 让smt-imp服务插入刚刚查询出来的数据
	 * @param paths
	 */
	private void callInsertData(String tablename, List<Map<String, Object>> datas) {
		if(datas.isEmpty())
			return;
		
		restTemplate.exchange(new APIServer() {
			
			@Override
			public String getName() {
				return "插入固定数据API";
			}
			
			@Override
			public String getUrl() {
				return "http://smt-imp/fixed_data/insert/" + tablename;
			}

			@Override
			public HttpMethod getRequestMethod() {
				return HttpMethod.POST;
			}
			
		}, JSONObject.toJSONString(datas), null);
	}
}
