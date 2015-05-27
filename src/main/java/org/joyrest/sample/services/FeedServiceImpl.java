package org.joyrest.sample.services;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.joyrest.sample.model.Feed;

public class FeedServiceImpl implements FeedService {

	@Override
	public Feed save(Feed feed) {
		return new Feed(
			feed.getTitle() + " - " + 1,
			feed.getDescription() + " - " + 1,
			new Date());
	}

	@Override
	public Feed get(String feedId) {
		return new Feed("JoyRest Feed - " + feedId, "JoyRest Feed Description - " + feedId, new Date());
	}

	@Override
	public List<Feed> getAll() {
		return IntStream.range(1, 25)
			.mapToObj(i -> new Feed("JoyRest Feed - " + i, "JoyRest Feed Description - " + i, new Date()))
			.collect(toList());
	}
}
