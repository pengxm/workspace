package com.chinaunicom.wo.flow.services.factory.lottery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.worldleaf.fw.dto.SqlPrmDto;
import com.worldleaf.fw.factory.DataBaseFactory;

/**
 * 抽奖Db
 * 
 * @author 李永超 2015-06-18
 */
public final class LotteryDb {

    /**
     * 私有构造函数（防止实例化）
     */
    private LotteryDb() {
        // nothing
    }

    /**
     * 获得中奖信息
     * 
     * @param dbt
     *            数据库连接
     * @param tel
     *            手机号码
     * @param ltrId
     *            抽奖ID
     * 
     * @return 是否参加过抽奖
     * @throws SQLException
     *             SQL异常
     */
    public static List<Map<String, Object>> getLuckInf(Connection dbt,
            long tel, int ltrId) throws SQLException {
        List<SqlPrmDto> lstParam = new ArrayList<SqlPrmDto>();
        lstParam.add(new SqlPrmDto(Types.BIGINT, tel));
        lstParam.add(new SqlPrmDto(Types.INTEGER, ltrId));
        return DataBaseFactory.selDBRtnLst(dbt, "lotteryfactory.001", lstParam);
    }

    /**
     * 取得奖品信息并锁表
     * 
     * @param dbt
     *            数据库连接
     * @param ltrID
     *            抽奖ID
     * @param przID
     *            奖品ID
     * @return 奖品查询结果
     * @throws SQLException
     *             SQL异常
     */
    public static Map<String, Object> getTbPrz(Connection dbt, int ltrID,
            int przID) throws SQLException {
        List<SqlPrmDto> lstParam = new ArrayList<SqlPrmDto>();
        lstParam.add(new SqlPrmDto(Types.INTEGER, ltrID));
        lstParam.add(new SqlPrmDto(Types.INTEGER, przID));
        return DataBaseFactory.selDBRtnMap(dbt, "lotteryfactory.002", lstParam);
    }

    /**
     * 查询抽奖信息
     * 
     * @param dbt
     *            数据库连接
     * @return 抽奖查询结果
     * @throws SQLException
     *             SQL异常
     */
    public static List<Map<String, Object>> getTbLtr(Connection dbt)
            throws SQLException {
        return DataBaseFactory.selDBRtnLst(dbt, "lotteryfactory.003");
    }

    /**
     * 更新奖品数量
     * 
     * @param dbt
     *            数据库连接
     * @param srpPrzCnt
     *            剩余奖品数量
     * @param ltrID
     *            抽奖ID
     * @param przID
     *            奖品ID
     * @return 更新件数
     * @throws SQLException
     *             SQL异常
     */
    public static int updatePrzInf(Connection dbt, int srpPrzCnt, int ltrID,
            int przID) throws SQLException {
        List<SqlPrmDto> lstParam = new ArrayList<SqlPrmDto>();
        lstParam.add(new SqlPrmDto(Types.INTEGER, srpPrzCnt));
        lstParam.add(new SqlPrmDto(Types.INTEGER, ltrID));
        lstParam.add(new SqlPrmDto(Types.INTEGER, przID));
        return DataBaseFactory.updateDB(dbt, "lotteryfactory.004", lstParam);
    }
}
