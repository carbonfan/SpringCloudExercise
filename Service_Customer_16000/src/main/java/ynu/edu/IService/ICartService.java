package ynu.edu.IService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ynu.edu.Entity.CommonResult;
import ynu.edu.Entity.User;

@FeignClient("provider-server")

public interface ICartService {
    @GetMapping("/user/getUserById/{Id}")
    public CommonResult<User> getCartById(@PathVariable("Id") Integer Id);

    @PostMapping("/user/getMsg")
    public String getMsg(@RequestParam(name = "userName") String userName);
    @PutMapping("/user/put")
    public String putMsg(@RequestBody User user);
    @DeleteMapping("/user/delete")
    public String deleteMsg(@RequestBody User user);
}
