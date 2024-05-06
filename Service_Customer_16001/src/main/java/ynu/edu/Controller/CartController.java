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
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Resource
    ICartService cartService;

    @Bulkhead(name = "bulkheadA", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "getCartByIdDown")
    @GetMapping("/getCartById/{Id}")
    public CompletableFuture<User> getCartById(@PathVariable("Id") Integer Id) throws Exception {
        CompletableFuture<User> result = CompletableFuture.supplyAsync(()->{return cartService.getCartById(Id).getResult();});
        String meg = "该功能正常。";
        System.out.println(meg);
        Thread.sleep(10000L);

        return result;
    }

    public CompletableFuture<User> getCartByIdDown(@PathVariable("Id") Integer Id, Throwable e) {
        e.printStackTrace();
        String meg = "线程池隔离";
        System.out.println(meg);
        return CompletableFuture.supplyAsync(()->{return new CommonResult<>(400,"fallback",new User()).getResult();});
    }
}
