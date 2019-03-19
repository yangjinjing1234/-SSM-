package cn.bdqn.service;

import java.util.List;



import cn.bdqn.entity.User;
import cn.bdqn.util.PageBean;

public interface UserService {

	List<User> findByName(String name);

	User findLogin(String userCode, String userPassword);
	
	//检测用户是否存在
	boolean checkUserCode(String userCode);

	int addUser(User user);

	int updateUser(User user);

	PageBean<User> findByPage(String userName, Integer roleId, 
			Integer pageNo,Integer pageSize);

	User findByUid(Integer uid);
	
	int delUserById (Integer uid);
	
	boolean checkUserPassword(Integer id,String userPassword);
}
