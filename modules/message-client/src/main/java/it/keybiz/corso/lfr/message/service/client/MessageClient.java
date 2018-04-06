package it.keybiz.corso.lfr.message.service.client;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import it.keybiz.corso.lfr.message.api.Message;
import it.keybiz.corso.lfr.message.api.exception.MessageException;

/**
 * @author amusarra
 */
@Component(
		property = { 
				"osgi.command.function=sendMessageByEmail", 
				"osgi.command.function=sendMessageByFile", 
				"osgi.command.function=sendMessage", 
				"osgi.command.scope=message"
		},
		service = Object.class
)
public class MessageClient {
	
	public void sendMessageByEmail(String message) throws MessageException {
		System.out.printf("Message Id %s\n", _messageEmailServices.sendMessage(message));
	}

	public void sendMessageByEmail(String message, String email) throws MessageException {
		System.out.printf(
				"Message Id %s\n", 
				_messageEmailServices.sendMessage(message, email));
	}

	public void sendMessageByFile(String message) throws MessageException {
		System.out.printf("Message Id %s\n", _messageFileServices.sendMessage(message));
	}

	public void sendMessageByFile(String message, String destination) throws MessageException {
		System.out.printf(
				"Message Id %s\n", 
				_messageFileServices.sendMessage(message, destination));
		
	}

	public void sendMessage(String message) throws MessageException {
		for (Message messageService : _messageServices) {
			System.out.printf("Message Id %s\n", messageService.sendMessage(message));
		}
	}

	@Reference(
			target = "(objectClass=it.keybiz.corso.lfr.message.api.Message)"
	)
	protected List<Message> _messageServices; 

	@Reference(
			target = "(component.name=it.keybiz.corso.lfr.message.service.email.MessageEmailService)"
	)
	protected Message _messageEmailServices; 

	@Reference(
			target = "(component.name=it.keybiz.corso.lfr.message.service.file.MessageFileService)"
	)
	protected Message _messageFileServices; 	
}