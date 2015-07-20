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

import static java.util.Collections.singletonList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.joyrest.routing.entity.CollectionType.List;
import static org.joyrest.test.unit.assertion.JoyrestAssert.assertType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.sample.model.Feed;
import org.joyrest.sample.services.FeedService;
import org.joyrest.test.unit.ControllerUnitTest;
import org.joyrest.test.unit.model.MockRequest;
import org.joyrest.test.unit.model.MockResponse;
import org.junit.Test;

public class FeedControllerMocksTest extends ControllerUnitTest {

	@TestSubject
	private FeedController feedController = new FeedController();

	@Mock
	private FeedService service;

	@Override
	public void configure() {
		setController(feedController);
		setGlobalPath("feeds");
	}

	@Test
	public void testGetAll() {
		MockRequest<?> request = new MockRequest<>();
		MockResponse<Feed> response = new MockResponse<>();

		expect(service.getAll()).andReturn(singletonList(new Feed()));

		replayAll();

		get(request, response);

		verifyAll();

		assertType(List(Feed.class), response.getEntity());
		assertTrue(response.getEntity().isPresent());
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	public void testGet() {
		MockRequest<?> request = new MockRequest<>();
		MockResponse<Feed> response = new MockResponse<>();

		expect(service.get(eq("1"))).andReturn(new Feed());
		replayAll();

		get("1", request, response);

		verifyAll();

		assertType(Feed.class, response.getEntity());
		assertEquals("1", request.getPathParam("id"));
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	public void testPost() {
		Date date = new Date();
		Feed feed = new Feed("Title", "Description", date);
		Feed savedFeed = new Feed("Title - 1", "Description - 1", date);

		MockRequest<Feed> request = new MockRequest<>();
		request.setEntity(feed);
		MockResponse<Feed> response = new MockResponse<>();

		expect(service.save(eq(feed))).andReturn(savedFeed);
		replayAll();

		post(request, response);

		verifyAll();

		assertType(Feed.class, response.getEntity());
		Feed entity = response.getEntity().get();
		assertEquals(savedFeed.getTitle(), entity.getTitle());
		assertEquals(savedFeed.getDescription(), entity.getDescription());
		assertEquals(HttpStatus.CREATED, response.getStatus());
	}
}
