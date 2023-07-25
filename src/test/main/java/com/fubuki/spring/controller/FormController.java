package com.fubuki.spring.controller;


import com.fubuki.spring.entity.Form;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
public class FormController {
    //@PostMapping("/apply")
    @ResponseBody
    public String apply(String name, String course, Integer[] purpose){
        System.out.println(name);
        System.out.println(course);
        String s="";
        for (Integer p : purpose) {
            System.out.println(p);
            s+=String.valueOf(p);
        }
        return "SUCCESS---"+name+":"+course+"===="+s;
    }

    //@PostMapping("/apply")
    @ResponseBody
    public String apply(String name, String course, @RequestParam List<Integer> purpose){
        System.out.println(name);
        System.out.println(course);
        String s="";
        for (Integer p : purpose) {
            System.out.println(p);
            s+=String.valueOf(p);
        }
        return "list SUCCESS---"+name+":"+course+"===="+s;
    }

    //@PostMapping("/apply")
    @ResponseBody
    public String apply(Form form){
        return "form SUCCESS===="+form.getName()+"   :"+form.getCourse()+form.getPurpose();
    }
    @PostMapping("/apply")
    @ResponseBody
    public String applyDelivery(Form form){
        return "formDelivery SUCCESS===="
                +form.getName()+"   :"+form.getCourse()+form.getPurpose()
                +form.getDelivery();
    }
}
