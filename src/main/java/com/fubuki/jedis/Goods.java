package com.fubuki.jedis;

public class Goods {
    private String goodsName;
    private Integer goodsId;
    private Float price;
    private String title;

    public Goods() {
    }

    public Goods(String goodsName, Integer goodsId, Float price, String title) {
        this.goodsName = goodsName;
        this.goodsId = goodsId;
        this.price = price;
        this.title = title;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
