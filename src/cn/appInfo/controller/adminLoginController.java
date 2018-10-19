package cn.appInfo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.appInfo.entity.BackendUser;
import cn.appInfo.service.BackendUserService;
import cn.appInfo.utils.Constants;

@Controller
public class adminLoginController {

	@Resource
	private BackendUserService backendUserService;
	@RequestMapping(value="/backend.html",method=RequestMethod.GET)
	public String main(){
		return "backendLogin";
	}
	@RequestMapping(value="/backendLogin.html",method=RequestMethod.POST)
	public String backendLogin(String userCode,String userPassword,HttpSession session,HttpServletRequest request){
		BackendUser backendUser = backendUserService.login(userCode, userPassword);
		if(backendUser==null){
			request.setAttribute("error", "用户名或密码错误");
			return "backendLogin";
		}
		session.setAttribute(Constants.USER_SESSION, backendUser);
		return "redirect:/sys2/backendindex.html";
	}
	@RequestMapping(value="/backendUser/logout.html")
	public String logout(HttpSession session){
		if(Constants.USER_SESSION!=null){
			session.removeAttribute(Constants.USER_SESSION);
			return "redirect:/backend.html";
		}
		return "backendjsp/backendindex";
	}
	//显示首页
	@RequestMapping(value="/sys2/backendindex.html")
	public String frame(){
		return "backendjsp/backendindex";
	}
}
