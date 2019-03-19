package cn.bdqn.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.bdqn.dao.UserDao;
import cn.bdqn.entity.Role;
import cn.bdqn.entity.User;
import cn.bdqn.service.RoleService;
import cn.bdqn.service.UserService;
import cn.bdqn.util.PageBean;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "roleService")
	private RoleService roleService;

	// 单文件上传
	@RequestMapping("/doUpload.html")
	public String doUpload(@RequestParam(required = false) String userName,
			MultipartFile pic, HttpSession session) {
		System.out.println("上传人" + userName);
		String path = session.getServletContext()
				.getRealPath("/statics/upload");
		/*
		 * File dir = new File(path); System.out.println(path);
		 */
		String fileName = pic.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf('.'));
		String newName = UUID.randomUUID() + "_person" + suffix;
		File file = new File(path, newName);
		try {
			pic.transferTo(file);
			return "success";
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "upload";
		}
	}

	// 多文件上传
	@RequestMapping("doUpload2.html")
	public String doUpload2(@RequestParam(required = false) String userName,
			@RequestParam MultipartFile[] pic, HttpSession session) {
		System.out.println("上传人" + userName);
		String path = session.getServletContext()
				.getRealPath("/statics/upload");
		File dir = new File(path);
		for (MultipartFile mFile : pic) {
			String fileName = mFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
			List<String> listTypes = Arrays.asList("gif", "png", "jpg");
			if (listTypes.contains(suffix)) {
				String newName = UUID.randomUUID() + "_person" + suffix;
				File file = new File(dir, newName);
				try {
					mFile.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return "upload2";
			}
		}
		return "success";
	}

	// 显示修改
	@RequestMapping("/modify/{uid}")
	public String showUser(@PathVariable("uid") Integer uid, Model model) {
		User user = userService.findByUid(uid);
		model.addAttribute("user", user);
		return "usermodify";
	}

	// 处理修改
	@RequestMapping("/updateuser.html")
	public String updateUser(User user, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		user.setModifyBy(userLogin.getId());
		user.setModifyDate(new Date());
		int ret = userService.updateUser(user);
		if (ret > 0) {
			return "success";
		} else {
			return "useradd";
		}
	}

	// Ajax的结果
	@RequestMapping(value = "/getrolelist", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	// @ResponseBody注解表示返回的是AJAX 不会变成jsp
	@ResponseBody
	public Object getrolelist() {
		List<Role> roleList = roleService.findAll();
		return roleList;
	}

	@RequestMapping(value = "/checkUserCode")
	@ResponseBody
	public Object checkUserCode(@RequestParam String userCode) {
		// System.out.println(userCode);
		Map<String, Object> map = new HashMap<String, Object>();
		// 调用业务层查询是否存在
		if (userService.checkUserCode(userCode)) {
			map.put("userCode", "exist");
		} else {
			map.put("userCode", "notExist");
		}
		return map;
	}

	// 显示新增用户
	@RequestMapping("/useradd.html")
	public String showAdd(@ModelAttribute("user") User user) {
		return "useradd";
	}

	// 处理新增用户
	@RequestMapping("/doAdd.html")
	public String doAdd(User user, HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		user.setCreatedBy(userLogin.getId());
		user.setCreationDate(new Date());
		int ret = userService.addUser(user);
		if (ret > 0) {
			return "success";
		} else {
			return "useradd";
		}

	}

	// 注销
	@RequestMapping("/logout.html")
	public String logout(HttpSession session) {
		session.removeAttribute("userLogin");
		// session.invalidate();
		// return "redirect:/login.html";
		return "logoutsuccess";
	}

	// 处理登录
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String userCode,
			@RequestParam String userPassword, HttpSession session, Model model) {
		User userLogin = userService.findLogin(userCode, userPassword);
		if (userLogin != null) {
			session.setAttribute("userLogin", userLogin);

			return "redirect:/main.html";
		} else {
			String msg = "用户名或密码有误!";
			model.addAttribute("msg", msg);
			return "login";
		}
	}

	// 查询分页
	@RequestMapping("/userList.html")
	public String pageUser(
			@RequestParam(value = "queryname", required = false) String userName,
			@RequestParam(value = "queryUserRole", required = false) Integer roleId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			Model model) {
		// 查询所有的角色
		try {
			if (userName != null && !userName.equals(""))
				userName = new String(userName.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", userName);
		model.addAttribute("queryUserRole", roleId);
		if (pageNo == null) {
			pageNo = 1;
		}
		int pageSize = 5;
		PageBean<User> pageBean = userService.findByPage(userName, roleId,
				pageNo, pageSize);

		model.addAttribute("pageBean", pageBean);
		return "userlist";
	}

	// 通过id删除用户信息
	@RequestMapping("/del/{uid}")
	@ResponseBody
	public Object delById(@PathVariable("uid") Integer uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUid(uid);
		if (user == null) {
			map.put("delResult", "notexist");
		} else {
			int ret = userService.delUserById(uid);
			if (ret > 0) {
				map.put("delResult", "true");
			} else {
				map.put("delResult", "false");
			}
		}
		return map;
	}

	// 通过id查询所有
	@RequestMapping("/find/{uid}")
	@ResponseBody
	public Object findById(@PathVariable("uid") Integer uid) {
		User user = userService.findByUid(uid);
		return user;
	}

	// 显示修改密码
	@RequestMapping("/pwdmodify.html")
	public String pwdmodify(HttpSession session) {
		return "pwdmodify";
	}

	// ajax处理密码
	@RequestMapping("/checkPassword")
	@ResponseBody
	public Object checkPassword(HttpSession session,
			@RequestParam("oldpassword") String oldpassword) {
		User userLogin = (User) session.getAttribute("userLogin");
		Map<Object, String> map = new HashMap<Object, String>();
		if (userLogin == null) {
			map.put("result", "sessionerror");
		} else if (oldpassword == "") {
			map.put("result", "error");
		} else {
			Integer id = userLogin.getId();
			if (userService.checkUserPassword(id, oldpassword)) {
				map.put("result", "true");
			} else {
				map.put("result", "false");
			}
		}
		return map;
	}

	@RequestMapping("/updatePassword")
	public String updatePassword(User user, HttpSession session,
			@RequestParam("newpassword") String newpassword) {
		User userlogin = (User) session.getAttribute("userLogin");
		user.setId(userlogin.getId());
		user.setUserPassword(newpassword);
		int ret = userService.updateUser(user);
		if (ret > 0) {
			return "success";
		} else {
			return "pwdmodify";
		}
	}
}
