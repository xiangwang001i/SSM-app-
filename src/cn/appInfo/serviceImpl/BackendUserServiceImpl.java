package cn.appInfo.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appInfo.dao.BackendUserDao;
import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.BackendUser;
import cn.appInfo.service.BackendUserService;
import cn.appInfo.utils.PageSupport;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Resource
	private BackendUserDao backendUserDao;
	
	@Override
	public BackendUser login(String userCode, String userPassword) {
		BackendUser backendUser = backendUserDao.login(userCode);
		if(backendUser!=null){
			if(!backendUser.getUserPassword().equals(userPassword)){
				backendUser = null;
			}
		}
		return backendUser;
	}

	@Override
	public List<AppInfo> findAppInfoByStatus(String querySoftwareName, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId,
			Integer currentPageNo, Integer pageSize) {
		return backendUserDao.selectAppInfoByStatus(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public List<AppInfo> findAllAppInfo(Integer devId,PageSupport pages) {
		String querySoftwareName = null;
		Integer queryCategoryLevel1 = null;
		Integer queryCategoryLevel2 = null;
		Integer queryCategoryLevel3 = null;
		Integer queryFlatformId = null;
		return backendUserDao.selectAppInfoByStatus(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (pages.getCurrentPageNo()-1)*pages.getPageSize(), pages.getPageSize());
	}

	@Override
	public int getTotalCount(String querySoftwareName, Integer queryFlatformId, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer devId) {
		return backendUserDao.totalCount(querySoftwareName, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, devId);
	}

	@Override
	public AppInfo getBackendView(Integer appid) {
		return backendUserDao.getView(appid);
	}

	@Override
	public App_Version getView2(Integer versionId) {
		return backendUserDao.getView2(versionId);
	}

	@Override
	public int updateStatus(Integer id, Integer status) {
		return backendUserDao.check(id, status);
	}

}
