package org.joyrest.sample.services;

import java.util.List;

import org.joyrest.sample.model.Feed;

public interface FeedService {

	Feed save(Feed feed);

	Feed get(String feedId);

	List<Feed> getAll();

}
