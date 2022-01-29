package cn.com.springcoud.contoller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoveryController {

  @Autowired
  private DiscoveryClient dc;

  @GetMapping("/services")
  public Set<String> getServices(){
    return new LinkedHashSet<>(dc.getServices());
  }

  @GetMapping("/services/{serviceName}")
  public List<ServiceInstance> getServiceInstances(@PathVariable String serviceName){
    return dc.getInstances(serviceName);
  }

  @GetMapping("/services/{serviceName}/{instanceId}")
  public ServiceInstance getServiceInstance(@PathVariable String serviceName,@PathVariable String instanceId){
    return getServiceInstances(serviceName).stream()
        .filter(serviceInstance -> instanceId.equals(serviceInstance.getInstanceId()))
        .findFirst().orElseThrow(()->new RuntimeException("No such instance"));
  }

}
