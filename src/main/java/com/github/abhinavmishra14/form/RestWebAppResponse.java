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
package com.github.abhinavmishra14.form;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class RestWebAppResponse.
 */
public class RestWebAppResponse {

	/** The response. */
	private Map<String, Object> response; 
	
	/** The validated. */
	private boolean validated;
	
	/** The error messages. */
	private Map<String, String> errorMessages; 
	
	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	public Map<String, String> getErrorMessages() {
		if (this.errorMessages == null) {
			this.errorMessages = new ConcurrentHashMap<String, String> ();
		}
		return errorMessages;
	}

	/**
	 * Checks if is validated.
	 *
	 * @return true, if is validated
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Sets the validated.
	 *
	 * @param validated the new validated
	 */
	public void setValidated(final boolean validated) {
		this.validated = validated;
	}
	
	/**
	 * Sets the error messages.
	 *
	 * @param errorMessages the error messages
	 */
	public void setErrorMessages(final Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public Map<String, Object> getResponse() {
		if (this.response == null) {
			this.response = new ConcurrentHashMap<String, Object> ();
		}
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the response
	 */
	public void setResponse(final Map<String, Object> response) {
		this.response = response;
	}
}
