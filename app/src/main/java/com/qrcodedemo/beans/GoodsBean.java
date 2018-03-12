package com.qrcodedemo.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LG on 2018/3/9.
 */
@Entity
public class GoodsBean {
    @Id
    private long id;
    @NotNull
    private String name;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 881765383)
    public GoodsBean(long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1806305570)
    public GoodsBean() {
    }

}
