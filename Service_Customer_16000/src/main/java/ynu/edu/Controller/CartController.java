package ynu.edu.Controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;//不要用Netflix的
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ynu.edu.Entity.CommonResult;
import ynu.edu.Entity.User;
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
    @PostMapping("/getMsg")
    public String getMsg(@RequestParam(name = "userName") String userName){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userName",userName);
        System.out.println(discoveryClient.getInstances("provider-server").get(0).getHost());
        System.out.println(discoveryClient.getInstances("provider-server").get(0).getPort());
        return restTemplate.postForObject(
                "http://"+discoveryClient.getInstances("provider-server").get(0).getHost()
                         +":"+discoveryClient.getInstances("provider-server").get(0).getPort()
                         + "/user/getMsg",map,String.class);
    }
    @PutMapping("/put")
    public String putMsg(@RequestBody User user){
        return restTemplate.postForEntity(
                "http://"+discoveryClient.getInstances("provider-server").get(0).getHost()
                        +":"+discoveryClient.getInstances("provider-server").get(0).getPort()
                        + "/user/putMsg",user,String.class).getBody();
    }
    @DeleteMapping("delete")
    public String deleteMsg(@RequestBody User user){
        return restTemplate.postForEntity(
                "http://"+discoveryClient.getInstances("provider-server").get(0).getHost()
                        +":"+discoveryClient.getInstances("provider-server").get(0).getPort()
                        + "/user/deleteMsg",user,String.class).getBody();
    }


}
