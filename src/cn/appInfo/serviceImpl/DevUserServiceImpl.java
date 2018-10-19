package cn.appInfo.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.appInfo.dao.DevUserDao;
import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.entity.DevUser;
import cn.appInfo.service.DevUserService;
import cn.appInfo.utils.PageSupport;

@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {
	@Resource
	private DevUserDao devUserDao;
	@Override
	public DevUser login(String devCode, String devPassword) {
		DevUser devUser = devUserDao.login(devCode);
		if(devUser!=null){
			if(!devUser.getDevPassword().equals(devPassword)){
				devUser = null;
			}
		}
		return devUser;
	}
	@Override
	public List<Data_Dictionary> findStatus() {
		return devUserDao.selectStatus();
	}
	@Override
	public List<Data_Dictionary> findFlatform() {
		return devUserDao.selectFlatform();
	}
	@Override
	public List<App_Category> findLevel1() {
		return devUserDao.selectLevel1();
	}
	@Override
	public List<App_Category> findLevel2(Integer level1) {
		return devUserDao.selectLevel2(level1);
	}
	@Override
	public List<App_Category> findLevel3(Integer parentId) {
		return devUserDao.selectLevel3(parentId);
	}
	@Override
	public List<AppInfo> findAllInfo(PageSupport pages) {
		String querySoftwareName = null;
		Integer queryStatus = null;
		Integer queryCategoryLevel1 = null;
		Integer queryCategoryLevel2 = null;
		Integer queryCategoryLevel3 = null;
		Integer queryFlatformId = null;
		Integer devId = null;
		return devUserDao.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (pages.getCurrentPageNo()-1)*pages.getPageSize(), pages.getPageSize());
	}
	@Override
	public List<AppInfo> findInfoByCondition(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId,
			Integer currentPageNo, Integer pageSize) {
		return devUserDao.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}
	@Override
	public int findTotalCount(String querySoftwareName,
			Integer queryStatus,
			Integer queryFlatformId,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer devId) {
		return devUserDao.totalCount(querySoftwareName,
									 queryStatus,
									 queryFlatformId,
									 queryCategoryLevel1,
									 queryCategoryLevel2,
									 queryCategoryLevel3,
									 devId);
	}
	@Override
	public int findOne(String APKName) {
		return devUserDao.selectOne(APKName);
	}
	@Override
	public int addAppInfo(AppInfo appInfo) {
		return devUserDao.addAppInfo(appInfo);
	}
	@Override
	public AppInfo findAppInfoById(Integer pid) {
		return devUserDao.getAppInfoById(pid);
	}
	@Override
	public List<App_Category> getAppCategoryListByParentId(Integer parentId) throws Exception {
	    return devUserDao.getAppCategoryListByParentId(parentId);
	}
	@Override
	public int updateModify(AppInfo appInfo) {
		return devUserDao.modify(appInfo);
	}
	@Override
	public List<App_Version> findVersionInfoById(Integer appId) {
		return devUserDao.getAppVersionList(appId);
	}
	@Override
	public int addVersion(App_Version appVersion) {
		return devUserDao.add(appVersion);
	}
	@Override
	public int updateVersionId(Integer versionId, Integer appId) {
		return devUserDao.updateVersionId(versionId, appId);
	}
	@Override
	public App_Version findVersionById(Integer id) {
		return devUserDao.getVersionInfoById(id);
	}
	@Override
	public int updateVersionFile(Integer id) {
		return devUserDao.deleteApkFile(id);
	}
	@Override
	public int modifyVersion(App_Version appVersion) {
		return devUserDao.modifyVersion(appVersion);
	}
	@Override
	public AppInfo findViewInfo(Integer appId) {
		return devUserDao.getView(appId);
	}
	@Override
	public int delAppInfoVersion(Integer id) {
		int line = 0;
		try {
			if(devUserDao.delVersionInfo(id)>0){
				devUserDao.delAppInfo(id);
				line = 1;
			}else{
				devUserDao.delAppInfo(id);
				line = 1;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			line = 0;
		}
		return line;
	}
	@Override
	public int updateAppInfoStatus(Integer appId, Integer status) {
		return devUserDao.updateStatus(appId, status);
	}

}
