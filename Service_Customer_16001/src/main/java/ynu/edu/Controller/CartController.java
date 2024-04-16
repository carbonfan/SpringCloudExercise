package ynu.edu.Controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;//不要用Netflix的
import ynu.edu.Entity.CommonResult;
import ynu.edu.Entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Resource
    private RestTemplate restTemplate;//自动加载进入项目

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/getCartById/{Id}")
    public CommonResult<User> getCartById(@PathVariable("Id") Integer Id){
        List<ServiceInstance> list = discoveryClient.getInstances("provider-server");
        for(ServiceInstance i : list){
            System.out.println(i.getHost()+"\t"+i.getPort());
        }
        return restTemplate.getForObject(
                "http://"+discoveryClient.getInstances("provider-server").get(0).getHost()
                        +":"+ discoveryClient.getInstances("provider-server").get(0).getPort()
                        + "/user/getUserById/"+Id.toString(),
                CommonResult.class);
    }
}
