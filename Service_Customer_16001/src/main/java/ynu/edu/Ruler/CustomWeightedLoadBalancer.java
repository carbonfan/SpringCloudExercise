package ynu.edu.Ruler;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadLocalRandom;

public class CustomWeightedLoadBalancer {
//    @Bean
//    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
//                                                            LoadBalancerClientFactory factory){
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new LoadBalancer
//    }
    @Bean
    ServiceInstanceListSupplier customLoadBalancer(ConfigurableApplicationContext context){
        return ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .withWeighted(instance -> ThreadLocalRandom.current().nextInt(1,101))
                .withCaching()
                .build(context);
    }
}
