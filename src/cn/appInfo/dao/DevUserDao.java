package cn.appInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.entity.DevUser;

public interface DevUserDao {

	public DevUser login(@Param("devCode")String devCode);
	public List<Data_Dictionary> selectStatus();  //APP状态
	public List<Data_Dictionary> selectFlatform();  //所属平台
	public List<App_Category> selectLevel1();  //查询一级分类
	public List<App_Category> selectLevel2(@Param("level1")Integer level1);
	public List<App_Category> selectLevel3(@Param("parentId")Integer parentId);
	public List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
			@Param(value="status")Integer queryStatus,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="devId")Integer devId,
			@Param(value="from")Integer currentPageNo,
			@Param(value="pageSize")Integer pageSize);
	public int totalCount(@Param(value="softwareName")String querySoftwareName,
			@Param(value="status")Integer queryStatus,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="devId")Integer devId);
	public int selectOne(@Param("APKName")String APKName);
	public int addAppInfo(AppInfo appInfo);
	public AppInfo getAppInfoById(@Param("pid")Integer pid);
	public List<App_Category> getAppCategoryListByParentId(@Param("parentId")Integer parentId)throws Exception;
	public int modify(AppInfo appInfo);
	public List<App_Version> getAppVersionList(@Param("appId")Integer appId);
	public int add(App_Version appVersion);
	public int updateVersionId(@Param(value="versionId")Integer versionId,@Param(value="id")Integer appId);
	public App_Version getVersionInfoById(@Param("id")Integer id);
	public int deleteApkFile(@Param("id")Integer id);
	public int modifyVersion(App_Version appVersion);
	public AppInfo getView(@Param("appid")Integer appid);
	public int delAppInfo(@Param("id")Integer id);
	public int delVersionInfo(@Param("id")Integer id);
	public int updateStatus(@Param("appId")Integer appId,@Param("status")Integer status);
}
