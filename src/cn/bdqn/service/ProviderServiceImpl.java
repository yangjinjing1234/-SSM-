package cn.bdqn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bdqn.dao.ProviderDao;
import cn.bdqn.entity.Provider;
import cn.bdqn.util.PageBean;


@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

	@Resource
	private ProviderDao providerDao;
	@Override
	public List<Provider> findAll() {
	
		return providerDao.findAll();
	}
	
	
	@Override
	public PageBean<Provider> findByPage(String proCode, String proName,
			Integer pageNo, Integer pageSize) {
		PageBean<Provider> pageBean = new PageBean<Provider>();
		pageBean.setPageSize(pageSize);
		int totalCount = providerDao.totalCountById(proCode, proName);
		pageBean.setTotalCount(totalCount);
		pageBean.setPageNo(pageNo);
		Integer from = (pageBean.getPageNo() - 1) * pageSize;
		List<Provider> pageList = providerDao.findByCondition(proCode, proName, from, pageSize);
		pageBean.setPageList(pageList);
		return pageBean;
	}


	@Override
	public Provider findById(Integer id) {
		
		return providerDao.findById(id);
	}


	@Override
	public int delProvider(Integer id) {
		
		return providerDao.delProvider(id);
	}


	@Override
	public int updateProvider(Provider provider) {
		
		return providerDao.updateProvider(provider);
	}


	@Override
	public int addProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerDao.addProvider(provider);
	}

}
