package com.chinaunicom.wo.flow.services.factory.lottery;

/**
 * 抽奖状态Dto
 * 
 * @author 卢晓昀 2015-07-19
 */
public class LotteryStatusDto {

    /** 是否能抽奖 */
    private boolean m_ifCanLottery = false;

    /** 抽奖ID */
    private int m_ltrID = 0;

    /** 奖品ID */
    private int m_przID = 0;

    /** 奖品名称 */
    private String m_przNm = "";

    /** 状态 */
    private String m_sts = "";

    /**
     * 获得是否能抽奖
     * 
     * @return 是否能抽奖
     */
    public boolean getIfCanLottery() {
        return m_ifCanLottery;
    }

    /**
     * 设置是否能抽奖
     * 
     * @param ifCanLottery
     *            是否能抽奖
     */
    public void setIfCanLottery(boolean ifCanLottery) {
        this.m_ifCanLottery = ifCanLottery;
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
     * 获得状态
     * 
     * @return 状态
     */
    public String getSts() {
        return m_sts;
    }

    /**
     * 设置状态
     * 
     * @param sts
     *            状态
     */
    public void setSts(String sts) {
        this.m_sts = sts;
    }
}