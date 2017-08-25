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
package com.github.abhinavmishra14.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PropertyReader.
 */
public class PropertyReader {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReader.class);

	/** The Constant KEYS. */
	private final static Properties KEYS = new Properties();

	/** The Constant instance. */
	private static final PropertyReader INSTANCE = new PropertyReader();

	/**
	 * Instantiates a new keys provider.
	 */
	private PropertyReader() {
		super();
		init();
	}

	/**
	 * Gets the single instance of KeysProvider.
	 *
	 * @return single instance of KeysProvider
	 */
	public static PropertyReader getInstance() {
		return INSTANCE;
	}

	/**
	 * Loads the properties from the alfresco-global.properties.
	 */
	private void init() {
		try (final InputStream inStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("config.properties")) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Loading access keys from 'config.properties'");
			}
			KEYS.load(inStream);
		} catch (IOException ioex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Exception getting the keys from config.properties ",ioex);
			}
		}
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public Properties getKeys() {
		return KEYS;
	}
	
	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public static String getProperty(final String key) {
		if(StringUtils.isBlank(key)){
			throw new IllegalArgumentException("Key must not be null!");
		}
		return KEYS.getProperty(key);
	}
}
