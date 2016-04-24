package leettalk.util;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import leettalk.model.SubmissionResult;

@Component
public class SphereEngineApi {

	@Value("${sphere-engine-token}")
	private String token;

	@Value("${sphere-engine-url}")
	private String url;

	@Autowired
	private RestTemplate template;

	@Bean
	public RestTemplate template() {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter());
		template.getInterceptors().add(new FuckSphereEngineInterceptor());
		return template;
	}

	public int createSubmission(String sourceCode, int language, String input) {
		Submission submission = new Submission(sourceCode, language, input);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("api").pathSegment("v3")
				.pathSegment("submissions").queryParam("access_token", token);	
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Submission> entity = new HttpEntity<Submission>(submission, headers);
		

		ResponseEntity<SubmissionId> response = template.exchange(builder.toUriString(), HttpMethod.POST, entity, SubmissionId.class);
		
		return Integer.valueOf(response.getBody().getId());
	}

	public SubmissionResult getSubmissionResult(int id) {
		ResultOptions options = new ResultOptions(id, false, false, true, false, false);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("api").pathSegment("v3")
				.pathSegment("submissions").pathSegment(Integer.toString(id)).queryParam("access_token", token).queryParam("withOutput", true);
		
		SubmissionResult response = template.getForObject(builder.toUriString(), SubmissionResult.class);
		
		return response;
	}
}

class ResultOptions {
	private int id;
	private boolean withSource;
	private boolean withInput;
	private boolean withOutput;
	private boolean withStderr;

	public ResultOptions() {
		super();
	}

	public ResultOptions(int id, boolean withSource, boolean withInput, boolean withOutput, boolean withStderr,
			boolean withCmpinfo) {
		super();
		this.id = id;
		this.withSource = withSource;
		this.withInput = withInput;
		this.withOutput = withOutput;
		this.withStderr = withStderr;
		this.withCmpinfo = withCmpinfo;
	}

	private boolean withCmpinfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isWithSource() {
		return withSource;
	}

	public void setWithSource(boolean withSource) {
		this.withSource = withSource;
	}

	public boolean isWithInput() {
		return withInput;
	}

	public void setWithInput(boolean withInput) {
		this.withInput = withInput;
	}

	public boolean isWithOutput() {
		return withOutput;
	}

	public void setWithOutput(boolean withOutput) {
		this.withOutput = withOutput;
	}

	public boolean isWithStderr() {
		return withStderr;
	}

	public void setWithStderr(boolean withStderr) {
		this.withStderr = withStderr;
	}

	public boolean isWithCmpinfo() {
		return withCmpinfo;
	}

	public void setWithCmpinfo(boolean withCmpinfo) {
		this.withCmpinfo = withCmpinfo;
	}
}

class Submission {
	private String sourceCode;
	private int language;
	private String input;

	public Submission() {
	}

	public Submission(String sourceCode, int language, String input) {
		this.sourceCode = sourceCode;
		this.language = language;
		this.input = input;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SubmissionId {
	private String id;

	public SubmissionId() {

	}

	public SubmissionId(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

class FuckSphereEngineInterceptor implements ClientHttpRequestInterceptor {
	//because sending json and specifing text/html is cool
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		ClientHttpResponse response = execution.execute(request, body);
		HttpHeaders headers = response.getHeaders();
		
		if (headers.containsKey("Content-Type")) {
			headers.remove("Content-Type");
		}
		
		headers.add("Content-Type", "application/json");
		
		return response;
	}
	
	
}