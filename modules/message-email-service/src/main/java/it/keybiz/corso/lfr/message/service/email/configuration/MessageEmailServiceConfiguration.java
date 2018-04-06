/**
 * 
 */
package it.keybiz.corso.lfr.message.service.email.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author amusarra
 *
 */
@ExtendedObjectClassDefinition(category = "key-biz")
@Meta.OCD(
	id = "it.keybiz.corso.lfr.message.service.email.configuration.MessageEmailServiceConfiguration",
	localization = "content/Language",
	name = "message-email-service-configuration-name"
)
public interface MessageEmailServiceConfiguration {

		@Meta.AD(
			deflt = "true",
			description = "message-email-service-configuration-async-help",
			name = "message-email-service-configuration-async",
			required = false
		)
		public boolean async();

		@Meta.AD(
			deflt = "antonio.musarra@gmail.com",
			description = "message-email-service-configuration-report-to-help",
			name = "message-email-service-configuration-report-to",
			required = false
		)
		public String reportTo();

		@Meta.AD(
			deflt = "noreply-loginfailure@example.com",
			description = "message-email-service-configuration-from-help",
			name = "message-email-service-configuration-from", required = false
		)
		public String from();

		@Meta.AD(
			deflt = "Message from Liferay",
			description = "message-email-service-configuration-email-subject-help",
			name = "message-email-service-configuration-email-subject",
			required = false
		)
		public String emailSubject();

}
