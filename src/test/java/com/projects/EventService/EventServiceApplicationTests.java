package com.projects.EventService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@DisplayName("EventService Tests")
class EventServiceApplicationTests {

	protected static MockMvc mvc;

	private static final Event event = new Event("1", "Event1", "Description of event1");

	@Autowired
	WebApplicationContext webApplicationContext;

	private ObjectMapper mapper = new ObjectMapper();


	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mapper = new ObjectMapper();
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void createEvent() throws Exception {
		setUp();
		String uri = "/events";
		Event event = new Event();
		event.setId("1");
		event.setDescription("Description of event1");
		event.setName("Event1");

		String content = mapper.writeValueAsString(event);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@ParameterizedTest
	@MethodSource("getEventArg")
	public void getEvents(Event event) throws Exception {
		setUp();
		String uri = "/events";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Event[] events = mapFromJson(content, Event[].class);
		assertTrue(events.length > 0);

		Event eventFromResult = events[0];
		assertNotNull(eventFromResult);
		assertEquals(eventFromResult.getDescription(), event.getDescription());
	}

	private static Stream<Arguments> getEventArg() {
		return Stream.of(
				arguments(event)
		);
	}

}
