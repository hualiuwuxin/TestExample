package com.lomi.entity;

import com.lomi.utils.CodeUtil;

import cn.hutool.core.util.IdUtil;

public class Goods {
    private Long id;

    private String name;

    private Integer stock;

    private String des;

    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
    
    
    
    
	  
    @Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", stock=" + stock + ", des=" + des + ", data=" + data + "]";
	}
    
    
	public static Goods randomGoods() {
    	Goods goods = new Goods();
		goods.setId(IdUtil.getSnowflakeNextId());
		goods.setData(  CodeUtil.getRandomNum18() );
		goods.setDes( CodeUtil.randomCode(6) );
		goods.setName( CodeUtil.randomCode(3) );
		goods.setStock( Integer.valueOf( CodeUtil.randomNumCode(4) ) );
		
    	return goods;
    }
	
	public static void main(String[] args) {
		 System.out.println( Long.MAX_VALUE ); 
	}
    
}