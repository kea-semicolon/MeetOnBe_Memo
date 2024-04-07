package semicolon.MeetOn_Memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication
public class MeetOnMemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetOnMemoApplication.class, args);
	}

}
