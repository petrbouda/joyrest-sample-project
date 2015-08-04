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

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.oauth2.configurer.user.InMemoryUserDetailsServiceConfigurer;
import org.joyrest.oauth2.initializer.AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public class InMemoryOAuthBinder extends AbstractBinder {

    private AuthorizationServerConfiguration authorizationServerConfiguration() throws Exception {
        // @formatter:off
		ClientDetailsService clientDetailsService = new InMemoryClientDetailsServiceBuilder()
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
			    .secret("123456")
			.and().build();

		UserDetailsService userDetailsService = new InMemoryUserDetailsServiceConfigurer()
		    .withUser("roy", "spring")
				.roles("USER")
	        .and()
	        .withUser("craig", "spring")
				.roles("ADMIN")
	        .and()
			.withUser("greg", "spring")
	            .roles("GUEST")
			.and().build();

        return new AuthorizationServerConfiguration()
			.clientDetailsService(clientDetailsService)
			.userDetailsService(userDetailsService);
        // @formatter:on
    }

    @Override
    protected void configure() {
        try {
            bind(authorizationServerConfiguration())
                .to(AuthorizationServerConfiguration.class);
        } catch (Exception e) {
            throw new InvalidConfigurationException("An error occurred during configuring OAuth2 Security.", e);
        }
    }
}