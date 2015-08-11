/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.sample;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.oauth2.initializer.AuthorizationServerConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.sample.controller.FeedController;
import org.joyrest.sample.services.FeedService;
import org.joyrest.sample.services.FeedServiceImpl;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.inject.Singleton;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FeedServiceImpl.class)
			.to(new TypeLiteral<FeedService>() { })
			.in(Singleton.class);

		bind(FeedController.class)
			.to(ControllerConfiguration.class)
			.in(Singleton.class);

		install(new InMemoryOAuthBinder());
	}
}
