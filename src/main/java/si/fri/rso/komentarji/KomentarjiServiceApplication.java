package si.fri.rso.komentarji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import si.fri.rso.komentarji.repositories.KomentarRepository;
import si.fri.rso.komentarji.resolvers.Query;

@SpringBootApplication
@EnableJpaRepositories("si.fri.rso.komentarji.repositories")
@EnableDiscoveryClient
public class KomentarjiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KomentarjiServiceApplication.class, args);
	}

	@Bean
	public Query query(KomentarRepository komentarRepository) {
		return new Query(komentarRepository);
	}

}
