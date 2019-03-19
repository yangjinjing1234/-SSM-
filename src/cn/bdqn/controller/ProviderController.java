package cn.bdqn.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bdqn.entity.Provider;
import cn.bdqn.entity.User;
import cn.bdqn.service.ProviderService;
import cn.bdqn.util.PageBean;

@RequestMapping("/provider")
@Controller
public class ProviderController {
	@Resource(name = "providerService")
	private ProviderService providerService;

	@RequestMapping("/providerList.html")
	public String pageUser(
			@RequestParam(value = "queryProCode", required = false) String queryProCode,
			@RequestParam(value = "queryProName", required = false) String queryProName,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			Model model) {
		// 查询所有的角色
		try {
			if (queryProCode != null && !queryProCode.equals("")) {
				queryProCode = new String(queryProCode.getBytes("ISO-8859-1"),
						"utf-8");
			}
			if (queryProName != null && !queryProName.equals("")) {
				queryProName = new String(queryProName.getBytes("ISO-8859-1"),
						"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("queryProCode", queryProCode);
		model.addAttribute("queryProName", queryProName);
		if (pageNo == null) {
			pageNo = 1;
		}
		int pageSize = 5;
		PageBean<Provider> pageBean = providerService.findByPage(queryProCode,
				queryProName, pageNo, pageSize);
		model.addAttribute("pageBean", pageBean);
		return "providerlist";
	}

	// 查询单个供应商
	@RequestMapping("/showProvider.html")
	public String showProvider(@RequestParam("id") Integer id, Model model) {
		Provider provider = providerService.findById(id);
		model.addAttribute("provider", provider);
		return "providerview";
	}

	// ajax删除供应商
	@RequestMapping("/delProvider/{pid}")
	@ResponseBody
	public Object delProvider(@PathVariable("pid") Integer pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Provider provider = providerService.findById(pid);
		if (provider == null) {
			map.put("delResult", "notexist");
		} else {
			int ret = providerService.delProvider(pid);
			if (ret > 0) {
				map.put("delResult", "true");
			} else {
				map.put("delResult", "false");
			}
		}
		return map;
	}

	@RequestMapping("/showModify.html")
	public String showModify(@RequestParam("id") Integer id, Model model) {
		Provider provider = providerService.findById(id);
		model.addAttribute("provider", provider);
		return "providermodify";
	}

	@RequestMapping("/doModify")
	public String doModify(Provider provider, HttpSession session) {
		User userlogin = (User) session.getAttribute("userLogin");
		provider.setModifyBy(userlogin.getId());
		provider.setModifyDate(new Date());
		int ret = providerService.updateProvider(provider);
		if (ret > 0) {
			return "redirect:/provider/providerList.html";
		} else {
			return "redirect:/provider/showModify.html";
		}
	}

	// 显示新增页面
	@RequestMapping("/showCreated.html")
	public String showCreated(@ModelAttribute("provider") Provider provider) {
		return "provideradd";
	}
	
	@RequestMapping("/doCreated")
	public String doCreated(Provider provider,HttpSession session) {
		User userlogin = (User) session.getAttribute("userLogin");
		provider.setCreatedBy(userlogin.getId());
		provider.setCreationDate(new Date());
		int ret=providerService.addProvider(provider);
		if (ret > 0) {
			return "redirect:/provider/providerList.html";
		} else {
			return "redirect:/provider/showCreated.html";
		}
	}
	
}
