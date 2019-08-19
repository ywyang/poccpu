/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2019/7/15 @author xinjie
 */
package com.example.springcpudemo.repository;



import com.example.springcpudemo.pojo.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName BookeInfoRegistry
 * @Description TODO
 * @Author xinjie
 * @CreateDate 2019/7/15 10:25
 */
public interface BookeInfoRegistry extends JpaRepository<BookInfo, Integer> {
    BookInfo getBookInfoById(Integer id);
}
