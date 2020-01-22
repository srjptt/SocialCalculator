package com.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suraj on 27/6/19.
 */
@RestController("/springBoot")
public class ApiController {

    @RequestMapping(value = "/health")
    public String health(){
        return "Health is ok!!";
    }

    @RequestMapping(value = "/categoryList" )
    @ResponseBody
    public List<Category> getCategoryList(){
        List<Category> listCategory =new ArrayList<>();
        Category c1= new Category("Casual Shirts","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        Category c2= new Category("Socks","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        Category c3= new Category("Running Shoes","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        Category c4= new Category("Tracksuits","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        Category c5= new Category("Wallets","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        Category c6= new Category("Polo T Shirts","https://n2.sdlcdn.com/imgs/a/b/c/sdtv/casual-shirts.jpg","\"https://www.snapdeal.com/products/lifestyle-socks?sort=plrty&display_name=Socks");
        listCategory.add(c1);
        listCategory.add(c2);
        listCategory.add(c3);
        listCategory.add(c4);
        listCategory.add(c5);
        listCategory.add(c6);
        return listCategory;
    }
}

class Category {
    String title;
    String imgUrl;
    String catUrl;

    public Category(String title, String imgUrl, String catUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.catUrl = catUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCatUrl() {
        return catUrl;
    }

    public void setCatUrl(String catUrl) {
        this.catUrl = catUrl;
    }
}