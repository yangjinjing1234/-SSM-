package cn.bdqn.service;



import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.bdqn.dao.UserDao;
import cn.bdqn.entity.User;
import cn.bdqn.util.PageBean;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao ;
	

	@Override
	public List<User> findByName(String name) {
		return userDao.findByName(name);
	}

	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public User findLogin(String userCode, String userPassword) {
		User user = userDao.findByCode(userCode);
		if(user!=null&&user.getUserPassword().equals(userPassword)){
			return user;
		}
		return null;
	}

	@Override
	public PageBean<User> findByPage(String userName, Integer roleId,
			Integer pageNo, Integer pageSize) {
		PageBean<User> pageBean = new PageBean<User>();
		pageBean.setPageSize(pageSize);
		int totalCount = userDao.getTotalCount(userName,roleId);
		pageBean.setTotalCount(totalCount);
		pageBean.setPageNo(pageNo);
		Integer from = (pageBean.getPageNo()-1)*pageSize ;
		List<User> pageList = userDao.findByConditions(userName, roleId, from , pageSize);
		pageBean.setPageList(pageList );
		return pageBean;
	}

	@Override
	public boolean checkUserCode(String userCode) {
		User user = userDao.findByCode(userCode);
		if(user!=null){
			return true;
		}
		return false;
	}

	@Override
	public User findByUid(Integer uid) {
		// TODO Auto-generated method stub
		return userDao.findByUid(uid);
	}

	@Override
	public int delUserById(Integer uid) {
		// TODO Auto-generated method stub
		return userDao.delUserById(uid);
	}

	@Override
	public boolean checkUserPassword(Integer id, String userPassword) {
		User user=userDao.findByUid(id);
		if(user.getUserPassword().equals(userPassword)){
			return true;
		}
		return false;
	}

	
}
