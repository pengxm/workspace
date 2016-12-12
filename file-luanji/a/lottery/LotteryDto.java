package com.chinaunicom.wo.flow.services.factory.lottery;

import java.util.Map;

import com.chinaunicom.wo.flow.services.dto.ImageInfDto;

/**
 * 抽奖Dto
 * 
 * @author 李永超 2015-06-18
 */
public class LotteryDto {

    /** 抽奖ID */
    private int m_ltrID = 0;

    /** 渠道号 */
    private String m_sprdMd5 = "";
    
    /** 抽奖类型 */
    private String m_ltrTyp = "";

    /** 抽奖标题 */
    private String m_ltrTtl = "";

    /** 抽奖内容 */
    private String m_ltrCon = "";

    /** 后记 */
    private String m_endCon = "";

    /** 开始日时 */
    private String m_bgnDtTm = "";

    /** 结束日时 */
    private String m_endDtTm = "";

    /** 宣传图片信息 */
    private ImageInfDto m_adPicInf = null;

    /** 背景图片信息 */
    private ImageInfDto m_bgPicInf = null;
    
    /** 按钮图片信息 */
    private ImageInfDto m_btnPicInf = null;

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
     * 获得渠道号
     * 
     * @return 渠道号
     */
    public String getSprdMd5() {
        return m_sprdMd5;
    }

    /**
     * 设置渠道号
     * 
     * @param sprdMd5
     *            渠道号
     */
    public void setSprdMd5(String sprdMd5) {
        this.m_sprdMd5 = sprdMd5;
    }
    
    /**
     * 获得抽奖类型
     * 
     * @return 抽奖类型
     */
    public String getLtrTyp() {
        return m_ltrTyp;
    }

    /**
     * 设置抽奖类型
     * 
     * @param ltrTyp
     *            抽奖类型
     */
    public void setLtrTyp(String ltrTyp) {
        this.m_ltrTyp = ltrTyp;
    }

    /**
     * 获得抽奖标题
     * 
     * @return 抽奖标题
     */
    public String getLtrTtl() {
        return m_ltrTtl;
    }

    /**
     * 设置抽奖标题
     * 
     * @param ltrTtl
     *            抽奖标题
     */
    public void setLtrTtl(String ltrTtl) {
        this.m_ltrTtl = ltrTtl;
    }

    /**
     * 获得抽奖内容
     * 
     * @return 抽奖内容
     */
    public String getLtrCon() {
        return m_ltrCon;
    }

    /**
     * 设置抽奖内容
     * 
     * @param ltrCon
     *            抽奖内容
     */
    public void setLtrCon(String ltrCon) {
        this.m_ltrCon = ltrCon;
    }

    /**
     * 获得后记
     * 
     * @return 后记
     */
    public String getEndCon() {
        return m_endCon;
    }

    /**
     * 设置后记
     * 
     * @param endCon
     *            后记
     */
    public void setEndCon(String endCon) {
        this.m_endCon = endCon;
    }

    /**
     * 获得开始日时
     * 
     * @return 开始日时
     */
    public String getBgnDtTm() {
        return m_bgnDtTm;
    }

    /**
     * 设置开始日时
     * 
     * @param bgnDtTm
     *            开始日时
     */
    public void setBgnDtTm(String bgnDtTm) {
        this.m_bgnDtTm = bgnDtTm;
    }

    /**
     * 获得结束日时
     * 
     * @return 结束日时
     */
    public String getEndDtTm() {
        return m_endDtTm;
    }

    /**
     * 设置结束日时
     * 
     * @param endDtTm
     *            结束日时
     */
    public void setEndDtTm(String endDtTm) {
        this.m_endDtTm = endDtTm;
    }

    /**
     * 获得宣传图片信息
     * 
     * @return 宣传图片信息
     */
    public ImageInfDto getAdPicInf() {
        return m_adPicInf;
    }

    /**
     * 设置宣传图片信息
     * 
     * @param adPicInf
     *            宣传图片信息
     */
    public void setAdPicInf(ImageInfDto adPicInf) {
        this.m_adPicInf = adPicInf;
    }

    /**
     * 获得外部链接
     * 
     * @return 外部链接
     */
    public ImageInfDto getBgPicInf() {
        return m_bgPicInf;
    }

    /**
     * 设置外部链接
     * 
     * @param bgPicInf
     *            外部链接
     */
    public void setBgPicInf(ImageInfDto bgPicInf) {
        this.m_bgPicInf = bgPicInf;
    }

    /**
     * 获得按钮图片信息
     * 
     * @return 按钮图片信息
     */
    public ImageInfDto getBtnPicInf() {
        return m_btnPicInf;
    }

    /**
     * 设置按钮图片信息
     * 
     * @param btnPicInf
     *            按钮图片信息
     */
    public void setBtnPicInf(ImageInfDto btnPicInf) {
        this.m_btnPicInf = btnPicInf;
    }

    /**
     * map2dto
     * 
     * @param map
     *            信息map
     */
    public void map2dto(Map<String, Object> map) {
        if (null != map && !map.isEmpty()) {

            // 抽奖ID
            if (map.containsKey("LTR_ID")) {
                setLtrID((int) map.get("LTR_ID"));
            }

            // 渠道号
            if (map.containsKey("SPRD_MD5")) {
                setSprdMd5((String) map.get("SPRD_MD5"));
            }
            
            // 抽奖类型
            if (map.containsKey("LTR_TYP")) {
                setLtrTyp((String) map.get("LTR_TYP"));
            }

            // 抽奖标题
            if (map.containsKey("LTR_TTL")) {
                setLtrTtl((String) map.get("LTR_TTL"));
            }

            // 抽奖内容
            if (map.containsKey("LTR_CON")) {
                setLtrCon((String) map.get("LTR_CON"));
            }

            // 后记
            if (map.containsKey("END_CON")) {
                setEndCon((String) map.get("END_CON"));
            }

            // 开始日时
            if (map.containsKey("BGN_DT_TM")) {
                setBgnDtTm((String) map.get("BGN_DT_TM"));
            }

            // 结束日时
            if (map.containsKey("END_DT_TM")) {
                setEndDtTm((String) map.get("END_DT_TM"));
            }
        }
    }
}