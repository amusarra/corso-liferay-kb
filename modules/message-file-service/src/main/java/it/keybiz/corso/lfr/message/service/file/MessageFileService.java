package it.keybiz.corso.lfr.message.service.file;

import java.io.IOException;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import it.keybiz.corso.lfr.message.api.Message;
import it.keybiz.corso.lfr.message.api.exception.MessageException;
import it.keybiz.corso.lfr.message.service.file.configuration.MessageFileServiceConfiguration;

/**
 * @author amusarra
 */
@Component(
	configurationPid = "it.keybiz.corso.lfr.message.service.file.configuration.MessageFileServiceConfiguration",
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = Message.class
)
public class MessageFileService implements Message {

	@Override
	public String sendMessage(String message) throws MessageException {
		if (Validator.isNull(message)) {
			throw new MessageException("Text message can not be null!");
		}
		
		return sendMessage(message, _messageFileServiceConfiguration.filepath());		
	}

	@Override
	public String sendMessage(String message, String destination) throws MessageException {
		if (_log.isInfoEnabled()) {
			_log.info("Writing text message to file "
					+ destination
					+ "...");
		}

		if (Validator.isNull(message)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Writing text message to file "
						+ destination
						+ " Text message can not be null!...[ERROR]");
			}
			throw new MessageException("Text message can not be null!");
		}

		if (Validator.isNull(destination)) {
			destination = _messageFileServiceConfiguration.filepath();
		}

		if (!Validator.isUri(destination)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Writing text message to file "
						+ destination
						+ " Destination must be path file!...[ERROR]");
			}
			throw new MessageException("Destination must be path file!");
		}

		String messageId = PortalUUIDUtil.generate();
		
		try {
			message = messageId 
					+ StringPool.PIPE 
					+ message 
					+ StringPool.RETURN_NEW_LINE;
			
			FileUtil.write(destination, message, true, true);

			if (_log.isInfoEnabled()) {
				_log.info("Writing text message to file "
						+ destination
						+ "...[OK]");
			}
		} catch (IOException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Writing text message via email to "
						+ destination
						+ "...[ERROR]", e);
			}
			throw new MessageException(e.getMessage());
		}

		return messageId;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_messageFileServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				MessageFileServiceConfiguration.class,
				properties);
	}

	private static Log _log = LogFactoryUtil.getLog(
			MessageFileService.class);
	
	private volatile MessageFileServiceConfiguration
	_messageFileServiceConfiguration;


}