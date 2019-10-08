package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.ProvinceRepository;
import com.codepolishing.engineer.repository.UserRepository;
import com.codepolishing.engineer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


@Controller
public class SignUp2Controller {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ProvinceRepository provinceRepository;

    @Autowired
    public SignUp2Controller(UserRepository userRepository, UserRoleRepository userRoleRepository, ProvinceRepository provinceRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("sign_up2")
    public String showSignUpPage2(@ModelAttribute("user") User user, Model model)
    {
        if(user == null) return "user_sign_up2";
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        model.addAttribute("user",user);
        return "user_sign_up2";
    }
    @PostMapping("sign_up2")
    public String validAllUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        if(bindingResult.hasFieldErrors("name") ||
        bindingResult.hasFieldErrors("surname") ||
        bindingResult.hasFieldErrors("birthDate"))
        {
            model.addAttribute("user",user);
            return "user_sign_up2";
        }else {
            File file = new File(getClass().getResource("/public/images/users/user_new.png").getPath());
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png", bos);

            byte[] data = bos.toByteArray();

            user.setImage(data);

            userRepository.save(user);
            return "redirect:/";
        }

    }
}
