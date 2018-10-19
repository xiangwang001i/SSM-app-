package cn.appInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Version;
import cn.appInfo.entity.BackendUser;

public interface BackendUserDao {
	public BackendUser login(@Param("userCode")String userCode);
	public List<AppInfo> selectAppInfoByStatus(@Param(value="softwareName")String querySoftwareName,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="devId")Integer devId,
			@Param(value="from")Integer currentPageNo,
			@Param(value="pageSize")Integer pageSize);
	public int totalCount(@Param(value="softwareName")String querySoftwareName,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="devId")Integer devId);
	public AppInfo getView(@Param("appid")Integer appid);
	public App_Version getView2(@Param("versionId")Integer versionId);
	public int check(@Param("id")Integer id,@Param("status")Integer status);
}
