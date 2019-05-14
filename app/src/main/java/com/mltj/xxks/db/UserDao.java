package com.mltj.xxks.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.mltj.xxks.bean.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class UserDao {
    private Dao<User, Integer> dao;
    private DatabaseHelper helper;

    public UserDao(Context contex) {
        try {
            helper = DatabaseHelper.getHelper(contex);
            dao = helper.getDao(User.class);
            if (dao == null) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     */
    public void add(User object) {
        try {
            dao.create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删（通过实体）
     */
    public void del(User object) {
        try {
            dao.delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删（通过id）
     * @param id
     */
    public void delById(Integer id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     */
    public void update(User object) {
        try {
            dao.update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查
     * @return
     */
    public List<User> queryAll() {
        ArrayList<User> users = null;
        try {
            users = (ArrayList<User>) dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 获取user
     * @param id user编号
     * @return
     */
    public User getById(Integer id) {
        try {
            //父母信息为空
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取user
     * @param name user姓名
     * @return
     */
    public List<User> getByName(String name) {
        try {
            List<User> users = dao.queryBuilder().where().eq("name", name).query();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
