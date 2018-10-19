package cn.appInfo.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.entity.DevUser;
import cn.appInfo.service.DevUserService;
import cn.appInfo.utils.Constants;
import cn.appInfo.utils.PageSupport;

@Controller
public class devUserController {
	@Resource
	private DevUserService devUserService;
	private Logger logger = Logger.getLogger(devUserController.class);
	
	@RequestMapping(value="/sys/appInfolist.html",method=RequestMethod.GET)
	public String main(Model model){
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(1);
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(devUserService.findTotalCount(null,null,null,null,null,null,null));
		List<Data_Dictionary> list = devUserService.findStatus();
		List<Data_Dictionary> list2 = devUserService.findFlatform();
		List<App_Category> list3 = devUserService.findLevel1();
		List<AppInfo> list4 = devUserService.findAllInfo(pages);
		
		model.addAttribute("pages",pages);
		model.addAttribute("status",list);
		model.addAttribute("flatform",list2);
		model.addAttribute("level1",list3);
		model.addAttribute("appInfoList",list4);
		return "devjsp/appinfolist";
	}
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public String pages(@RequestParam Integer pageIndex,
						@RequestParam(required=false) String querySoftwareName,
						@RequestParam(required=false) Integer queryStatus,
						@RequestParam(required=false) Integer queryFlatformId,
						@RequestParam(required=false) Integer queryCategoryLevel1,
						@RequestParam(required=false) Integer queryCategoryLevel2,
						@RequestParam(required=false) Integer queryCategoryLevel3,
						HttpSession session,
						Model model){
		DevUser u = (DevUser)session.getAttribute(Constants.USER_SESSION);
		int zt = devUserService.findTotalCount(querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,u.getId());
		int zy = zt%Constants.pageSize==0?zt/Constants.pageSize:zt/Constants.pageSize+1;
		System.out.println(zt+"\t"+zy);
		if(pageIndex<=0){
			pageIndex = 1;
		}
		if(pageIndex>=zy){
			pageIndex = zy;
		}
		System.out.println(zt+"\t"+zy);
		List<AppInfo> list4 = null;
		if(zt==0){
			list4 = null;
		}else{
			list4 = devUserService.findInfoByCondition(querySoftwareName, 
																	 queryStatus, 
																	 queryCategoryLevel1,
																	 queryCategoryLevel2, 
																	 queryCategoryLevel3, 
																	 queryFlatformId, 
																	 u.getId(), 
																	 pageIndex, 
																	 5);
		}
		
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(pageIndex);
		pages.setPageSize(Constants.pageSize);
		System.out.println(zt);
		pages.setTotalCount(zt);
		List<Data_Dictionary> list = devUserService.findStatus();
		List<Data_Dictionary> list2 = devUserService.findFlatform();
		List<App_Category> list3 = devUserService.findLevel1();
		
		if(querySoftwareName!=null || querySoftwareName!=""){
			model.addAttribute("querySoftwareName",querySoftwareName);
		}
		if(queryStatus!=null){
			model.addAttribute("queryStatus",queryStatus);
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
		model.addAttribute("status",list);
		model.addAttribute("flatform",list2);
		model.addAttribute("level1",list3);
		model.addAttribute("appInfoList",list4);
		
		return "devjsp/appinfolist";
	}
	@RequestMapping(value="/addAppInfo",method=RequestMethod.POST)
	public String addAppInfo(AppInfo appInfo,HttpServletRequest request,HttpSession session,@RequestParam(value ="a_logoPicPath", required = false) MultipartFile attach){
		DevUser du = (DevUser)session.getAttribute(Constants.USER_SESSION);
		String logoLocPath = null;
		String logoPicPath = null;
		//判断文件是否为空
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
			int filesize = 50000;
	        if(attach.getSize() >  filesize){//上传大小不得超过 500k
            	request.setAttribute("uploadFileError", " * 上传大小不得超过 50k");
            	request.setAttribute("addInfoBack", appInfo);
	        	return "devjsp/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
            		|| prefix.equalsIgnoreCase("jpeg")){//上传图片格式不正确
            	Random r = new Random();
				int random = r.nextInt(1000000);
            	String fileName = System.currentTimeMillis()+random+"_Personal.jpg";  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	attach.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    request.setAttribute("addInfoBack", appInfo);
                    return "devjsp/appinfoadd";
                }  
                logoLocPath = path+File.separator+fileName;
                logoPicPath = "statics"+File.separator+"uploadfiles";
            }else{
            	request.setAttribute("uploadFileError", " * 上传图片格式不正确");
            	request.setAttribute("addInfoBack", appInfo);
            	return "devjsp/appinfoadd";
            }
		}
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setCreatedBy(du.getId());
		appInfo.setCreationDate(new Date());
		appInfo.setDevId(du.getId());
		if(devUserService.addAppInfo(appInfo)>0){
			return "redirect:/sys/appInfolist.html";
		}
		return "devjsp/appinfoadd";
	}
	/**
	 * 验证唯一性
	 * @param APKName
	 * @return
	 */
	@RequestMapping(value="/APKName",method=RequestMethod.GET)
	@ResponseBody
	public String APKName(@RequestParam String APKName){
		int line = devUserService.findOne(APKName);
		if(APKName==null || APKName==""){
			return "empty";
		}else if(line>0){
			return "exist";
		}else if(line==0){
			return "noexist";
		}else{
			return "hz";
		}
	}
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<App_Category> getAppCategoryList (@RequestParam String pid){
		logger.debug("getAppCategoryList pid ============ " + pid);
		if(pid.equals("")) pid = null;
		return getCategoryList(pid);
	}
	
	public List<App_Category> getCategoryList (String pid){
		List<App_Category> categoryLevelList = null;
		try {
			categoryLevelList = devUserService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryLevelList;
	}
	@RequestMapping(value="/flatformInfo",method=RequestMethod.GET)
	@ResponseBody
	public Object flatform(){
		List<Data_Dictionary> list2 = devUserService.findFlatform();
		return list2;
	}
	@RequestMapping(value="/level1",method=RequestMethod.GET)
	@ResponseBody
	public Object level1(){
		List<App_Category> list3 = devUserService.findLevel1();
		return list3;
	}
	@RequestMapping(value="/getLevel2",method=RequestMethod.GET)
	@ResponseBody
	public Object Getlevel2(@RequestParam Integer pid){
		logger.info(pid);
		List<App_Category> list = devUserService.findLevel2(pid);
		return JSON.toJSON(list);
	}
	@RequestMapping(value="/getLevel3",method=RequestMethod.GET)
	@ResponseBody
	public Object Getlevel3(@RequestParam Integer pid){
		logger.info(pid);
		List<App_Category> list = devUserService.findLevel3(pid);
		return JSON.toJSON(list);
	}
	
	@RequestMapping(value="/sys/getaddInfo",method=RequestMethod.GET)
	public String addGET(){
		return "devjsp/appinfoadd";
	}
	/**
	 * 显示修改页面
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String GETupdate(@RequestParam Integer id,Model model){
		System.out.println(id);
		AppInfo appInfo = devUserService.findAppInfoById(id);
		model.addAttribute("appInfo",appInfo);
		return "devjsp/appinfomodify";
	}
	/**
	 * 修改及保存及重新上传
	 * @param appInfo
	 * @param session
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String POSTupdate(AppInfo appInfo,HttpSession session,@RequestParam(required=false) Integer status){
		DevUser du = (DevUser)session.getAttribute(Constants.USER_SESSION);
		if(status==null){
			appInfo.setModifyBy(du.getId());
			appInfo.setModifyDate(new Date());
			int line = devUserService.updateModify(appInfo);
			if(line>0){
				return "redirect:/sys/appInfolist.html";
			}else{
				return "devjsp/appinfomodify";
			}
		}else{
			appInfo.setStatus(status);
			appInfo.setModifyBy(du.getId());
			appInfo.setModifyDate(new Date());
			int line = devUserService.updateModify(appInfo);
			if(line>0){
				return "redirect:/sys/appInfolist.html";
			}else{
				return "devjsp/appinfomodify";
			}
		}
	}
	/**
	 * 显示新增版本信息页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/appversionadd",method=RequestMethod.GET)
	public String appversionadd(@RequestParam Integer id,Model model){
		List<App_Version> list = devUserService.findVersionInfoById(id);
		model.addAttribute("appVersionList",list);
		model.addAttribute("appId",id);
		return "devjsp/appversionadd";
	}
	/**
	 * 保存新增版本的信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appversionadd",method=RequestMethod.POST)
	public String appversionaddPOST(@RequestParam Integer appId,@RequestParam Integer publishStatus,App_Version appVersion,HttpServletRequest request,HttpSession session,@RequestParam(value ="a_downloadLink", required = false) MultipartFile attach){
		DevUser du = (DevUser)session.getAttribute(Constants.USER_SESSION);
		String logoLocPath = null;
		String logoPicPath = null;
		String fileName = null;
		//判断文件是否为空
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
			long filesize = 5000000000l;
	        if(attach.getSize() >  filesize){//上传大小不得超过 500MB
            	request.setAttribute("uploadFileError", " * 上传大小不得超过 500MB");
            	request.setAttribute("addInfoBack", appVersion);
	        	return "devjsp/appversionadd";
            }else if(prefix.equalsIgnoreCase("apk") 
            		|| prefix.equalsIgnoreCase("jpeg")){//上传图片格式不正确
            	Random r = new Random();
				int random = r.nextInt(1000000);
            	fileName = System.currentTimeMillis()+random+"_Personal.apk";  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                try {  
                	attach.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    request.setAttribute("addInfoBack", appVersion);
                    return "devjsp/appversionadd";
                }  
                logoLocPath = path+File.separator+fileName;
                logoPicPath = "statics"+File.separator+"uploadfiles"+File.separator+fileName;
            }else{
            	request.setAttribute("uploadFileError", " * 上传图片格式不正确");
            	request.setAttribute("addInfoBack", appVersion);
            	return "devjsp/appversionadd";
            }
		}
		appVersion.setDownloadLink(logoPicPath);
		appVersion.setApkFileName(fileName);
		appVersion.setApkLocPath(logoLocPath);
		appVersion.setAppId(appId);
		appVersion.setPublishStatus(publishStatus);
		appVersion.setCreatedBy(du.getId());
		appVersion.setCreationDate(new Date());
		if(devUserService.addVersion(appVersion)>0){
			if(devUserService.updateVersionId(appVersion.getId(), appId)>0){
				return "redirect:/sys/appInfolist.html";
			}
		}
		request.setAttribute("addInfoBack", appVersion);
		return "devjsp/appversionadd";
	}
	/**
	 * 显示版本信息修改页面
	 * @param vid
	 * @param aid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/appversionmodify",method=RequestMethod.GET)
	public String appversionmodify(@RequestParam Integer vid,@RequestParam Integer aid,Model model){
		System.out.println(vid+"\t"+aid);
		List<App_Version> list = devUserService.findVersionInfoById(aid);
		App_Version appVersion = devUserService.findVersionById(vid);
		model.addAttribute("appVersionList",list);
		model.addAttribute("appVersion",appVersion);
		return "devjsp/appversionmodify";
	}
	/**
	 * 删除文件方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delfile.json",method=RequestMethod.GET)
	@ResponseBody
	public Object appversiondel(@RequestParam Integer id){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		String fileLocPath = null;
		try {
			fileLocPath = (devUserService.findVersionById(id)).getApkLocPath();
			File file = new File(fileLocPath);
			if (file.exists()) {
				if (file.delete()) {// 删除服务器存储的物理文件
					if (devUserService.updateVersionFile(id)>0) {// 更新表
						resultMap.put("result", "success");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "failed");
		}
		return JSON.toJSON(resultMap);
	}
	@RequestMapping(value="/appversionmodifysave",method=RequestMethod.POST)
	public String appversionmodifysave(App_Version appVersion,@RequestParam(value="attach",required= false) MultipartFile attach,HttpSession session,HttpServletRequest request){
		DevUser du = (DevUser)session.getAttribute(Constants.USER_SESSION);
		String logoLocPath = null;
		String logoPicPath = null;
		String fileName = null;
		//判断文件是否为空
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"); 
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀     
			long filesize = 5000000000l;
	        if(attach.getSize() >  filesize){//上传大小不得超过 500MB
            	request.setAttribute("uploadFileError", " * 上传大小不得超过 500MB");
            	request.setAttribute("addInfoBack", appVersion);
	        	return "devjsp/appversionmodify";
            }else if(prefix.equalsIgnoreCase("apk") 
            		|| prefix.equalsIgnoreCase("jpeg")){//上传图片格式不正确
            	Random r = new Random();
				int random = r.nextInt(1000000);
            	fileName = System.currentTimeMillis()+random+"_Personal.apk";  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                try {  
                	attach.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    request.setAttribute("addInfoBack", appVersion);
                    return "devjsp/appversionmodify";
                }  
                logoLocPath = path+File.separator+fileName;
                logoPicPath = "statics"+File.separator+"uploadfiles"+File.separator+fileName;
                appVersion.setDownloadLink(logoPicPath);
        		appVersion.setApkFileName(fileName);
        		appVersion.setApkLocPath(logoLocPath);
            }else{
            	request.setAttribute("uploadFileError", " * 上传图片格式不正确");
            	request.setAttribute("addInfoBack", appVersion);
            	return "devjsp/appversionmodify";
            }
		}
		appVersion.setModifyBy(du.getId());
		appVersion.setModifyDate(new Date());
		if(devUserService.modifyVersion(appVersion)>0){
			return "redirect:/sys/appInfolist.html";
		}
		return "devjsp/appversionmodify";
	}
	@RequestMapping(value="/sys/appinfoview",method=RequestMethod.GET)
	public String appinfoview(@RequestParam Integer appinfoid,Model model){
		System.out.println(appinfoid);
		List<App_Version> list = devUserService.findVersionInfoById(appinfoid);
		AppInfo appInfo = devUserService.findViewInfo(appinfoid);
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersionList",list);
		return "devjsp/appinfoview";
	}
	/**
	 * ajax删除APP
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delapp.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delapp(@RequestParam Integer id){
		HashMap<String,String> result = new HashMap<String,String>();
		if(devUserService.delAppInfoVersion(id)>0){
			result.put("delResult", "true");
		}else{
			result.put("delResult", "false");
		}
		return result;
	}
	/**
	 * ajax上架、下架操作
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="/updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public Object sale(@RequestParam Integer appId,@RequestParam String info){
		HashMap<String,String> result = new HashMap<String,String>();
		if(info.equals("open")){  //上架
			if(devUserService.updateAppInfoStatus(appId, 4)>0){
				result.put("resultMsg","success");
			}else{
				result.put("resultMsg","failed");
			}
		}else if(info.equals("close")){  //下架
			if(devUserService.updateAppInfoStatus(appId, 5)>0){
				result.put("resultMsg","success");
			}else{
				result.put("resultMsg","failed");
			}
		}
		return result;
	}
}
