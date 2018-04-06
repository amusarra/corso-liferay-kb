package it.smc.corso.service.helloworld.client.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
		id = "it.smc.corso.service.helloworld.client.configuration.HelloWorldServiceConfiguration",
		localization = "content/Language",
		name = "hellowordservice.client.command.configuration.name"
	)
public interface HelloWorldServiceConfiguration {

	@Meta.AD(
			deflt = "Ciao Mondo", 
			description = "Cosa dire", 
			required = false)
	public String say();

	@Meta.AD(
			deflt = "true", 
			description = "Abilitato", 
			required = false)
	public boolean enabled();
}
