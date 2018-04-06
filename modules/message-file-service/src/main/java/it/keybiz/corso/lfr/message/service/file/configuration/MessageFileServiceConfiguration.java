package it.keybiz.corso.lfr.message.service.file.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author amusarra
 *
 */
@ExtendedObjectClassDefinition(category = "key-biz")
@Meta.OCD(
	id = "it.keybiz.corso.lfr.message.service.file.configuration.MessageFileServiceConfiguration",
	localization = "content/Language",
	name = "message-file-service-configuration-name"
)
public interface MessageFileServiceConfiguration {
	@Meta.AD(
			deflt = "/tmp/message_file.txt",
			description = "message-file-service-configuration-filepath-help",
			name = "message-file-service-configuration-filepath",
			required = false
	)
	public String filepath();

}
