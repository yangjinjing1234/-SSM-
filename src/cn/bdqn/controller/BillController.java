package cn.bdqn.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import cn.bdqn.dao.BillDao;
import cn.bdqn.entity.Bill;
import cn.bdqn.entity.Provider;
import cn.bdqn.entity.Role;
import cn.bdqn.entity.User;
import cn.bdqn.service.BillService;
import cn.bdqn.service.ProviderService;
import cn.bdqn.service.RoleService;
import cn.bdqn.service.UserService;
import cn.bdqn.util.PageBean;

@Controller
@RequestMapping("/bill")
public class BillController {
	@Resource(name = "billService")
	private BillService billService;

	@Resource(name = "providerService")
	private ProviderService providerService;

	@RequestMapping("/billList.html")
	public String pageUser(
			@RequestParam(value = "queryProductName", required = false) String queryProductName,
			@RequestParam(value = "queryProviderId", required = false) Integer queryProviderId,
			@RequestParam(value = "queryIsPayment", required = false) Integer queryIsPayment,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			Model model) {
		// 查询所有的角色
		try {
			if (queryProductName != null && !queryProductName.equals(""))
				queryProductName = new String(
						queryProductName.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Provider> providerList = providerService.findAll();
		model.addAttribute("providerList", providerList);
		model.addAttribute("queryProductName", queryProductName);
		model.addAttribute("queryProviderId", queryProviderId);
		if (pageNo == null) {
			pageNo = 1;
		}
		int pageSize = 5;
		PageBean<Bill> pageBean = billService.findByPage(queryProductName,
				queryProviderId, queryIsPayment, pageNo, pageSize);
		model.addAttribute("pageBean", pageBean);
		return "billlist";
	}

	// 显示新增页面
	@RequestMapping("/billadd.html")
	public String showadd(@ModelAttribute("bill") Bill bill) {
		return "billadd";
	}

	// ajax处理下拉列表
	@RequestMapping("/providerList")
	@ResponseBody
	public Object showprovider() {
		List<Provider> ProviderList = providerService.findAll();
		return ProviderList;
	}

	// 处理新增
	@RequestMapping("/doAddBill")
	public String doAdd(Bill bill, HttpSession session) {
		User userlogin = (User) session.getAttribute("userLogin");
		bill.setCreatedBy(userlogin.getId());
		bill.setCreationDate(new Date());
		int ret = billService.addBill(bill);
		if (ret > 0) {
			return "redirect:/bill/billList.html";
		} else {
			return "redirect:/bill/billadd.html";
		}
	}

	// 显示订单信息
	@RequestMapping("/showBillInformation.html")
	public String showBillInformation(@RequestParam("billid") Integer billid,
			Model model) {
		Bill bill = billService.findByBidAndPid(billid);
		model.addAttribute("bill", bill);
		return "billview";
	}

	@RequestMapping("/showModify.html")
	public String showModify(@RequestParam("billid") Integer billid, Model model) {
		Bill bill = billService.findByBid(billid);
		model.addAttribute("bill", bill);
		return "billmodify";
	}

	@RequestMapping("/modifyBill")
	public String modifyBill(Bill bill, HttpSession session) {
		User user = (User) session.getAttribute("userLogin");
		bill.setModifyBy(user.getId());
		bill.setModifyDate(new Date());
		int ret = billService.updateBill(bill);
		if (ret > 0) {
			return "redirect:/bill/billList.html";
		} else {
			return "redirect:/bill/showModify.html";
		}
	}
	//ajax处理删除订单
	@RequestMapping("/delBill/{bid}")
	@ResponseBody
	public Object delBill(@PathVariable("bid") Integer bid){
		Map<String, Object> map=new HashMap<String, Object>();
		Bill bill=billService.findByBid(bid);
		if(bill==null){
			map.put("delResult", "notexist");
		}else{
			int ret=billService.delById(bid);
			if(ret>0){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "false");
			}
		}
		return map;
	}
}
