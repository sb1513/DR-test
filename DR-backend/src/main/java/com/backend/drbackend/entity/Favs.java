package com.backend.drbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

@TableName("favs")
public class Favs {
    @TableId(value = "fav_id",type = IdType.AUTO)
    private int favId;
    @TableField("fav_user_id")
    private int userId;
    @TableField("fav_ask")
    private String favAsk;
    @TableField("fav_ans")
    private String favAns;
    @TableField("fav_create_time")
    private LocalDateTime createTime;

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFavAsk() {
        return favAsk;
    }

    public void setFavAsk(String favAsk) {
        this.favAsk = favAsk;
    }

    public String getFavAns() {
        return favAns;
    }

    public void setFavAns(String favAns) {
        this.favAns = favAns;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
