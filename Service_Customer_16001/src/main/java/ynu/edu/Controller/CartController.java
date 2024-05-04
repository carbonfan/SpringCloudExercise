package ynu.edu.Controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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
import ynu.edu.IService.ICartService;

import java.util.List;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Resource
    ICartService cartService;

    @Bulkhead(name = "bulkheadA", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "downCartById")
    @GetMapping("/getCartById/{Id}")
    public CommonResult<User> getCartById(@PathVariable("Id") Integer Id){
        return cartService.getCartById(Id);
    }

    public CommonResult<User> downCartById(@PathVariable("Id") Integer Id){
        String meg = "线程池隔离！";
        return new CommonResult<>(6000, meg, new User());
    }
}
