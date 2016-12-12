package com.chinaunicom.wo.flow.services.page.act;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.chinaunicom.wo.flow.services.action.PageBaseAction;
import com.chinaunicom.wo.flow.services.constant.OnlineMsg;
import com.chinaunicom.wo.flow.services.factory.lottery.LotteryDto;
import com.chinaunicom.wo.flow.services.factory.lottery.LotteryFactory;
import com.chinaunicom.wo.flow.services.factory.lottery.LotteryStatusDto;
import com.chinaunicom.wo.flow.services.factory.lottery.PrzDto;
import com.worldleaf.fw.dto.MessageDto;
import com.worldleaf.fw.factory.DomainFactory;
import com.worldleaf.fw.util.ValidateUtils;

/**
 * 抽奖
 * 
 * @author 李永超 2015-06-18
 * 
 */
public class ACT005Action extends PageBaseAction {

    /** 序列化ID */
    private static final long serialVersionUID = 1L;

    /** 业务ID */
    private static final String BUSSINESS_ID = "ACT005";

    /** 常量:8 */
    protected static final int M8 = 8;

    /** 抽奖ID */
    private String m_ltrId = "";

    /** 抽奖Dto */
    private LotteryDto m_ltrInf = new LotteryDto();

    /** 请求方式 */
    private String m_reqTyp = "1";

    /** 奖品名称列表 */
    private String m_przNmJson = "";
    
    /** 奖品信息 */
    private String m_przInf = "";

    /**
     * 获得抽奖ID
     * 
     * @return 抽奖ID
     */
    public String getLtrId() {
        return m_ltrId;
    }

    /**
     * 设置抽奖ID
     * 
     * @param ltrId
     *            抽奖ID
     */
    public void setLtrId(String ltrId) {
        this.m_ltrId = ltrId;
    }

    /**
     * 获得抽奖Dto
     * 
     * @return 抽奖Dto
     */
    public LotteryDto getLtrInf() {
        return m_ltrInf;
    }

    /**
     * 设置抽奖Dto
     * 
     * @param ltrInf
     *            抽奖Dto
     */
    public void setLtrInf(LotteryDto ltrInf) {
        this.m_ltrInf = ltrInf;
    }

    /**
     * 获得请求方式
     * 
     * @return 请求方式
     */
    public String getReqTyp() {
        return m_reqTyp;
    }

    /**
     * 设置请求方式
     * 
     * @param reqTyp
     *            请求方式
     */
    public void setReqTyp(String reqTyp) {
        this.m_reqTyp = reqTyp;
    }

    /**
     * 获得奖品名称列表
     * 
     * @return 奖品名称列表
     */
    public String getPrzNmJson() {
        return m_przNmJson;
    }

    /**
     * 设置奖品名称列表
     * 
     * @param przNmJson
     *            奖品名称列表
     */
    public void setPrzNmJson(String przNmJson) {
        this.m_przNmJson = przNmJson;
    }
    
    /**
     * 获得奖品信息
     * 
     * @return 奖品信息
     */
    public String getPrzInf() {
        return m_przInf;
    }

    /**
     * 设置奖品信息
     * 
     * @param przInf
     *            奖品信息
     */
    public void setPrzInf(String przInf) {
        this.m_przInf = przInf;
    }

    /**
     * 获得业务ID
     * 
     * @return 业务ID
     */
    public String getBussinessID() {
        return BUSSINESS_ID;
    }

    /**
     * 准备
     * 
     * @param dbt
     *            数据库连接
     * @param msg
     *            消息存放池
     * @return 返回success时，继续执行;返回success以外时，返回值为跳转页面的jsp标识；
     * @throws Exception
     *             异常
     */
    public String prepare(Connection dbt, MessageDto msg) throws Exception {

        // session情报使用(防止信息篡改)
        String tkn = getTkn();
        String snTel = getBaseDto().getSnTel();
        if (ValidateUtils.isBlank(tkn) && !ValidateUtils.isBlank(snTel)) {
            setTel(snTel);
            return SUCCESS;
        }

        String tel = getTel();
        if (ValidateUtils.isBlank(tel) && m_reqTyp.equals("1")) {
            return SUCCESS;
        }

        return ifGotoLogin(msg);
    }

    /**
     * 检查
     * 
     * @param dbt
     *            数据库连接
     * @param msg
     *            消息存放池
     * @return 返回success时，继续执行;返回success以外时，返回值为跳转页面的jsp标识；
     * @throws Exception
     *             异常
     */
    public String check(Connection dbt, MessageDto msg) throws Exception {

        // 抽奖ID的独立检查
        if (!checkLtrId(msg)) {
            return SYSERROR;
        }

        int ltrId = Integer.parseInt(getLtrId());
        setLtrInf(LotteryFactory.getLtrDto(ltrId));

        List<PrzDto> przList = LotteryFactory.getPrizeoByLtrId(ltrId);

        List<String> przNameList = new ArrayList<>();

        for (PrzDto dto : przList) {
            przNameList.add(dto.getPrzNm());
        }

        String nmJsonString = JSONArray.fromObject(przNameList).toString();

        setPrzNmJson(nmJsonString);

        if (m_reqTyp.equals("2")) {
            // 抽奖状态
            String sts = LotteryFactory.getLotteryStatus(ltrId);

            // 抽奖进行中
            if (LotteryFactory.STS_NOW.equals(sts)) {

                LotteryStatusDto ltrStsDto = LotteryFactory.ifCanLottery(dbt,
                        Long.parseLong(getTel()), ltrId);

                // 抽奖状态
                String ltrSts = ltrStsDto.getSts();

                // 不能抽奖
                if (!ltrStsDto.getIfCanLottery()) {

                    // 已中奖
                    if (LotteryFactory.STS_USR_LUCK.equals(ltrSts)) {

                      setPrzInf("啥也没中");
                      return ERROR;
                        // 没中奖时一天只能抽一次
                    } else if (LotteryFactory.STS_USR_UNLUCK.equals(ltrSts)) {
                        setPrzInf("中过了别闹");
                        return ERROR;
                    }
                }

                // 抽奖已结束
            } else if (LotteryFactory.STS_PAST.equals(sts)) {
                msg.addErrMsg(OnlineMsg.MACT0303E);
                return ERROR;

                // 抽奖未开始
            } else {
                msg.addErrMsg(OnlineMsg.MACT0302E);
                return ERROR;
            }
        }

        // 能抽奖
        return SUCCESS;
    }

    /**
     * 业务处理
     * 
     * @param dbt
     *            数据库连接
     * @param msg
     *            消息存放池
     * @return 返回success时，继续执行;返回success以外时，返回值为跳转页面的jsp标识;
     * @throws Exception
     *             异常
     */
    public String bussinessProcess(Connection dbt, MessageDto msg)
            throws Exception {

        if (m_reqTyp.equals("2")) {
            int ltrId = Integer.parseInt(getLtrId());
            long tel = Long.parseLong(getTel());

            // 抽奖
            LotteryStatusDto ltrStsDto = LotteryFactory
                    .lottery(dbt, ltrId, tel);

            // 抽奖状态
            String ltrSts = ltrStsDto.getSts();

            // 中奖
            if (LotteryFactory.STS_USR_LUCK.equals(ltrSts)) {

                msg.addPromptMsg(OnlineMsg.MACT0301I);
                return SUCCESS;
            }

            // 没中奖
            msg.addErrMsg(OnlineMsg.MACT0305E);
            return GOTO;
        }
        return SUCCESS;
    }

    /**
     * 获得SSO登录处理的回调URL
     * 
     * @return 页面跳转路径
     */
    public String getSsoCallBackUrl() {
        return getSsoCallBackUrl("act005");
    }

    /**
     * 获得SSO登录处理的回调URL
     * 
     * @param id
     *            页面ID
     * @return 页面跳转路径
     */
    protected String getSsoCallBackUrl(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append(DomainFactory.getOnlineDomain());
        sb.append("/page/");
        sb.append(id);
        sb.append("?head.cfg=");
        sb.append(getHead().getCfg());
        sb.append("&foot.cfg=");
        sb.append(getFoot().getCfg());
        sb.append("&ltrId=");
        sb.append(getLtrId());
        sb.append("&channel=");
        sb.append(getChannel());
        sb.append("&reqTyp=2");
        return sb.toString();
    }

    /**
     * 抽奖ID的独立检查
     * 
     * @param msg
     *            检查信息dto
     * @return 检查结果:true：成功；false：失败
     */
    public boolean checkLtrId(MessageDto msg) {

        String ltrId = getLtrId();

        if (ValidateUtils.isBlank(ltrId)) {
            msg.addErrMsg(OnlineMsg.MACT0301E);
            return false;
        }

        if (ltrId.length() > M8) {
            msg.addErrMsg(OnlineMsg.MACT0301E);
            return false;
        }

        if (!ValidateUtils.isNumeric(ltrId)) {
            msg.addErrMsg(OnlineMsg.MACT0301E);
            return false;
        }

        // 抽奖ID的是否存在
        if (!LotteryFactory.ifExistedLottery(getChannel(),
                Integer.parseInt(ltrId), "2")) {
            msg.addErrMsg(OnlineMsg.MACT0301E);
            return false;
        }

        return true;
    }
}
