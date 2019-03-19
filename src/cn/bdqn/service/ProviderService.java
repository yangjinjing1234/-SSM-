package cn.bdqn.service;

import java.util.List;

import cn.bdqn.entity.Provider;
import cn.bdqn.util.PageBean;

public interface ProviderService {
	List<Provider> findAll();
	
	PageBean<Provider> findByPage(String proCode,String proName,Integer pageNo,Integer pageSize);
	
	Provider findById(Integer id);
	
	int delProvider(Integer id);
	
	int updateProvider(Provider provider);
	
	int addProvider(Provider provider);
}
