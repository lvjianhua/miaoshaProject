package com.lv_miaoshaProject.service.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 抢购消息类
 *
 */
@Data
public class GetProductMessage implements Serializable {
    public Integer productId;
    public Integer userId;
    public Date recevieDate;
}
