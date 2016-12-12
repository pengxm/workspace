package com.chinaunicom.wo.flow.services.factory.lottery;

import java.util.Map;

/**
 * 奖品dto
 * 
 * @author 卢晓昀 2015-07-19
 */
public class PrzDto {

    /** 奖品ID */
    private int m_przID = 0;

    /** 抽奖ID */
    private int m_ltrID = 0;

    /** 奖品名称 */
    private String m_przNm = "";

    /** 中奖概率 */
    private int m_winPrb = 0;

    /** 数量上限 */
    private int m_maxPrzCnt = 0;

    /** 剩余数量 */
    private int m_srpPrzCnt = 0;

    /**
     * 获得奖品ID
     * 
     * @return 奖品ID
     */
    public int getPrzID() {
        return m_przID;
    }

    /**
     * 设置奖品ID
     * 
     * @param przID
     *            奖品ID
     */
    public void setPrzID(int przID) {
        this.m_przID = przID;
    }

    /**
     * 获得抽奖ID
     * 
     * @return 抽奖ID
     */
    public int getLtrID() {
        return m_ltrID;
    }

    /**
     * 设置抽奖ID
     * 
     * @param ltrID
     *            抽奖ID
     */
    public void setLtrID(int ltrID) {
        this.m_ltrID = ltrID;
    }

    /**
     * 获得奖品名称
     * 
     * @return 奖品名称
     */
    public String getPrzNm() {
        return m_przNm;
    }

    /**
     * 设置奖品名称
     * 
     * @param przNm
     *            奖品名称
     */
    public void setPrzNm(String przNm) {
        this.m_przNm = przNm;
    }

    /**
     * 获得中奖概率
     * 
     * @return 中奖概率
     */
    public int getWinPrb() {
        return m_winPrb;
    }

    /**
     * 设置中奖概率
     * 
     * @param winPrb
     *            中奖概率
     */
    public void setWinPrb(int winPrb) {
        this.m_winPrb = winPrb;
    }

    /**
     * 获得数量上限
     * 
     * @return 数量上限
     */
    public int getMaxPrzCnt() {
        return m_maxPrzCnt;
    }

    /**
     * 设置数量上限
     * 
     * @param maxPrzCnt
     *            数量上限
     */
    public void setMaxPrzCnt(int maxPrzCnt) {
        this.m_maxPrzCnt = maxPrzCnt;
    }

    /**
     * 获得剩余数量
     * 
     * @return 剩余数量
     */
    public int getSrpPrzCnt() {
        return m_srpPrzCnt;
    }

    /**
     * 设置剩余数量
     * 
     * @param srpPrzCnt
     *            剩余数量
     */
    public void setSrpPrzCnt(int srpPrzCnt) {
        this.m_srpPrzCnt = srpPrzCnt;
    }

    /**
     * map2dto
     * 
     * @param map
     *            信息map
     */
    public void map2dto(Map<String, Object> map) {
        if (null != map && !map.isEmpty()) {

            // 奖品ID
            if (map.containsKey("PRZ_ID")) {
                setPrzID((int) map.get("PRZ_ID"));
            }

            // 抽奖ID
            if (map.containsKey("LTR_ID")) {
                setLtrID((int) map.get("LTR_ID"));
            }

            // 奖品名称
            if (map.containsKey("PRZ_NM")) {
                setPrzNm((String) map.get("PRZ_NM"));
            }

            // 中奖概率
            if (map.containsKey("WIN_PRB")) {
                // 中奖概率无法直接强转为整型
                setWinPrb((int) map.get("WIN_PRB"));
            }

            // 数量上限
            if (map.containsKey("MAX_PRZ_CNT")) {
                setMaxPrzCnt((int) map.get("MAX_PRZ_CNT"));
            }

            // 剩余数量
            if (map.containsKey("SRP_PRZ_CNT")) {
                setSrpPrzCnt((int) map.get("SRP_PRZ_CNT"));
            }
        }
    }
}