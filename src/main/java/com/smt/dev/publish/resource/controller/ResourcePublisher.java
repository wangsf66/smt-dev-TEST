package com.smt.dev.publish.resource.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.douglei.tools.file.FileUtil;
import com.douglei.tools.file.writer.FileBufferedWriter;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.spring.eureka.cloud.feign.APIServer;
import com.ibs.spring.eureka.cloud.feign.RestTemplateWrapper;
import com.smt.dev.publish.resource.entity.ResourceAdapter;
import com.smt.dev.publish.resource.service.ResourcePublishService;

/**
 * 资源发布器
 * @author DougLei
 */
@RestController
@RequestMapping("/resource")
public class ResourcePublisher {
	private static final Logger logger = LoggerFactory.getLogger(ResourcePublisher.class);
	private FixedResourceHelper helper = new FixedResourceHelper(); 
	
	@Autowired
	private RestTemplateWrapper restTemplate;
	
	@Autowired
	private ResourcePublishService service;
	
	/**
	 * 发布资源
	 * @return 
	 * @throws IOException 
	 */
//	@RequestMapping(value = "/publish", method = RequestMethod.GET)
//	public Response publish(HttpServletRequest request) throws IOException {
//		String resourceId = request.getParameter("resourceId");
//		return publish(resourceId, true);
//	}
	
	/**
	 * 发布资源
	 * @param resourceId 要发布的资源id
	 * @param instantLoad 是否要smt-imp服务即时加载发布的资源
	 * @return 
	 * @throws IOException 
	 */
//	@RequestMapping(value = "/publish/{resourceId}/{instantLoad}", method = RequestMethod.GET)
//	public Response publish(@PathVariable String resourceId, @PathVariable boolean instantLoad) throws IOException {
//		logger.info("要发布的资源id为:{}", resourceId);
//		List<ResourceAdapter> resources = service.getResources(resourceId);
//		if(resources.isEmpty()) {
//			ResponseContext.addValidation("id为%s的资源发布失败, 未查询到任何content信息", null, resourceId);
//		}else {
//			List<String> paths = write2Files(resources);
//			
//			if(instantLoad)
//				callLoadResources(paths);
//			
//			service.saveResourcePublishingState(resourceId);
//			ResponseContext.addData(resourceId);
//		}
//		return ResponseContext.getFinalResponse();
//	}
	
	/**
	 * 将资源写到文件
	 * @param resources
	 * @return 资源文件的绝对路径集合
	 * @throws IOException
	 */
//	private List<String> write2Files(List<ResourceAdapter> resources) throws IOException {
//		logger.info("开始向指定文件写入资源content");
//		List<String> paths = new ArrayList<String>(resources.size());
//		try(FileBufferedWriter writer = new FileBufferedWriter()){
//			for (ResourceAdapter resource : resources) {
//				if(resource.isUpdateName()) { // 如果修改了名称
//					if(resource.getOldName() != null) // 如果是修改了资源名, 将之前旧资源名对应的文件删除
//						deleteOldFile(resource);
//					
//					// 写入带有oldName的资源文件, 资源文件名开头有TEMP.作为标识
//					writer.setTargetFile(resource.getFile("TEMP." + resource.getName() + resource.getType().getFileSuffix()));
//					writer.writeAndClose(resource.getContent());
//					paths.add(writer.getTargetFile().getAbsolutePath()); // 记录资源文件的绝对路径
//					if(logger.isInfoEnabled()) {
//						logger.info("写入的文件内容为: {}", resource.getContent());
//						logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//					}
//					
//					// 写入不带有oldName的资源文件
//					writer.setTargetFile(resource.getFile());
//					writer.writeAndClose(resource.getContent().replaceAll("oldName=\"([^\"]*)\"", ""));
//					if(logger.isInfoEnabled()) {
//						logger.info("写入的文件内容为: {}", resource.getContent().replaceAll("oldName=\"([^\"]*)\"", ""));
//						logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//					}
//				} else { // 否则, 就直接创建资源文件
//					writer.setTargetFile(resource.getFile());
//					writer.writeAndClose(resource.getContent());
//					paths.add(writer.getTargetFile().getAbsolutePath()); // 记录资源文件的绝对路径
//					if(logger.isInfoEnabled()) {
//						logger.info("写入的文件内容为: {}", resource.getContent());
//						logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//					}
//				}
//				
//				if(helper.isFixedResource(resource.getName())) 
//					helper.write2File(resource, writer, paths);
//			}
//		}
//		logger.info("结束向指定文件写入资源content");
//		return paths;
//	}
//	
	/**
	 * 删除旧的资源文件
	 * @param resource
	 */
	private void deleteOldFile(ResourceAdapter resource) {
		FileUtil.delete(resource.getFile(resource.getOldName() + resource.getType().getFileSuffix()));
		if(logger.isInfoEnabled()) 
			logger.info("删除旧的资源文件: {}", resource.getOldName() + resource.getType().getFileSuffix());
		
		if(helper.isFixedResource(resource.getName()))
			helper.deleteOldFile(resource);
	}

	/**
	 * 让smt-imp服务加载刚刚创建的资源
	 * @param paths
	 */
	private void callLoadResources(List<String> paths) {
		logger.info("调用smt-imp的接口, 加载指定的资源文件: {}", paths);
		restTemplate.exchange(new APIServer() {
			
			@Override
			public String getName() {
				return "加载资源API";
			}
			
			@Override
			public String getUrl() {
				return "http://smt-imp/resource/load";
			}

			@Override
			public HttpMethod getRequestMethod() {
				return HttpMethod.POST;
			}
			
		}, JSONObject.toJSONString(paths), null);
	}
	
	
	/**
	 * 取消资源的发布状态
	 * @return 
	 */
	@RequestMapping(value = "/publish/state/cancel", method = RequestMethod.GET)
	public Response cancelPublishState(HttpServletRequest request) {
		String resourceId = request.getParameter("resourceId");
		service.cancelPublishState(resourceId);
		ResponseContext.addData(resourceId);
		return ResponseContext.getFinalResponse();
	}
	
}
