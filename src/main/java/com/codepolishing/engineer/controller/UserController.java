package com.codepolishing.engineer.controller;


import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/getUserImage", method = RequestMethod.GET)
    public void getUserImage(HttpServletResponse response, Principal principal) throws IOException {
        User user = userRepository.findByEmail(principal.getName());
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        if(user.getImage() != null) {
            response.getOutputStream().write(user.getImage());
            response.getOutputStream().close();
        }
        else{
            response.getOutputStream().close();
        }
    }

}
