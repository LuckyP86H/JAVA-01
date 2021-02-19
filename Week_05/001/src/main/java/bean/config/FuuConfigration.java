package bean.config;

import bean.config.service.FuuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FuuConfigration {

    @Bean(name = "fuuService")
    public FuuService fuuService() {
        return new FuuService() {
            @Override
            public void fuu() {
                System.out.println("java config fuu");
            }
        };
    }
}
