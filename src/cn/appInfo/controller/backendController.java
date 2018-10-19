package cn.appInfo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.BackendUser;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.service.BackendUserService;
import cn.appInfo.service.DevUserService;
import cn.appInfo.utils.Constants;
import cn.appInfo.utils.PageSupport;

@Controller
public class backendController {

	@Resource
	private BackendUserService backendUserService;
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/sys2/backend.html",method=RequestMethod.GET)
	public String main(Model model,HttpSession session){
		BackendUser b = (BackendUser)session.getAttribute(Constants.USER_SESSION);
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(1);
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(backendUserService.getTotalCount(null, null, null, null, null, b.getId()));
		List<Data_Dictionary> list2 = devUserService.findFlatform();
		List<App_Category> list3 = devUserService.findLevel1();
		List<AppInfo> list4 = backendUserService.findAllAppInfo(b.getId(), pages);
		
		model.addAttribute("pages",pages);
		model.addAttribute("flatFormList",list2);
		model.addAttribute("categoryLevel1List",list3);
		model.addAttribute("appInfoList",list4);
		return "backendjsp/applist";
	}
	@RequestMapping(value="/backend/getLevel2",method=RequestMethod.GET)
	@ResponseBody
	public Object Getlevel2(@RequestParam Integer pid){
		List<App_Category> list = devUserService.findLevel2(pid);
		return JSON.toJSON(list);
	}
	@RequestMapping(value="/backend/getLevel3",method=RequestMethod.GET)
	@ResponseBody
	public Object Getlevel3(@RequestParam Integer pid){
		List<App_Category> list = devUserService.findLevel3(pid);
		return JSON.toJSON(list);
	}
	@RequestMapping(value="/backend/list",method=RequestMethod.POST)
	public String pages(@RequestParam Integer pageIndex,
						@RequestParam(required=false) String querySoftwareName,
						@RequestParam(required=false) Integer queryFlatformId,
						@RequestParam(required=false) Integer queryCategoryLevel1,
						@RequestParam(required=false) Integer queryCategoryLevel2,
						@RequestParam(required=false) Integer queryCategoryLevel3,
						HttpSession session,
						Model model){
		BackendUser b = (BackendUser)session.getAttribute(Constants.USER_SESSION);
		int zt = backendUserService.getTotalCount(querySoftwareName,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,b.getId());
		int zy = zt%Constants.pageSize==0?zt/Constants.pageSize:zt/Constants.pageSize+1;
		if(pageIndex<=0){
			pageIndex = 1;
		}
		if(pageIndex>=zy){
			pageIndex = zy;
		}
		List<AppInfo> list4 = null;
		if(zt==0){
			list4 = null;
		}else{
			list4 = backendUserService.findAppInfoByStatus(querySoftwareName, 
					 queryCategoryLevel1,
					 queryCategoryLevel2, 
					 queryCategoryLevel3, 
					 queryFlatformId, 
					 b.getId(), 
					 pageIndex, 
					 5);
		}
		
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(pageIndex);
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(zt);
		List<Data_Dictionary> list2 = devUserService.findFlatform();
		List<App_Category> list3 = devUserService.findLevel1();
		
		if(querySoftwareName!=null || querySoftwareName!=""){
			model.addAttribute("querySoftwareName",querySoftwareName);
		}
		if(queryFlatformId!=null){
			model.addAttribute("queryFlatformId",queryFlatformId);
		}
		if(queryCategoryLevel1!=null){
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
		}
		if(queryCategoryLevel2!=null){
			List<App_Category> categoryLevel2List = devUserService.findLevel2(queryCategoryLevel1);
			model.addAttribute("categoryLevel2List",categoryLevel2List);
			model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
		}
		if(queryCategoryLevel3!=null){
			List<App_Category> categoryLevel3List = devUserService.findLevel3(queryCategoryLevel2);
			model.addAttribute("categoryLevel3List",categoryLevel3List);
			model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
		}
		model.addAttribute("pages",pages);
		model.addAttribute("flatFormList",list2);
		model.addAttribute("categoryLevel1List",list3);
		model.addAttribute("appInfoList",list4);
		return "backendjsp/applist";
	}
	@RequestMapping(value="/backend/check",method=RequestMethod.GET)
	public String check(@RequestParam Integer aid,Model model){
		AppInfo appInfo = backendUserService.getBackendView(aid);
		App_Version v = backendUserService.getView2(appInfo.getVersionId());
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersion",v);
		return "backendjsp/backendView";
	}
	@RequestMapping(value="/backend/checksave",method=RequestMethod.POST)
	public String checksave(@RequestParam Integer status,@RequestParam Integer id,Model model){
		int line = backendUserService.updateStatus(id, status);
		if(line>0){
			return "redirect:/sys2/backend.html";
		}
		return "backendjsp/backendView";
	}
}
