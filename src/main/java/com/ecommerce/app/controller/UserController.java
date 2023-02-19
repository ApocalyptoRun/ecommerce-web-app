package com.ecommerce.app.controller;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.user.SignInResponseDto;
import com.ecommerce.app.dto.user.SignupDto;
import com.ecommerce.app.dto.user.SigninDto;
import com.ecommerce.app.service.UserService;

import io.swagger.models.Response;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    //show form signin
    @GetMapping("/showFormSignin")
    public String showFormSignin(Model model) {
    	SigninDto signinDto = new SigninDto();
    	model.addAttribute("signinDto", signinDto);
    	return "signin";
    }
    
    
    //show form signup
    @GetMapping("/showFormSignup")
    public String showFormSignup(Model model) {
    	SignupDto signupDto = new SignupDto();
    	model.addAttribute("signupDto", signupDto);
    	return "signup";
    }

    //sign UP

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignupDto signupDto){
         userService.signUp(signupDto);
         return "redirect:/user/showFormSignin";
    }

    //sign IN

    @PostMapping("/signin")
    public String signIn(@ModelAttribute SigninDto singinDto, Model model, HttpServletResponse response){
        String token = userService.signIn(singinDto);
          
        
       Cookie cookie = new Cookie("token",token);
       cookie.setMaxAge(60*60*24);
       cookie.setPath("/");
       response.addCookie(cookie);
        
       return "redirect:/product/ByCategory/59";
    }
    
    // sign OUT
    
    @GetMapping("/signout")
    public String singOut(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    	return "redirect:/product/ByCategory/59";
    }
}











