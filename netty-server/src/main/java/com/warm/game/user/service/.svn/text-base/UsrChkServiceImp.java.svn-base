package com.warm.game.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warm.game.user.dao.UserDao;
import com.warm.game.user.entity.User;

//这个service层注解对应@Autowired
@Service("usrChkServiceImp")
public class UsrChkServiceImp implements UsrChkService {

	@Resource
	public UserDao userDao;
	
	public boolean check(User user) throws Exception {
		boolean flag = false;
		if (user.getUserName().equals("zhj")) {
			flag = true;
		}
		//20170805这里有异常的问题显示了log和控制Exception的问题
//		userDao.addUser(user);
		return flag;
	}
}
