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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.exception.RestWebAppException;
import com.github.abhinavmishra14.form.RestWebAppResponse;
import com.github.abhinavmishra14.form.UserRegistration;
import com.github.abhinavmishra14.service.UserProfileService;

/**
 * The Class UserRegistrationController.
 */
@RestController
@RequestMapping("/")
public class UserRegistrationController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

	/** The user profile service. */
	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Register.
	 *
	 * @param inputData
	 *            the input data
	 * @return the string
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public RestWebAppResponse register(@RequestBody final UserRegistration userForm) {
		LOGGER.info("register invoked, inputData: {}", userForm);
		final RestWebAppResponse regResp = new RestWebAppResponse();
		if (StringUtils.isEmpty(userForm.getName())) {
			regResp.setValidated(false);
			regResp.getErrorMessages().put("Name", "Name must be provided!");
		} else if (StringUtils.isEmpty(userForm.getEmailId())) {
			regResp.setValidated(false);
			regResp.getErrorMessages().put("EmailId", "EmailId must be provided!");
		} else if (userForm.getPincode() == 0) {
			regResp.setValidated(false);
			regResp.getErrorMessages().put("Pincode", "Pincode must be provided!");
		} else {
			try {
				final String userId = userProfileService.registerUser(userForm);
				regResp.getResponse().put("userId", userId);
			} catch (RestWebAppException excp) {
				LOGGER.error("Exception occurred while registering the user", excp);
				regResp.getErrorMessages().put("ErrorMessage", excp.getMessage());
			}
		}
		return regResp;
	}
}
