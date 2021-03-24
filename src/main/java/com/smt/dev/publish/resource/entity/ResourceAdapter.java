package com.smt.dev.publish.resource.entity;

import java.io.File;

import com.douglei.orm.mapping.MappingType;
import com.douglei.tools.StringUtil;
import com.smartone.ddm.resource.entity.Resource;

/**
 * 资源适配器
 * @author DougLei
 */
public class ResourceAdapter {
	private String folder;
	private String name;
	private String oldName;
	private MappingType type;
	private String content;
	private boolean isUpdateName; // 是否修改过资源中的name, 目前包括资源名或列名
	
	public ResourceAdapter(String folder, Resource resource) {
		this.folder = folder;
		this.name = resource.getName();
		this.oldName = StringUtil.isEmpty(resource.getOldName())?null:resource.getOldName();
		this.type = resource.getType();
		this.content = resource.getContent();
		this.isUpdateName = resource.getIsUpdateName()==null?false:resource.getIsUpdateName();
	}

	
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
	public MappingType getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public boolean isUpdateName() {
		return isUpdateName;
	}

	/**
	 * 获取 (默认资源文件名的) 资源文件
	 * @return
	 */
	public File getFile() {
		return new File(folder + name + type.getFileSuffix());
	}
	
	/**
	 * 获取 (指定资源文件名的) 资源文件
	 * @param fileName 
	 * @return
	 */
	public File getFile(String fileName) {
		return new File(folder + fileName);
	}
}
