package ynu.edu.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @RequestMapping("fallback")
    public String fallback(){
        return "请稍后尝试。";
    }
}
