package cn.bdqn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.entity.User;

public interface UserDao {

	User findByUid(Integer uid);
	
	//分页查询对应集合
	List<User> findByConditions(
			@Param("userName")
			String userName,
			@Param("roleId")
			Integer roleId,
			@Param("from")
			Integer from,
			@Param("pageSize")
			Integer pageSize
			);
	//分页的总数量
	int getTotalCount(
			@Param("userName")
			String userName, 
			@Param("roleId")
			Integer roleId);


	User findByCode(String userCode);
	
	List<User> findByName(String name);

	int addUser(User user);

	int updateUser(User user);

	int delUserById(Integer id);
	
}
