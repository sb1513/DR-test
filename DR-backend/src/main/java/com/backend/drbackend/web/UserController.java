package com.backend.drbackend.web;

import com.backend.drbackend.entity.User;
import com.backend.drbackend.service.UserService;
import com.backend.drbackend.utils.JWTutil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private JWTutil jwtutil;

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public R register(@RequestBody User user) {
        try{
            user.setScore(200);
            userService.save(user);
            return new R(2000,"注册成功",null);
        }catch (Exception e){
            e.printStackTrace(); // 关键：把真正的异常打印出来
            return new R(5000,"注册失败，昵称或用户名重复",null);
        }
    }

    @PostMapping("register2")
    public R register2(String nickName, String loginName, String loginPwd, String email) {
        try{
            User u = new User();
            u.setNickName(nickName);
            u.setUserName(loginName);
            u.setUserPwd(loginPwd);
            u.setEmail(email);
            u.setScore(200);
            userService.save(u);
            return new R(2000,"注册成功",null);
        }catch (Exception e){
            e.printStackTrace(); // 关键：把真正的异常打印出来
            return new R(5000,"注册失败，用户名重复"+e.getMessage(),null);
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> map) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",map.get("userName"));
        qw.eq("user_password",map.get("userPwd"));
        User u = userService.getOne(qw);
        if(u!=null){
            Map<String,Object> map1 = new HashMap<>();
            String jwt = jwtutil.generateToken(Map.of("user_id",u.getId(),"nickName",u.getNickName()));
            map1.put("id",u.getId());
            map1.put("nickName",u.getNickName());
            map1.put("userName",u.getUserName());
            return new R(2000,"登录成功!",Map.of("user",map1,"token",jwt));
        }
        else{
            return new R(4001,"用户名或密码错误",null);
        }
    }

    @PostMapping("/user_update")
    public R user_update(@RequestAttribute("user_id") int user_id,@RequestBody Map<String, String> map) {
        String loginPwd = map.get("loginPwd");
        String newPwd = map.get("newPwd");
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_id",user_id);
        qw.eq("user_password",loginPwd);
        User user = userService.getOne(qw);
        if(user!=null){
            user.setUserPwd(newPwd);
            userService.updateById(user);
            return new R(2000,"密码修改成功!",null);
        }
        else{
            return new R(4001,"用户名或密码错误",null);
        }
    }

    @PostMapping("/user_delete")
    public R user_delete(@RequestBody Map<String,Object> map) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_id",map.get("id"));
        User user = userService.getOne(qw);
        if(user!=null){
            userService.removeById(user.getId());
            return new R (2000, "注销成功", null);
        }else{
            return new R (5001, "注销失败",null);
        }
    }

    @RequestMapping("/win01")
    public R userWin01(String name,String birthday) {
        return new R(101,"你好:", Map.of("id",101,"name",name,"fuck","birthday"));
    }
    @PostMapping("/win02")
    public String userWin02(@RequestBody Map<String,Object> user) {
        return user.get("name")+"error!"+user.get("fuck");
    }
    @RequestMapping("/win03/{n1}/{n2}")
    public R userWin03(@PathVariable("n1")String name,@PathVariable("n2") String birthday) {
        return new R(101,"你好:", Map.of("id",101,"name",name,"fuck",birthday));
    }
    @PostMapping("/upload")
    public R upload(@RequestParam("file")MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return new R(101010,"文件不能为空",null);
        }
        File directory = new File("E:/MyProject/DR-test/DR-backend/Files/");

        if (!directory.exists()) {
            directory.mkdirs();
        }
        file.transferTo(new File("E:/MyProject/DR-test/DR-backend/Files/"+file.getOriginalFilename()));
        return new R(2000,"success",null);
    }
}
