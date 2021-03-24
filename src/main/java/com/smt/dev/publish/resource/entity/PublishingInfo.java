package com.smt.dev.publish.resource.entity;

/**
 * (资源)发布信息
 * @author DougLei
 */
public class PublishingInfo {
	private int id;
	private String resourceId;
	
	public PublishingInfo() {
	}
	public PublishingInfo(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
