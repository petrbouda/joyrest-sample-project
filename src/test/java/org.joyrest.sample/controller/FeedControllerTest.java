package org.joyrest.sample.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.sample.services.FeedServiceImpl;
import org.joyrest.test.unit.JoyrestUnitTest;
import org.joyrest.test.unit.model.MockRequest;
import org.joyrest.test.unit.model.MockResponse;
import org.junit.Test;

//@TestedController(FeedController.class)
public class FeedControllerTest extends JoyrestUnitTest {

	@Override
	public void configure() {
		FeedController feedController = new FeedController();
		feedController.setFeedService(new FeedServiceImpl());

		setController(feedController);
		setGlobalPath("feeds");
	}

	@Test
	public void testGetAll() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();

		get(request, response);

		assertTrue(response.getEntity().isPresent());
		assertEquals(HttpStatus.OK, response.getStatus());
	}

}
