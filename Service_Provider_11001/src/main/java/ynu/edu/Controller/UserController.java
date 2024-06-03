package ynu.edu.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import ynu.edu.Entity.CommonResult;
import ynu.edu.Entity.User;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {
    @Value("${server.port}")
    private String message;
    @GetMapping("/getUserById/{userId}")
    public CommonResult<User> getUserById(@PathVariable("userId") Integer userId){
        CommonResult<User> result = new CommonResult<>();
        Integer code = 200;
        String msg = "success"+ message;

        try {
            User user = new User(userId,"test2","090431");

            result.setResult(user);

        } catch (Exception e){
            code = 500;
            msg = "failed";
        }
        result.setCode(code);
        result.setMeg(msg);
        return result;
    }
    @PostMapping("/getMsg")
    public String getMsg(@RequestParam(name = "userName") String userName){
        return userName;
    }
    @PostMapping("/putMsg")
    public String putMsg(@RequestBody User user){
        if(user != null) {
            return "success";
        }else {
            return "error";
        }
    }

    @PostMapping("/deleteMsg")
    public String delete(@RequestBody User user){
        if(user != null){
            return "already deleted";
        }else {
            return "delete error";
        }
    }
}
