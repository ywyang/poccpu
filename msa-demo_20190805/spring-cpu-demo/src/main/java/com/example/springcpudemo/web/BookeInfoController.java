/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2019/7/15 @author xinjie
 */
package com.example.springcpudemo.web;



import com.example.springcpudemo.pojo.BookInfo;
import com.example.springcpudemo.repository.BookeInfoRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @ClassName BookeInfoController
 * @Description TODO
 * @Author xinjie
 * @CreateDate 2019/7/15 10:36
 */
@RestController
public class BookeInfoController {

    @Autowired
    private BookeInfoRegistry bookeInfoRegistry;
    
    private Random random = new  Random();


    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookInfo getBookeInfo(@PathVariable("id") Integer id) {
        BookInfo bookInfo = bookeInfoRegistry.getBookInfoById(random.nextInt(id) + 1);
        if(bookInfo != null){
            bookInfo.setGetCurrentTime(System.currentTimeMillis());
            return bookInfo;
        }else {
            return null;
        }
    }
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public void saveBookeInfo() {
        System.out.println("begin save bookinfo !...........................");
        List<BookInfo> books = new ArrayList<>();
        long beginTime = System.currentTimeMillis();
        for(int j=1,i=1; j<=1000000; j++,i++){
            BookInfo bookInfo = new BookInfo();
            bookInfo.setId(j);
            bookInfo.setName("name-test-" + j);
            bookInfo.setPubisher("pub-test" + j);
            books.add(bookInfo);
            if (i == 10000) {
                bookeInfoRegistry.saveAll(books);
                i = 0;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("init bookinfo uses: " + ((endTime - beginTime) / 1000) + "s.");
    }
}

