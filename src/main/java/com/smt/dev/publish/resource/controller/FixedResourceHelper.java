package com.smt.dev.publish.resource.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.file.FileUtil;
import com.douglei.tools.file.writer.FileBufferedWriter;
import com.smt.dev.publish.resource.entity.ResourceAdapter;
import com.smt.dev.publish.util.FixedResourceInfo;

/**
 * 固定资源工具类
 * @author DougLei
 */
class FixedResourceHelper extends FixedResourceInfo{
	private static final Logger logger = LoggerFactory.getLogger(FixedResourceHelper.class);

	/**
	 * 将固定资源内容写入到文件中
	 * @param resource
	 * @param writer
	 * @param paths
	 * @throws IOException
	 */
//	public void write2File(ResourceAdapter resource, FileBufferedWriter writer, List<String> paths) throws IOException {
//		// 同样判断是否修改了资源名, 可以参看 {@link ResourcePublisher.write2Files(List)}
//		String resourceName = resource.getName() + suffix;
//		String content = resource.getContent().replaceFirst(resource.getName(), resourceName);
//		if(resource.isUpdateName()) {
//			if(resource.getOldName() != null)
//				content = content.replaceFirst(resource.getOldName(), resource.getOldName() + suffix);
//			
//			// 写入带有oldName的资源文件, 资源文件名开头有TEMP.作为标识
//			writer.setTargetFile(resource.getFile("TEMP." + resourceName + resource.getType().getFileSuffix()));
//			writer.writeAndClose(content);
//			paths.add(writer.getTargetFile().getAbsolutePath()); // 记录资源文件的绝对路径
//			if(logger.isInfoEnabled()) {
//				logger.info("写入的文件内容为: {}", content);
//				logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//			}
//			
//			// 写入不带有oldName的资源文件
//			writer.setTargetFile(resource.getFile(resourceName + resource.getType().getFileSuffix()));
//			writer.writeAndClose(content.replaceAll("oldName=\"([^\"]*)\"", ""));
//			if(logger.isInfoEnabled()) {
//				logger.info("写入的文件内容为: {}", content.replaceAll("oldName=\"([^\"]*)\"", ""));
//				logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//			}
//		} else {
//			writer.setTargetFile(resource.getFile(resourceName + resource.getType().getFileSuffix()));
//			writer.writeAndClose(content);
//			paths.add(writer.getTargetFile().getAbsolutePath()); // 记录资源文件的绝对路径
//			if(logger.isInfoEnabled()) {
//				logger.info("写入的文件内容为: {}", content);
//				logger.info("写入的文件路径为: {}", writer.getTargetFile().getAbsolutePath());
//			}
//		}
//	}

	/**
	 * 删除旧的资源文件
	 * @param resource
	 */
	public void deleteOldFile(ResourceAdapter resource) {
		FileUtil.delete(resource.getFile(resource.getOldName() + suffix + resource.getType().getFileSuffix()));
		if(logger.isInfoEnabled()) 
			logger.info("删除旧的资源文件: {}", resource.getOldName() + suffix + resource.getType().getFileSuffix());
	}
}
