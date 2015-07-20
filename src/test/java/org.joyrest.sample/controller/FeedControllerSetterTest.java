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
package org.joyrest.sample.controller;

import static org.joyrest.routing.entity.CollectionType.List;
import static org.joyrest.test.unit.assertion.JoyrestAssert.assertType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.sample.model.Feed;
import org.joyrest.sample.services.FeedServiceImpl;
import org.joyrest.test.unit.ControllerUnitTest;
import org.joyrest.test.unit.model.MockRequest;
import org.joyrest.test.unit.model.MockResponse;
import org.junit.Test;

public class FeedControllerSetterTest extends ControllerUnitTest {

	@Override
	public void configure() {
		FeedController feedController = new FeedController();
		feedController.setFeedService(new FeedServiceImpl());

		setController(feedController);
		setGlobalPath("feeds");
	}

	@Test
	public void testGetAll() {
		MockRequest<?> request = new MockRequest<>();
		MockResponse<Feed> response = new MockResponse<>();

		get(request, response);

		assertType(List(Feed.class), response.getEntity());
		assertTrue(response.getEntity().isPresent());
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	public void testGet() {
		MockRequest<?> request = new MockRequest<>();
		MockResponse<Feed> response = new MockResponse<>();

		get("1", request, response);

		assertType(Feed.class, response.getEntity());
		assertEquals("1", request.getPathParam("id"));
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	public void testPost() {
		MockRequest<Feed> request = new MockRequest<>();
		request.setEntity(new Feed("Title", "Description", new Date()));
		MockResponse<Feed> response = new MockResponse<>();

		post(request, response);

		assertType(Feed.class, response.getEntity());
		Feed entity = response.getEntity().get();
		assertEquals("Title - 1", entity.getTitle());
		assertEquals("Description - 1", entity.getDescription());
		assertEquals(HttpStatus.CREATED, response.getStatus());
	}
}
