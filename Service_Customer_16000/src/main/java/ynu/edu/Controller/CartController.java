package ynu.edu.Controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.discovery.DiscoveryClient;//不要用Netflix的
import org.springframework.web.bind.annotation.*;
import ynu.edu.Entity.CommonResult;
import ynu.edu.Entity.User;
import ynu.edu.IService.ICartService;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Resource
    private ICartService cartService;

    @CircuitBreaker(name = "circuitbreakerB", fallbackMethod = "downCartById")
    @GetMapping("/getCartById/{Id}")
    public CommonResult<User> getCartById(@PathVariable("Id") Integer Id){
        System.out.println("该功能正常。");
        return cartService.getCartById(Id);
    }
    public CommonResult<User> downCartById(@PathVariable("Id") Integer Id, Throwable e){
        e.printStackTrace();
        String meg = "爆满啦！信息的服务当前被熔断！方法降级！";
        System.out.println(meg);
        return new CommonResult<>(4000, meg, new User());
    }

    @CircuitBreaker(name = "circuitbreakerB", fallbackMethod = "downGetMsg")
    @PostMapping("/getMsg")
    public String getMsg(@RequestParam(name = "userName") String userName){
        return cartService.getMsg(userName);
    }
    public String downGetMsg(@RequestParam(name = "userName") String userName){
        if(userName.isEmpty()){
            return "用户名不存在。。。";
        }
        return "出错了。。。";
    }
    @PutMapping("/put")
    public String putMsg(@RequestBody User user){
        return cartService.putMsg(user);
    }
    @DeleteMapping("delete")
    public String deleteMsg(@RequestBody User user){
        return cartService.deleteMsg(user);
    }


}
