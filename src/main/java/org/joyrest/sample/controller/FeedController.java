package org.joyrest.sample.controller;

import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.HttpStatus.OK;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;
import static org.joyrest.routing.entity.ResponseType.Resp;

import java.util.List;

import javax.inject.Inject;

import org.joyrest.routing.TypedControllerConfiguration;
import org.joyrest.sample.model.Feed;
import org.joyrest.sample.services.FeedService;

public class FeedController extends TypedControllerConfiguration {

	FeedService feedService;

	@Override
	protected void configure() {
		setControllerPath("feeds");

		post((req, resp) -> {
			Feed feed = feedService.save(req.getEntity());
			resp.entity(feed)
				.status(CREATED);
		}, Req(Feed.class), Resp(Feed.class))
			.produces(JSON).consumes(JSON);

		get("{id}", (req, resp) -> {
			Feed feed = feedService.get(req.getPathParam("id"));
			resp.entity(feed)
				.status(OK);
		}, Resp(Feed.class))
			.produces(JSON);

		get((req, resp) -> {
			List<Feed> feeds = feedService.getAll();
			resp.entity(feeds)
				.status(OK);
		}, RespList(Feed.class))
			.produces(JSON);
	}

	@Inject
	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}
}
