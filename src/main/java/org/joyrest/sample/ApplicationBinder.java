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
			.to(new TypeLiteral<FeedService>() {
			})
			.in(Singleton.class);

		bind(FeedController.class)
			.to(ControllerConfiguration.class)
			.in(Singleton.class);

		try {
			install(new OAuth2Binder());
		} catch (Exception e) {
			throw new InvalidConfigurationException("An error occurred during configuring OAuth2 Security.", e);
		}
	}

	private static class OAuth2Binder extends AbstractBinder {

		private final AuthorizationServerConfiguration authorizationServerConfiguration;

		public OAuth2Binder() throws Exception {
			// @formatter:off
	        ClientDetailsService clients = new ClientDetailsServiceConfigurer(new InMemoryClientDetailsServiceBuilder())
		        .inMemory()
			        .withClient("clientapp")
				        .authorizedGrantTypes("password", "refresh_token")
				        .authorities("USER")
				        .scopes("read", "write")
				        .secret("123456")
			        .and()
			        .withClient("clientapp2")
				        .authorizedGrantTypes("client_credentials")
				        .authorities("USER")
				        .scopes("read", "write")
			            .secret("123456")
			        .and()
			        .withClient("clientapp3")
				        .authorizedGrantTypes("authorization_code")
				        .authorities("USER")
				        .redirectUris(
					        "http://localhost:5000/login.html",
					        "http://localhost:5000/diff_login.html")
				        .scopes("read", "write")
				        .autoApprove(true)
			            .secret("123456")
	                .and().build();

	        UserDetailsManager users = new InMemoryUserDetailsManagerConfigurer<>()
		        .withUser("roy")
		            .password("spring")
		            .roles("USER")
	            .and()
	            .withUser("craig")
			        .password("spring")
			        .roles("ADMIN")
	            .and()
	            .withUser("greg")
	                .password("spring")
	                .roles("GUEST")
		        .and()
				.getUserDetailsService();

            this.authorizationServerConfiguration = new AuthorizationServerConfiguration(users, clients);
            // @formatter:on
		}

		@Override
		protected void configure() {
			bind(authorizationServerConfiguration)
				.to(AuthorizationServerConfiguration.class);
		}
	}
}
