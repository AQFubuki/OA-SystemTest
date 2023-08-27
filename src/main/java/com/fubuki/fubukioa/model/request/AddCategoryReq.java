//package com.fubuki.fubukioa.model.request;
//
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//public class AddCategoryReq {
//    @NotNull(message = "name不能为null")
//    @Size(min = 2, max = 12)
//    private String name;
//    @NotNull(message = "type不能为null")
//    @Max(3)
//    @Min(1)
//    private Integer type;
//    @NotNull(message = "parentId不能为null")
//    private Long parentId;
//    @NotNull(message = "orderNum不能为null")
//    private Integer orderNum;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    public Integer getOrderNum() {
//        return orderNum;
//    }
//
//    public void setOrderNum(Integer orderNum) {
//        this.orderNum = orderNum;
//    }
//}
