package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.News;
import com.example.springboot.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends ServiceImpl<NewsMapper, News> {
    @Autowired
    private NewsMapper newsMapper;

    //新增或修改
    public boolean save_news(News news) {

        return saveOrUpdate(news); //mybatis-plus提供的方法，id存在则更新数据，id不存在则增加数据

    }
}
