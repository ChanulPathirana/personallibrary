package com.chanul.personallibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"google.client-id=test-client-id",
		"google.client-secret=test-client-secret",
		"google.redirect-uri=http://localhost:8080/api/google-drive/callback"
})
class PersonallibraryApplicationTests {

	@Test
	void contextLoads() {
	}

}
