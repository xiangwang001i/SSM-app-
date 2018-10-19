package cn.appInfo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.appInfo.entity.DevUser;
import cn.appInfo.service.DevUserService;
import cn.appInfo.utils.Constants;

@Controller
public class userLoginController {
	@Resource
	private DevUserService devUserService;
	//显示登录页面
	@RequestMapping(value="/devlogin.html",method=RequestMethod.GET)
	public String devLogin(){
		return "devlogin";
	}
	//处理登录
	@RequestMapping(value="/devlogin.html",method=RequestMethod.POST)
	public String dodevLogin(String devCode,String devPassword,HttpSession session,HttpServletRequest request){
		DevUser devUser = devUserService.login(devCode, devPassword);
		if(devUser==null){
			request.setAttribute("error", "用户名或密码错误");
			return "devlogin";
		}
		session.setAttribute(Constants.USER_SESSION, devUser);
		return "redirect:/sys/devindex.html";
	}
	@RequestMapping(value="/devUser/logout.html")
	public String logout(HttpSession session){
		if(Constants.USER_SESSION!=null){
			session.removeAttribute(Constants.USER_SESSION);
			return "redirect:/devlogin.html";
		}
		return "devjsp/devindex";
	}
	//显示首页
	@RequestMapping(value="/sys/devindex.html")
	public String frame(){
		return "devjsp/devindex";
	}
}
