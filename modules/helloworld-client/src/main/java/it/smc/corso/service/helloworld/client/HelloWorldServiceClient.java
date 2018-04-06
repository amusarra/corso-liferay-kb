package it.smc.corso.service.helloworld.client;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import aQute.bnd.annotation.metatype.Configurable;

import it.smc.corso.service.helloworld.api.HelloWorldService;
import it.smc.corso.service.helloworld.client.configuration.HelloWorldServiceConfiguration;

/**
 * @author amusarra
 */
@Component(
		configurationPid = "it.smc.corso.service.helloworld.client.configuration.HelloWorldServiceConfiguration",
		property = { 
				"osgi.command.function=sayHello", 
				"osgi.command.scope=hello"
		},
		service = Object.class
)
public class HelloWorldServiceClient {

	public void sayHello() {
		System.out.println(_helloWorldService.sayHello());
		
		System.out.println(_configuration.say());
		System.out.println(_configuration.enabled());

	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(
				HelloWorldServiceConfiguration.class, properties);		
	}
	
	@Reference
	protected HelloWorldService _helloWorldService;
	
	private volatile HelloWorldServiceConfiguration _configuration;
}