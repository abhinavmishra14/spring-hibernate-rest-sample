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
package com.github.abhinavmishra14.dao;

import com.github.abhinavmishra14.exception.RestWebAppException;
import com.github.abhinavmishra14.model.UserInfo;

/**
 * The Interface UserProfileDao.
 */
public interface UserProfileDao {

	/**
	 * Register user.
	 *
	 * @param name the name
	 * @param pincode the pincode
	 * @param emailId the email id
	 * @return the string
	 * @throws RestWebAppException the rest web app exception
	 */
	String registerUser(final String name, final int pincode, final String emailId) throws RestWebAppException;
	
	/**
	 * Gets the user detail.
	 *
	 * @param userId the user id
	 * @return the user detail
	 * @throws RestWebAppException the rest web app exception
	 */
	UserInfo getUserDetail(final String userId) throws RestWebAppException;
}
