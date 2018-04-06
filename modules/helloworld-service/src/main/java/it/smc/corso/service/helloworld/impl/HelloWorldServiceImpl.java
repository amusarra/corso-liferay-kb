package it.smc.corso.service.helloworld.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import it.smc.corso.service.helloworld.api.HelloWorldService;

/**
 * @author amusarra
 */
@Component(
	property = {
		// TODO enter required service properties
	},
	service = HelloWorldService.class
)
public class HelloWorldServiceImpl implements HelloWorldService {

	@Override
	public String sayHello() {
		return new String("Ciao Mondo!");
	}

	@Activate
	protected void activate() {
		System.out.println("Sono stato attivato!");
	}
	
	@Modified
	protected void modified() {
		System.out.println("Sono stato modificato!");
	}

	@Deactivate
	protected void deactivate() {
		System.out.println("Sono stato disattivato!");
	}
	
	// TODO enter required service methods

}