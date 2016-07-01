package it.unibz.precise;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import it.unibz.precise.model.TaskType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort=true)
public class ModelsClientTest {

	private static final String SERVICE_URI = "http://localhost:%s";
	
	@Value("${local.server.port}")
	private int port;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private Traverson traverson;
	
	@Before
	public void setup() {
		mockMvc = webAppContextSetup(this.context).build();
		traverson = TraversonUtil.create(String.format(SERVICE_URI, port));
	}

	@Test
	public void postNestedModel() throws IOException, Exception {
		Link fullModelsLink = traverson.follow("fullModels").asLink();
		MvcResult result = mockMvc.perform(
			post(fullModelsLink.getHref())
			.accept(MediaTypes.HAL_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(TestUtil.load("nestedModel.json"))
		)
		.andExpect(status().isCreated())
		.andReturn();
		
		Resources<TaskType> taskTypes = TraversonUtil.continueFrom(result)
			.follow("model", "config", "taskTypes")
			.toObject(new ParameterizedTypeReference<Resources<TaskType>>(){});
	
		assertThat(taskTypes.getContent(), hasSize(2));
	}

}
