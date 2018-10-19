package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.appInfo.entity.AppInfo;
import cn.appInfo.entity.App_Category;
import cn.appInfo.entity.Data_Dictionary;
import cn.appInfo.service.DevUserService;

public class Method {
	private Logger logger = Logger.getLogger(Method.class);
	@Test
	public void TestMethod(){
//		ApplicationContext cc = new ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
//		DevUserService d = (DevUserService)cc.getBean("devUserService");
//		for(AppInfo dd : d.findAllInfo(10)){
//			logger.info(dd.getId()+dd.getSoftwareName());
//		}
		Date date = new Date();
		System.out.println(date);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(date);
        System.out.println(dateNowStr);
	}
}
