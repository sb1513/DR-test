package com.backend.drbackend.web;


import com.backend.drbackend.entity.Favs;
import com.backend.drbackend.service.FavsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private FavsService favsService;

    @PostMapping("/newfavs")
    public R setFavs(@RequestBody Favs favs) {
        try{
            favs.setCreateTime(LocalDateTime.now());
            favsService.save(favs);
            return new R (2001, "收藏成功", null);
        }catch (Exception e){
            e.printStackTrace();
            return new R (5000, "问题重复",null);
        }
    }

    @GetMapping("/list/{user_id}")
    public R getFavs(@PathVariable int user_id){
        QueryWrapper<Favs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fav_user_id",user_id);
        List<Favs> favs = favsService.list(queryWrapper);
        return new R (2001, "获取成功", favs);
    }

    @PostMapping("/update")
    public R updateFavs(@RequestBody Favs favs){
        try{
            if(favs!=null){
                favsService.updateById(favs);
                return new R (2001, "修改成功", null);
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
    public R deleteFavs(@RequestBody Favs favs) {
        QueryWrapper<Favs> queryWrapper = new QueryWrapper<Favs>();
        queryWrapper.eq("fav_id", favs.getFavId());
        queryWrapper.eq("fav_user_id", favs.getUserId());
        queryWrapper.eq("fav_ask", favs.getFavAsk());
        Favs nfavs =  favsService.getOne(queryWrapper);
        if(nfavs!=null){
            favsService.removeById(favs.getFavId());
            return new R (2001, "删除成功",null);
        }else {
            return new R (4001, "请求失败",null);
        }
    }
}
