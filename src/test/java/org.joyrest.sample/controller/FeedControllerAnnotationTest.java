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
import org.joyrest.model.http.HttpStatus;
import org.joyrest.sample.model.Feed;
import org.joyrest.sample.services.FeedService;
import org.joyrest.test.unit.JoyrestUnitTest;
import org.joyrest.test.unit.annotation.TestedController;
import org.joyrest.test.unit.model.MockRequest;
import org.joyrest.test.unit.model.MockResponse;
import org.junit.Test;

@TestedController(value = FeedController.class, globalPath = "feeds")
public class FeedControllerAnnotationTest extends JoyrestUnitTest {

	@Mock
	private FeedService service;

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