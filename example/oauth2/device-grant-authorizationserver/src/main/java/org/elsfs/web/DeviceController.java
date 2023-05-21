package org.elsfs.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Steve Riesenberg
 */
@Controller
public class DeviceController {

    @GetMapping("/activate")
    public String activate(@RequestParam(value = "user_code", required = false) String userCode) {
        if (userCode != null) {
            return "redirect:/oauth2/device_verification?user_code=" + userCode;
        }
        return "activate";
    }

    @GetMapping("/activated")
    public String activated() {
        return "activated";
    }

    @GetMapping(value = "/", params = "success")
    public String success() {
        return "activated";
    }

}