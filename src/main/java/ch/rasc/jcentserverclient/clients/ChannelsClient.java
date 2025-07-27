package ch.rasc.jcentserverclient.clients;

import ch.rasc.jcentserverclient.models.ChannelsRequest;
import ch.rasc.jcentserverclient.models.ChannelsResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo Channels API client interface. Provides methods for listing and managing
 * channels.
 */
public interface ChannelsClient {

	/**
	 * List active channels. Returns information about currently active channels.
	 * @param request the channels request with optional pattern filter
	 * @return the channels response containing active channels
	 */
	@RequestLine("POST /channels")
	@Headers("Content-Type: application/json")
	ChannelsResponse channels(ChannelsRequest request);

}
