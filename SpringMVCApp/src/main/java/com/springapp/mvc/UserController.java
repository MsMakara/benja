package com.springapp.mvc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.springapp.mvc.dhis.*;
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Add(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public
    @ResponseBody
    String listUsersJson(ModelMap model) throws JSONException {
        JSONArray userArray = new JSONArray();
        for (User user : userRepository.findAll()) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("id", user.getId());
            userJSON.put("username", user.getUsername());
            userJSON.put("password", user.getPassword());

            userArray.put(userJSON);
        }
        return userArray.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {

        userRepository.save(user);

        try {
            HttpClientExample dhis=new HttpClientExample();
            dhis.sendGet( user.getUsername(), user.getPassword(), user.getUrl());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "redirect:/";
    }

    @RequestMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {

        userRepository.delete(userRepository.findOne(userId));

        return "redirect:/";
    }
}