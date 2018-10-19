package cn.appInfo.service;

import java.util.List;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.entity.DevUser;
import cn.appInfo.utils.PageSupport;

public interface DevUserService {

	public DevUser login(String devCode,String devPassword);
	public List<Data_Dictionary> findStatus();  //APP状态
	public List<Data_Dictionary> findFlatform();  //所属平台
	public List<App_Category> findLevel1();  //查询一级分类
	public List<App_Category> findLevel2(Integer level1);
	public List<App_Category> findLevel3(Integer parentId);
	public List<AppInfo> findAllInfo(PageSupport pages);
	public List<AppInfo> findInfoByCondition(String querySoftwareName,
			Integer queryStatus,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer queryFlatformId,
			Integer devId,
			Integer currentPageNo,
			Integer pageSize);
	public int findTotalCount(String querySoftwareName,
			Integer queryStatus,
			Integer queryFlatformId,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer devId);
	public int findOne(String APKName);
	public int addAppInfo(AppInfo appInfo);
	public AppInfo findAppInfoById(Integer pid);
	public List<App_Category> getAppCategoryListByParentId(Integer parentId)throws Exception;
	public int updateModify(AppInfo appInfo);
	public List<App_Version> findVersionInfoById(Integer appId);
	public int addVersion(App_Version appVersion);
	public int updateVersionId(Integer versionId,Integer appId);
	public App_Version findVersionById(Integer id);
	public int updateVersionFile(Integer id);
	public int modifyVersion(App_Version appVersion);
	public AppInfo findViewInfo(Integer appId);
	public int delAppInfoVersion(Integer id);
	public int updateAppInfoStatus(Integer appId,Integer status);
}
