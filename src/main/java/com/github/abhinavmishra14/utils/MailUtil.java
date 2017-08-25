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

import java.util.Calendar;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MailUtil.
 */
public final class MailUtil {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

	/**
	 * Send mail.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param content
	 *            the content
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static boolean sendMail(final String to, final String subject, final String content) {
		LOGGER.info("sendMail invoked.");
		boolean sendMailFlag = false;
		try {
			final Message message = mailProperties();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			final Multipart multipart = new MimeMultipart("alternative");
			final BodyPart htmlMessageBodyPart = new MimeBodyPart();
			htmlMessageBodyPart.setContent(content, "text/html");
			multipart.addBodyPart(htmlMessageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			LOGGER.debug("Email Message Sent..");
			sendMailFlag = true;
		} catch (Exception excp) {
			LOGGER.error("Exception occurred while sending email", excp);
		}
		return sendMailFlag;
	}

	/**
	 * Mail properties.
	 *
	 * @return the message
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static Message mailProperties() throws AddressException, MessagingException {
		final Properties props = new Properties();
		final String host = PropertyReader.getProperty("mailHostName");
		final String port = PropertyReader.getProperty("mailPort");
		final String fromAddress = PropertyReader.getProperty("fromAddress");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", PropertyReader.getProperty("mailPort"));
		final Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PropertyReader.getProperty("smtpUserName"),
						PropertyReader.getProperty("smtpUserPassword"));
			}
		});
		final Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		return message;
	}

	/**
	 * Mail authenticate.
	 *
	 * @return the message
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static Message mailAuthenticate() throws AddressException, MessagingException {
		final String fromAddress = PropertyReader.getProperty("fromAddress");
		final Properties props = new Properties();
		props.put("mail.smtp.host", PropertyReader.getProperty("mailHostNameGmail"));
		props.put("mail.smtp.socketFactory.port", PropertyReader.getProperty("mailPortGmail"));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", PropertyReader.getProperty("mailPortGmail"));
		final Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PropertyReader.getProperty("smtpUserName"),
						PropertyReader.getProperty("smtpUserPassword"));
			}
		});
		final Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		return message;
	}

	/**
	 * Send mail via gmail.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param content
	 *            the content
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static boolean sendMailViaGmail(final String to, final String subject, final String content) {
		LOGGER.info("sendMailViaGmail invoked..");
		boolean sendMailFlag = false;
		try {
			final Message message = mailAuthenticate();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			final Multipart multipart = new MimeMultipart("alternative");
			final BodyPart htmlMessageBodyPart = new MimeBodyPart();
			htmlMessageBodyPart.setContent(content, "text/html");
			multipart.addBodyPart(htmlMessageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			LOGGER.info("Email Message Sent..");
			sendMailFlag = true;
		} catch (Exception excp) {
			LOGGER.error("Exception occurred while sending email", excp);
		}
		return sendMailFlag;
	}
	
	/**
	 * Prepare content.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	public static String prepareContent(final String userId) {
		final String expiryDate =  RestAppUtils.getExpiryDate(Calendar.MINUTE, 10);
		final String ticket = expiryDate + ":" + userId;
		final StringBuilder strBuild = new StringBuilder();
		strBuild.append("Hi ").append(userId).append("<br/>").append(PropertyReader.getProperty("mailContent"))
			.append(PropertyReader.getProperty("mailContentLink"))
			.append("<a href='")
			.append(PropertyReader.getProperty("login.url"))
			.append(RestAppUtils.encodeBytesToBase64String(ticket.getBytes()))
			.append("'>Login here</a>").append(PropertyReader.getProperty("mailContentThankStmt"));
		return strBuild.toString();
	}

	/**
	 * Instantiates a new mail util.
	 */
	private MailUtil () {
		super();
	}
}
