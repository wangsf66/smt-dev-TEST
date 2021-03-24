package com.smt.dev.publish.resource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.Transaction;
import com.douglei.orm.context.TransactionComponent;
import com.ibs.code.service.BasicService;
import com.smartone.ddm.resource.entity.Resource;
import com.smartone.ddm.resource.service.ResourceContentService;
import com.smt.dev.publish.resource.entity.PublishingInfo;
import com.smt.dev.publish.resource.entity.ResourceAdapter;
import com.smt.dev.publish.util.ResourceProperties;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class ResourcePublishService extends BasicService{
	
	@Autowired
	private ResourceContentService resourceContentService;
	
	@Autowired
	private ResourceProperties resourceProperties;
	
	/**
	 * 获取要发布的资源集合
	 * @param resourceId
	 * @return
	 */
	@Transaction
	public List<ResourceAdapter> getResources(String resourceId){
		List<Resource> resources = resourceContentService.getResourceContent(resourceId);
		if(resources.isEmpty())
			return Collections.emptyList();
		
		List<ResourceAdapter> resourceAdapters = new ArrayList<ResourceAdapter>(resources.size());
		for (Resource resource : resources) 
			resourceAdapters.add(new ResourceAdapter(resourceProperties.getFolder(), resource));
		return resourceAdapters;
	}

	/**
	 * 保存资源的发布状态
	 * @param resourceId
	 */
	@Transaction
	public void saveResourcePublishingState(String resourceId) {
		if(Byte.parseByte(SessionContext.getSqlSession().uniqueQuery_("select count(id) from dev_publishing_info where resource_id=?", Arrays.asList(resourceId))[0].toString()) == 0)
			SessionContext.getTableSession().save(new PublishingInfo(resourceId));
	}

	/**
	 * 取消资源的发布状态
	 * @param resourceId 
	 * @return 
	 */
	@Transaction
	public void cancelPublishState(String resourceId) {
		SessionContext.getSqlSession().executeUpdate("delete dev_publishing_info where resource_id=?", Arrays.asList(resourceId));
	}
}
