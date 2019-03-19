package cn.bdqn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.entity.Provider;

public interface ProviderDao {
	List<Provider> findAll();

	List<Provider> findByCondition(@Param("proCode") String proCode,
			@Param("proName") String proName, @Param("from") Integer from,
			@Param("pageSize") Integer pageSize);
	
	int totalCountById(@Param("proCode") String proCode,
			@Param("proName") String proName);
	
	Provider findById(Integer id);
	
	int delProvider(Integer id);
	
	int updateProvider(Provider provider);
	
	int addProvider(Provider provider);
}
