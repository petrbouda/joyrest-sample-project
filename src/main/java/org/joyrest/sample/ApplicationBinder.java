package org.joyrest.sample;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.sample.controller.FeedController;
import org.joyrest.sample.services.FeedService;
import org.joyrest.sample.services.FeedServiceImpl;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.utils.transform.JsonReaderWriter;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FeedServiceImpl.class)
			.to(new TypeLiteral<FeedService>() {})
			.in(Singleton.class);

		bind(FeedController.class)
			.to(ControllerConfiguration.class)
			.in(Singleton.class);

		bind(JsonReaderWriter.class)
			.to(Reader.class)
			.to(Writer.class)
			.in(Singleton.class);
	}

}
