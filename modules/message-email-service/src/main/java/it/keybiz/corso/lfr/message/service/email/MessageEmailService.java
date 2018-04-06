package it.keybiz.corso.lfr.message.service.email;

import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.petra.mail.MailEngine;

import it.keybiz.corso.lfr.message.api.Message;
import it.keybiz.corso.lfr.message.api.exception.MessageException;
import it.keybiz.corso.lfr.message.service.email.configuration.MessageEmailServiceConfiguration;

/**
 * @author amusarra
 */
@Component(
	configurationPid = "it.keybiz.corso.lfr.message.service.email.configuration.MessageEmailServiceConfiguration",
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = Message.class
)
public class MessageEmailService implements Message {

	@Override
	public String sendMessage(String message) throws MessageException {
		
		return sendMessage(message, _messageEmailServiceConfiguration.reportTo());
	}

	@Override
	public String sendMessage(String message, String destination) throws MessageException {
		
		if (_log.isInfoEnabled()) {
			_log.info("Sending text message via email to "
					+ destination
					+ "...");
		}

		if (Validator.isNull(message)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Sending text message via email to "
						+ destination
						+ " Text message can not be null!...[ERROR]");
			}
			throw new MessageException("Text message can not be null!");
		}

		if (Validator.isNull(destination)) {
			destination = _messageEmailServiceConfiguration.reportTo();
		}

		if (!Validator.isEmailAddress(destination)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Sending text message via email to "
						+ destination
						+ " Destination address must be email!...[ERROR]");
			}
			throw new MessageException("Destination address must be email!");
		}

		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;

		try {
			fromAddress = new InternetAddress(
				_messageEmailServiceConfiguration.from());
			toAddress = new InternetAddress(
				destination);

			MailMessage mailMessage = new MailMessage();

			mailMessage.setTo(toAddress);
			mailMessage.setFrom(fromAddress);
			mailMessage.setSubject(
				_messageEmailServiceConfiguration.emailSubject());
			mailMessage.setBody(message);

			if (_messageEmailServiceConfiguration.async()) {
				MailServiceUtil.sendEmail(mailMessage);
			} else {
				MailEngine.send(mailMessage);
				
			}

			if (_log.isInfoEnabled()) {
				_log.info("Sending text message via email to "
						+ destination
						+ "...[OK]");
			}
		} catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Sending text message via email to "
						+ destination
						+ "...[ERROR]", e);
			}
			
			throw new MessageException(e.getMessage());
		}
		
		return PortalUUIDUtil.generate();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_messageEmailServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				MessageEmailServiceConfiguration.class,
				properties);
	}

	private static Log _log = LogFactoryUtil.getLog(
			MessageEmailService.class);
	
	private volatile MessageEmailServiceConfiguration
	_messageEmailServiceConfiguration;

}