package com.fubuki.fubukioa.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    //alibabaDruid与MyBatis兼容使用的数据源工厂类
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

    @Override
    public DataSource getDataSource() {
        try {
            ((DruidDataSource) this.dataSource).init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.dataSource;
    }
}
