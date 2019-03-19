package cn.bdqn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.entity.Bill;

public interface BillDao {
	List<Bill> findByUnion(@Param("productName") String productName,
			@Param("providerId") Integer providerId,
			@Param("isPayment") Integer isPayment, @Param("from") Integer from,
			@Param("pageSize") Integer pageSize);

	int getTotalBillCount(@Param("productName") String productName,
			@Param("providerId") Integer providerId,
			@Param("isPayment") Integer isPayment
			);
	
	int updateBill(Bill bill);
	
	int addBill(Bill bill);
	
	Bill findByBid(Integer id);
	
	Bill findByBidAndPid(Integer Bid);
	
	int delById(Integer id);
}
