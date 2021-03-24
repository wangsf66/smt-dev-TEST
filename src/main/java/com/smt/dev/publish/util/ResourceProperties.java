package com.smt.dev.publish.util;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix = "smt.dev.resource")
public class ResourceProperties {
	private String folder = System.getProperty("user.home") + File.separatorChar + ".dev-resources" + File.separatorChar; // 存储开发资源的文件夹;

	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
}
