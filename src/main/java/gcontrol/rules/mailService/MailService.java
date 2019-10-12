package gcontrol.rules.mailService;



import java.util.HashMap;

/**
 * This package containes classes to send notifications and alerts by mail.
 */
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import gcontrol.rules.configuration.VelocityTemplate;
import gcontrol.rules.initiator.RuleIntiatorV2;

/**
 * MailService class contains method to send mail.
 * 
 * @author sachin Nahar
 * @version 1.0
 * @since 2018-04-11
 */
public class MailService {

	/**
	 * Returns true if mail is sent otherwise return false
	 * 
	 * @param subject
	 *            String value representing mail subject
	 * 
	 * @param mailContent
	 *            String value representing mail content
	 * 
	 * @return true if mail sent otherwise returns false
	 * 
	 * @throws MessagingException
	 *             throw MessagingException if exception occured while sending mail
	 */
	public Boolean sendMail(String subject, String mailContent,String mailId,HashMap<String, String> object) {// throws Exception
		Boolean isSent = false;
		
		String templateData = new VelocityTemplate().readTemplate(System.getenv("Rule_Engine")+"/template", "notificationTemplate.vm",object);
		if (mailId!=null) {
			// Get the session object
			Properties props = new Properties();
			props.put("mail.smtp.host", RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostName());
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.port", RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostPortNumber());
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostPortNumber());
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostUserName(), RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostPassword());
				}
			});

			// Compose the message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(RuleIntiatorV2.FILE_CONFIGURATION.getSmtpHostUserName()));
				String[] toEmail = mailId.split(",");				
				InternetAddress[] addressTo = new InternetAddress[toEmail.length];
				for (int i = 0; i < toEmail.length; i++) {
					addressTo[i] = new InternetAddress(toEmail[i]);
				}
				message.setRecipients(Message.RecipientType.TO, addressTo);
				// Send Email only cc email ids			
				message.setSubject(subject);
				message.setText(mailContent);
				BodyPart messageBodyPart = new MimeBodyPart();
				// Fill the message
				// messageBodyPart.setText(message);
				messageBodyPart.setContent(templateData, "text/html");				
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);				
				// send the message
				Transport.send(message);
				System.out.println("mail sent to:: "+message.getAllRecipients()[0]);
				isSent = true;
			} 
			catch (Exception exception)
			{
			exception.printStackTrace();
			}
			}
		return isSent;
	}
}
