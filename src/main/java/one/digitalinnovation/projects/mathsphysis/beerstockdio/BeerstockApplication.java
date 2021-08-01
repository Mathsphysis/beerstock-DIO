package one.digitalinnovation.projects.mathsphysis.beerstockdio;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeerstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerstockApplication.class, args);
	}

	@Autowired
	ObjectMapper objectMapper;

	private static final Contact CONTACT = ContactBuilder.builder().build().toContact();


	@Bean
	public OpenAPI beerstockOpenAPI(@Value("${application-description}") String appDesciption,
									@Value("${application-version}") String appVersion) {
		return new OpenAPI()
				.info( new Info()
				.title("Beer Stock API DIO")
				.version(appVersion)
				.description(appDesciption)
				.contact(CONTACT)
				.termsOfService("http://swagger.io/terms")
				.license(new License().name("MIT License").url("https://github.com/Mathsphysis/personapi-dio/blob/main/LICENSE")));
	}

	@Builder
	private static class ContactBuilder {

		@Builder.Default
		private String name = "Maths Physis";

		@Builder.Default
		private String email = "smathsphysis@gmail.com";

		@Builder.Default
		private String url = "https://github.com/Mathsphysis";

		public Contact toContact() {
			Contact contact = new Contact();
			contact.setName(name);
			contact.setEmail(email);
			contact.setUrl(url);

			return contact;
		}
	}
}
