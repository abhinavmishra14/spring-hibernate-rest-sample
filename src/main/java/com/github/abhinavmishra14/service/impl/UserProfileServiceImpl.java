/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2017. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abhinavmishra14.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abhinavmishra14.dao.UserProfileDao;
import com.github.abhinavmishra14.exception.RestWebAppException;
import com.github.abhinavmishra14.form.UserRegistration;
import com.github.abhinavmishra14.model.UserInfo;
import com.github.abhinavmishra14.service.UserProfileService;

/**
 * The Class UserProfileServiceImpl.
 */
@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	/** The user registration dao. */
	@Autowired
	private UserProfileDao userProfileDao;

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.service.UserProfileService#registerUser(com.github.abhinavmishra14.form.UserRegistration)
	 */
	@Override
	public String registerUser(final UserRegistration userForm) throws RestWebAppException {
		LOGGER.info("registerUser invoked..");
		return userProfileDao.registerUser(userForm.getName(), userForm.getPincode(), userForm.getEmailId());
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.service.UserProfileService#getUserDetail(java.lang.String)
	 */
	@Override
	public UserInfo getUserDetail(final String userId) throws RestWebAppException {
		LOGGER.info("getUserDetail invoked..");
		return userProfileDao.getUserDetail(userId);
	}
}
