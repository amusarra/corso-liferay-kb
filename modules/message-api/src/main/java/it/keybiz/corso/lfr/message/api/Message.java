package it.keybiz.corso.lfr.message.api;

import it.keybiz.corso.lfr.message.api.exception.MessageException;

/**
 * @author amusarra
 */
public interface Message {
	
	/**
	 * Send text message to default destination
	 *  
	 * @param message
	 * @return
	 * @throws MessageException
	 */
	String sendMessage(String message) throws MessageException;

	/**
	 * Send text message to specified destination
	 * 
	 * @param message
	 * @param destination
	 * @return
	 * @throws MessageException
	 */
	String sendMessage(String message, String destination) throws MessageException;

}