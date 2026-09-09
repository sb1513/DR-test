package com.backend.drbackend.web;


import com.backend.drbackend.entity.Favs;
import com.backend.drbackend.service.FavsService;
import com.backend.drbackend.utils.JWTutil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/favs")
@CrossOrigin(origins = "http://localhost:5173")
public class FavsController {
    @Autowired
    private JWTutil jwtutil;
    @Autowired
    private FavsService favsService;

    @PostMapping("/newfavs")
    public R setFavs(@RequestBody Favs favs) {
        try{
            favs.setCreateTime(LocalDateTime.now());
            favsService.save(favs);
            return new R (2000, "收藏成功", null);
        }catch (Exception e){
            e.printStackTrace();
            return new R (5000, "问题重复",null);
        }
    }

    @GetMapping("/list")
    public R getFavs(@RequestAttribute("user_id") int user_id,int pageNum,int pageSize,String find){
        Page<Favs> pg = new Page<>(pageNum,pageSize);
        QueryWrapper<Favs> qw = new QueryWrapper<>();
        qw.eq("fav_user_id",user_id);
        qw.like(!find.isEmpty(),"fav_ask",find);
        qw.orderByDesc("fav_create_time");
        favsService.page(pg,qw);
        return new R (2000, "获取成功", pg);
    }

    @PostMapping("/update")
    public R updateFavs(@RequestAttribute int user_id, @RequestBody Favs favs){
        try{
            if(favs!=null&&user_id==favs.getUserId()){
                favsService.updateById(favs);
                return new R (2000, "修改成功", null);
            }
            else {
                return new R (5001, "请求失败", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new R (5001, "出现错误", null);
        }
    }

    @PostMapping("/delete")
    public R deleteFavs(@RequestAttribute("user_id")int user_id, @RequestBody Favs favs) {
        QueryWrapper<Favs> queryWrapper = new QueryWrapper<Favs>();
        queryWrapper.eq("fav_id", favs.getFavId());
        queryWrapper.eq("fav_user_id", user_id);
        queryWrapper.eq("fav_ask", favs.getFavAsk());
        Favs nfavs =  favsService.getOne(queryWrapper);
        if(nfavs!=null){
            favsService.removeById(favs.getFavId());
            return new R (2000, "删除成功",null);
        }else {
            return new R (4001, "请求失败",null);
        }
    }
}
