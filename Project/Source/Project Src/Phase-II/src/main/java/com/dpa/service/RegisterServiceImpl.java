package com.dpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpa.dao.RegisterDao;
import com.dpa.model.Register;

//calls the registerDao to insert user into the database
@Service
public class RegisterServiceImpl implements RegisterService{
	@Autowired
	RegisterDao registerDao;
	public int insertUser(Register register) {
		// TODO Auto-generated method stub
		int result = registerDao.insertUserDetails(register);
		return result;
	}

}
