/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2019/7/15 @author xinjie
 */
package com.example.springcpudemo.pojo;

import lombok.Data;

import javax.persistence.*;


/**
 * @ClassName BookInfo
 * @Description TODO
 * @Author xinjie
 * @CreateDate 2019/7/15 9:53
 */
@Entity
@Data
@Table(name = "book")
public class BookInfo {

    @Id
    @Column(unique = true, length = 16, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 64)
    private String pubisher;

    @Transient
    private long getCurrentTime;

}
