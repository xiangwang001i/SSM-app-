package cn.appInfo.service;

import java.util.List;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.BackendUser;
import cn.appInfo.utils.PageSupport;

public interface BackendUserService {

	public BackendUser login(String userCode,String userPassword);
	public List<AppInfo> findAppInfoByStatus(String querySoftwareName,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer queryFlatformId,
			Integer devId,
			Integer currentPageNo,
			Integer pageSize);
	public List<AppInfo> findAllAppInfo(Integer devId,PageSupport pages);
	public int getTotalCount(String querySoftwareName,
			Integer queryFlatformId,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer devId);
	public AppInfo getBackendView(Integer appid);
	public App_Version getView2(Integer versionId);
	public int updateStatus(Integer id,Integer status);
}
