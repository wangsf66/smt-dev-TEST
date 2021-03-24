package com.smt.dev.publish.util;

/**
 * 固定的资源信息
 * @author DougLei
 */
public abstract class FixedResourceInfo {
	
	/**
	 * 固定的资源名, 即表名, 多个是用,分割
	 */
	protected String resourceNames = "SMT_SETTING_FUNC,SMT_SETTING_PAGE,SMT_SETTING_LAYOUT";
	
	/**
	 * 目标固定资源名后缀
	 */
	protected String suffix = "_BASE";  
	
	/**
	 * 指定资源名是否是固定资源
	 * @param resourceName
	 * @return
	 */
	public boolean isFixedResource(String resourceName) {
		return resourceNames.indexOf(resourceName.toUpperCase()) != -1;
	}
}
