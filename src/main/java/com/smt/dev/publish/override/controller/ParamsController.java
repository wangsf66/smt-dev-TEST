package com.smt.dev.publish.override.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douglei.api.doc.annotation.Api;
import com.douglei.api.doc.annotation.ApiParam;
import com.douglei.api.doc.annotation.ApiParam_;
import com.douglei.api.doc.types.ParamStructType;
import com.douglei.orm.sessionfactory.validator.table.handler.UpdateValidateHandler;
import com.ibs.code.controller.BasicController;
import com.ibs.code.result.DataValidationResult;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.spring.resolver.method.argument.CommonParams;
import com.ibs.spring.resolver.method.argument.CommonParamsModel;
import com.smartone.ddm.resource.entity.DmResource;
import com.smartone.ddm.resource.entity.DmResourceParam;
import com.smt.dev.publish.override.service.ParamsService;



@RestController
@RequestMapping("/column")
public class ParamsController extends BasicController  {
	
	@Autowired
	private ParamsService paramsService;
	
	@Api(name="修改列",
			 request=@ApiParam(params ={
				 @ApiParam_(entity=DmResource.class,entityStruct=ParamStructType.OBJECT)
			 }))
	@RequestMapping({"/update"})
	public Response update(@CommonParams(cls=DmResourceParam.class)CommonParamsModel<DmResourceParam> commonParamsModel) {
		if(commonParamsModel.getList()!=null) {
       	if(validate4Table(commonParamsModel.getList(),UpdateValidateHandler.getInstance4UpdateNullValue())==DataValidationResult.SUCCESS) { 
       		paramsService.updateMany(commonParamsModel.getList());
   		 } 
       }
	   return ResponseContext.getFinalResponse(commonParamsModel.getIsBatch());
	}
	
	@Api(name="添加列",
			 request=@ApiParam(params ={
				 @ApiParam_(entity=DmResource.class,entityStruct=ParamStructType.OBJECT)
			 }))
	@RequestMapping({"/insert"})
	public Response save(@CommonParams(cls=DmResourceParam.class)CommonParamsModel<DmResourceParam> commonParamsModel) {
		if(commonParamsModel.getList()!=null) {
      	if(validate4Table(commonParamsModel.getList(),UpdateValidateHandler.getInstance4UpdateNullValue())==DataValidationResult.SUCCESS) { 
      		paramsService.insertMany(commonParamsModel.getList());
  		 } 
      }
	   return ResponseContext.getFinalResponse(commonParamsModel.getIsBatch());
	}
	
	@Api(name="删除参数",
			 url=@ApiParam(params ={
					 @ApiParam_(name="ids", required=true, description="参数id", egValue="1111")
			 }))
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Response delete(HttpServletRequest request) {
		String ids = getDeleteIds(request);
		if(ids!=null) {
			paramsService.delete(ids);
		}
	    return ResponseContext.getFinalResponse();
	}
}
