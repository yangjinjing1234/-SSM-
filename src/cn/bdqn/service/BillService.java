package cn.bdqn.service;

import cn.bdqn.entity.Bill;


import cn.bdqn.util.PageBean;

public interface BillService {
	PageBean<Bill> findByPage(String productName, Integer providerId, Integer isPayment,
			Integer pageNo,Integer pageSize);
	
	int updateBill(Bill bill);
	
	int addBill(Bill bill);
	
	Bill findByBid(Integer id);
	
	Bill findByBidAndPid(Integer Bid);
	
	int delById(Integer id);
}
