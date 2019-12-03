package com.codepolishing.engineer.controller;


import com.codepolishing.engineer.entity.Province;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.ProvinceRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/administrator")
public class AdminController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProvinceRepository provinceRepository;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String displayAllUsers(Model model)
    {
        List<User> allUsers = userRepository.findAll();

        model.addAttribute("allUsers",allUsers);

        return "users_list";
    }
    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    public String displayUser(@PathVariable int id, Model model)
    {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("provinces", provinceRepository.findAll());


        return "user_profile_management";
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User userForm)
    {
        User user = updateUserFromForm(id,userForm);
        userRepository.saveAndFlush(user);

        return "redirect:/administrator/users/"+id;
    }
    @RequestMapping(value = "/users/{id}/avatar", method = RequestMethod.DELETE)
    public String deleteUserAvatar(@PathVariable int id){
        User user = userRepository.findById(id);

        byte[] image = new byte[0];
        try {
            image = createDefaultUserAvatar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImage(image);
        userRepository.saveAndFlush(user);

        return "redirect:/administrator/users/"+id;
    }

    @RequestMapping(value = "/users/{id}/cv", method = RequestMethod.DELETE)
    public String deleteUserCv(@PathVariable int id)
    {
        User user = userRepository.findById(id);

        File userCVDirectory = new File(user.getCVPath());
        String[] filesInCVDirectory = userCVDirectory.list();
        if(filesInCVDirectory.length > 0) {
            new File(user.getCVPath() + "/" + filesInCVDirectory[0]).delete();
            user.setCVPath(null);
            userRepository.saveAndFlush(user);
        }
        else {
            System.out.println(getClass().getName() + ": Nie można usunąć CV z dysku");
        }
        return "redirect:/administrator/users/"+id;
    }
    @RequestMapping(value = "/users/{id}/enable")
    public String updateUserEnable(@PathVariable int id)
    {
        User user = userRepository.findById(id);
        user.setEnabled((short) 1);
        userRepository.save(user);

        return "redirect:/administrator/users/"+id;
    }
    //TODO:: NIE JEST TO POPRAWNE ROZWIAZANIE, ALE DZIALA
    @RequestMapping(value = "/users/{id}/disable")
    public String updateUserDisable(@PathVariable int id)
    {
        User user = userRepository.findById(id);
        user.setEnabled((short) 0);
        userRepository.save(user);

        return "redirect:/administrator/users/"+id;
    }

    // Zwracam tablicę bajtów zawierającej informacje o domyślnym avatarze użytkownika
    private byte[] createDefaultUserAvatar() throws IOException {
        File file = new File(getClass().getResource("/public/images/users/user_new.png").getPath());
        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png", bos);

        return bos.toByteArray();
    }


    // Ugly method :c
    private User updateUserFromForm(int userId, User userData)
    {
        User user = userRepository.findById(userId);

        user.setName(userData.getName());
        user.setSurname(userData.getSurname());
        user.setCity(userData.getCity());
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setBirthDate(userData.getBirthDate());
        user.setFlatNr(userData.getFlatNr());
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setStreet(userData.getStreet());
        user.setPostCode(userData.getPostCode());
        user.setHouseNumber(userData.getHouseNumber());
        user.setConfirmPassword(user.getPassword());

        return user;
    }


}
