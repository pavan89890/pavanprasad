package com.pavan.util;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	private static JavaMailSender staticjavaMailSender;

	public static final String FROM_EMAIL = "pavan89890@gmail.com";

	@Autowired
	private JavaMailSender javaMailSender;

	@PostConstruct
	public void init() {
		EmailUtil.staticjavaMailSender = this.javaMailSender;
	}

	public static void sendEmail(String toEmails[], String subject, String body) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(toEmails);

		msg.setSubject(subject);
		msg.setText(body);

		try {
			staticjavaMailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void sendMailWithAttachment(String toEmails, String subject, String body, List<File> filesToAttach)
			throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipients(Message.RecipientType.TO, toEmails);
				mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));
				mimeMessage.setSubject(subject);

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

				for (File fileToAttach : filesToAttach) {
					FileSystemResource file = new FileSystemResource(fileToAttach);
					helper.addAttachment(MimeUtility.encodeText(file.getFilename()), file);
				}
				helper.setText(body, true);
			}
		};

		try {
			staticjavaMailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			throw e;
		}
	}

}
