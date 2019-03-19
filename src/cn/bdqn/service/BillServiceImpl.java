package cn.bdqn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bdqn.dao.BillDao;
import cn.bdqn.entity.Bill;

import cn.bdqn.util.PageBean;

@Service("billService")
public class BillServiceImpl implements BillService {
	@Resource
	private BillDao billDao;

	@Override
	public PageBean<Bill> findByPage(String productName, Integer providerId,Integer isPayment,
			Integer pageNo, Integer pageSize) {
		PageBean<Bill> pageBean = new PageBean<Bill>();
		pageBean.setPageSize(pageSize);
		int totalCount = billDao.getTotalBillCount(productName, providerId,isPayment);
		pageBean.setTotalCount(totalCount);
		pageBean.setPageNo(pageNo);
		Integer from = (pageBean.getPageNo() - 1) * pageSize;
		List<Bill> pageList = billDao.findByUnion(productName, providerId,isPayment,
				from, pageSize);
		pageBean.setPageList(pageList);
		
		return pageBean;
	}

	@Override
	public int updateBill(Bill bill) {
		// TODO Auto-generated method stub
		return billDao.updateBill(bill);
	}

	@Override
	public int addBill(Bill bill) {
		// TODO Auto-generated method stub
		return billDao.addBill(bill);
	}

	@Override
	public Bill findByBid(Integer id) {
		
		return billDao.findByBid(id);
	}

	@Override
	public Bill findByBidAndPid(Integer Bid) {
		
		return billDao.findByBidAndPid(Bid);
	}

	@Override
	public int delById(Integer id) {
		
		return billDao.delById(id);
	}


}
