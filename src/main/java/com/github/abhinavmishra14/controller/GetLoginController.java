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
package com.github.abhinavmishra14.controller;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.exception.RestWebAppException;
import com.github.abhinavmishra14.form.RestWebAppResponse;
import com.github.abhinavmishra14.model.UserInfo;
import com.github.abhinavmishra14.service.UserProfileService;
import com.github.abhinavmishra14.utils.MailUtil;
import com.github.abhinavmishra14.utils.PropertyReader;

/**
 * The Class GetLoginController.
 */
@RestController
@RequestMapping("/")
public class GetLoginController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GetLoginController.class);
	
	/** The user profile service. */
	@Autowired
	private UserProfileService userProfileService;
	
	/**
	 * Gets the login.
	 *
	 * @param userId the user id
	 * @return the login
	 */
	@RequestMapping(value="/getLogin", method = RequestMethod.GET, produces="application/json")
	public RestWebAppResponse getLogin(final String userId) {
		LOGGER.info("getLogin invoked, userId: {}", userId);
		final RestWebAppResponse getLoginResp = new RestWebAppResponse();
		if (StringUtils.isEmpty(userId)) {
			getLoginResp.setValidated(false);
			getLoginResp.getErrorMessages().put("UserId", "UserId must be provided!");
		} else {
			try {
				final UserInfo userInfo = userProfileService.getUserDetail(userId);
				if (userInfo != null) {
					final String emailId = userInfo.getEmailId();
					LOGGER.info("Sending login info to user at: {}", emailId);
					if ("gmail".equals(PropertyReader.getProperty("mailMode"))) {
						MailUtil.sendMailViaGmail(emailId, PropertyReader.getProperty("mailSubject"), MailUtil.prepareContent(userId));
					} else {
						MailUtil.sendMail(emailId, PropertyReader.getProperty("mailSubject"), MailUtil.prepareContent(userId));
					}
					getLoginResp.getResponse().put("result", "Login details has been sent to email: "+emailId);
				} else {
					getLoginResp.getResponse().put("result", "Could not find user information for given userId: "+userId);
				}
			} catch (RestWebAppException | MessagingException excp) {
				LOGGER.error("Exception occurred while getting login details", excp);
				getLoginResp.getErrorMessages().put("ErrorMessage", excp.getMessage());
			}
		}
		return getLoginResp;
	}
}
