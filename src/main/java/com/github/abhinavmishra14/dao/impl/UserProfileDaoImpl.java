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
package com.github.abhinavmishra14.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.abhinavmishra14.dao.UserProfileDao;
import com.github.abhinavmishra14.exception.RestWebAppException;
import com.github.abhinavmishra14.model.UserInfo;
import com.github.abhinavmishra14.utils.RestAppUtils;
import com.github.abhinavmishra14.utils.HibernateUtil;

/**
 * The Class UserProfileDaoImpl.
 */
@Repository("userProfileDao")
public class UserProfileDaoImpl implements UserProfileDao {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.dao.UserProfileDao#registerUser(java.lang.String, int, java.lang.String)
	 */
	@Override
	public String registerUser(final String name, final int pincode, final String emailId) throws RestWebAppException {
		LOGGER.info("registerUser invoked..");
		final UserInfo usrInfo = new UserInfo();
		usrInfo.setName(name);
		usrInfo.setPincode(pincode);
		usrInfo.setEmailId(emailId);
		final String userID = RestAppUtils.getRandomString(emailId.length());
		usrInfo.setUserId(userID);

		final SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(usrInfo);
			transaction.commit();
		} catch (HibernateException excp) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOGGER.error("Failed to save userInfo", excp);
			throw new RestWebAppException("Failed to register user", excp);
		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.close();
			}
		}
		return userID;
	}

	/* (non-Javadoc)
	 * @see com.github.abhinavmishra14.dao.UserProfileDao#getUserDetail(java.lang.String)
	 */
	@Override
	public UserInfo getUserDetail(final String userId) throws RestWebAppException {
		LOGGER.info("getUserDetail invoked..");
		final SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		UserInfo userInfo = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			userInfo = (UserInfo) session.get(UserInfo.class, userId);
			transaction.commit();
		} catch (HibernateException excp) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOGGER.error("Failed to get userInfo", excp);
			throw new RestWebAppException("Failed to get userInfo", excp);
		} finally {
			if (session != null && session.isOpen()) {
				session.flush();
				session.close();
			}
		}
		return userInfo;
	}
}
