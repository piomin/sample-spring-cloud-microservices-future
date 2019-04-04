package pl.piomin.services.caller;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceSupplier;
import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ClientConfiguration {

    @Bean
    public RoundRobinLoadBalancer roundRobinContextLoadBalancer(
            LoadBalancerClientFactory clientFactory, Environment env) {
        String serviceId = clientFactory.getName(env);
        return new RoundRobinLoadBalancer(serviceId, clientFactory
                .getLazyProvider(serviceId, ServiceInstanceSupplier.class), -1);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceInstanceSupplier discoveryClientServiceInstanceSupplier(
            DiscoveryClient discoveryClient, Environment env,
            ObjectProvider<CacheManager> cacheManager) {
        // TODO: bean post processor to enable caching?
        DiscoveryClientServiceInstanceSupplier delegate = new DiscoveryClientServiceInstanceSupplier(
                discoveryClient, env);
        if (cacheManager.getIfAvailable() != null) {
            return new CachingServiceInstanceSupplier(delegate,
                    cacheManager.getIfAvailable());
        }
        return delegate;
    }
}
