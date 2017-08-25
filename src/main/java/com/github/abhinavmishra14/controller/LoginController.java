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

import java.text.ParseException;
import java.util.Date;

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
import com.github.abhinavmishra14.utils.RestAppUtils;

/**
 * The Class GetLoginController.
 */
@RestController
@RequestMapping("/")
public class LoginController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	/** The user profile service. */
	@Autowired
	private UserProfileService userProfileService;
	
	/**
	 * Gets the login.
	 *
	 * @param userId the user id
	 * @return the login
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET, produces="application/json")
	public RestWebAppResponse getLogin(final String ticket) {
		LOGGER.info("login invoked, ticket: {}", ticket);
		final RestWebAppResponse loginResp = new RestWebAppResponse();
		if (StringUtils.isEmpty(ticket)) {
			loginResp.setValidated(false);
			loginResp.getErrorMessages().put("Ticket", "Ticket must be provided!");
		} else {
			try {
				final String loginTicket = RestAppUtils.decodeBse64String(ticket);
				final String expDtStr = StringUtils.substringBeforeLast(loginTicket, ":");
				final Date expiryDate = RestAppUtils.getDateFromString(expDtStr);
				if (RestAppUtils.valiateExpiryDate(expiryDate)) {
					loginResp.setValidated(false);
					loginResp.getErrorMessages().put("Ticket", "Ticket has been expired!");
				} else {
					final String userId = StringUtils.substringAfterLast(loginTicket, ":");
					final UserInfo userInfo = userProfileService.getUserDetail(userId);
					loginResp.getResponse().put("Name", userInfo.getName());
					loginResp.getResponse().put("PinCode", userInfo.getPincode());
					loginResp.getResponse().put("EmailId", userInfo.getEmailId());
				}
			} catch (ParseException | RestWebAppException excp) {
				LOGGER.error("Exception occurred while login", excp);
				loginResp.getErrorMessages().put("ErrorMessage", excp.getMessage());
			}
		}
		return loginResp;
	}
}
