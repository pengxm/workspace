package com.chinaunicom.wo.flow.services.factory.lottery;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chinaunicom.wo.flow.cms.aq.aq00.bean.UsrActBean;
import com.chinaunicom.wo.flow.cms.aq.aq00.db.UsrActDb;
import com.chinaunicom.wo.flow.cms.ar.ar00.bean.LtrStBean;
import com.chinaunicom.wo.flow.cms.ar.ar00.db.LtrStDb;
import com.chinaunicom.wo.flow.cms.ar.ar00.db.PrzDb;
import com.chinaunicom.wo.flow.cms.zz.constant.Code;
import com.chinaunicom.wo.flow.services.constant.OnlineKey;
import com.chinaunicom.wo.flow.services.util.PicUtils;
import com.worldleaf.fw.factory.DataBaseFactory;
import com.worldleaf.fw.util.DTUtils;
import com.worldleaf.fw.util.ExceptionUtils;
import com.worldleaf.fw.util.ValidateUtils;

/**
 * 抽奖工厂
 * 
 * @author 李永超 2015-06-18
 * 
 */
public final class LotteryFactory {

    /** log */
    private static Logger m_log = LogManager.getLogger(LotteryFactory.class
            .getName());

    /** 业务更新间隔(一小时：60*60*1000) */
    private static final long REFRESH_INTERVAL_DEF = 3600000L;

    /** 业务更新间隔(一小时：60*60*1000) */
    private static long m_refreshInterval = REFRESH_INTERVAL_DEF;

    /** 业务最后一次更新日时 */
    private static Long m_timeStamp = 0L;

    /** 分隔符 */
    private static final String SP = "_";

    /** 抽奖状态：未开始 */
    public static final String STS_FUTURE = "1";

    /** 抽奖状态：进行中 */
    public static final String STS_NOW = "2";

    /** 抽奖状态：已结束 */
    public static final String STS_PAST = "3";

    /** 抽奖状态：不存在 */
    public static final String STS_NOEXIST = "4";

    /** 用户抽奖状态:未参加 */
    public static final String STS_USR_NOT_ROLL = "5";

    /** 用户抽奖状态:参加:未中奖 */
    public static final String STS_USR_UNLUCK = "6";

    /** 用户抽奖状态:参加:中奖 */
    public static final String STS_USR_LUCK = "7";

    /** 渠道号:抽奖ID */
    private static Map<String, Set<Integer>> m_sprd2LtrId = new ConcurrentSkipListMap<String, Set<Integer>>();

    /** 抽奖ID:抽奖dto */
    private static Map<Integer, LotteryDto> m_ltrId2LtrDto = new ConcurrentSkipListMap<Integer, LotteryDto>();

    /** 抽奖ID:奖品ID */
    private static Map<Integer, TreeSet<Integer>> m_ltrId2PrzId = new ConcurrentSkipListMap<Integer, TreeSet<Integer>>();

    /** 奖品ID:奖品Dto */
    private static Map<Integer, PrzDto> m_przId2PrzDto = new ConcurrentSkipListMap<Integer, PrzDto>();

    /** 电话号码+抽奖ID：判断日期(缓存已抽奖用户) */
    private static Map<String, String> m_ltrTel = new ConcurrentSkipListMap<String, String>();

    /** 中奖总概率 */
    private static final int LTR_VAL_MAX = 1000000;

    /** 常量:8 */
    private static final int C8 = 8;

    /**
     * 获得业务更新间隔
     * 
     * @return 业务更新间隔
     */
    public static long getRefreshInterval() {
        return m_refreshInterval;
    }

    /**
     * 设置业务更新间隔
     * 
     * @param refreshInterval
     *            业务更新间隔
     */
    public static void setRefreshInterval(long refreshInterval) {
        m_refreshInterval = refreshInterval;
    }

    /**
     * 私有构造函数（防止实例化）
     */
    private LotteryFactory() {
        // nothing
    }

    /**
     * 根据抽奖ID获得抽奖信息
     * 
     * @param ltrId
     *            抽奖ID
     * @return 抽奖信息
     */
    public static LotteryDto getLtrDto(int ltrId) {
        if (m_ltrId2LtrDto.containsKey(ltrId)) {
            return m_ltrId2LtrDto.get(ltrId);
        }
        return null;
    }

    /**
     * 根据渠道和抽奖ID判断抽奖是否存在
     * 
     * @param sprdMd5
     *            渠道号
     * @param ltrId
     *            抽奖ID
     * 
     * @return 抽奖信息
     */
    public static boolean ifExistedLottery(String sprdMd5, int ltrId, String ltrTyp) {
        updateCache();

        String sprAll9 = OnlineKey.SPRD_MD5_SP;
        if (m_sprd2LtrId.containsKey(sprAll9)) {
            Set<Integer> setLtrId = m_sprd2LtrId.get(sprAll9);
            if (setLtrId.contains(ltrId)) {
                if (ltrTyp.equals(m_ltrId2LtrDto.get(ltrId).getLtrTyp())) {
                    return true;
                }
            }
        }
        if (m_sprd2LtrId.containsKey(sprdMd5)) {
            Set<Integer> setLtrId = m_sprd2LtrId.get(sprdMd5);
            if (setLtrId.contains(ltrId)) {
                if (ltrTyp.equals(m_ltrId2LtrDto.get(ltrId).getLtrTyp())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据奖品ID判断奖品是否存在
     * 
     * @param przId
     *            选项ID
     * @return boolean true:存在 false:不存在
     */
    public static boolean ifExistedPrize(int przId) {
        if (m_przId2PrzDto.containsKey(przId)) {
            return true;
        }
        return false;
    }
    
    /**
     * 获得某次抽奖的奖品信息
     * 
     * @param ltrId
     *            抽奖ID
     * @return boolean true:存在 false:不存在
     */
    public static List<PrzDto> getPrizeoByLtrId(int ltrId) {
        List<PrzDto> rtnDto = new ArrayList<>();

        if (m_ltrId2PrzId.containsKey(ltrId)) {
            TreeSet<Integer> przIdSet = m_ltrId2PrzId.get(ltrId);

            for (Integer przId : przIdSet) {
                rtnDto.add(m_przId2PrzDto.get(przId));
            }
        }

        return rtnDto;

    }

    /**
     * 根据抽奖ID判断抽奖状态
     * 
     * @param ltrId
     *            抽奖ID
     * @return 抽奖状态
     */
    public static String getLotteryStatus(int ltrId) {
        if (m_ltrId2LtrDto.containsKey(ltrId)) {
            String now = DTUtils.getNow();
            LotteryDto ltrDto = m_ltrId2LtrDto.get(ltrId);
            if (now.compareTo(ltrDto.getBgnDtTm()) < 0) {
                return STS_FUTURE;
            } else if (now.compareTo(ltrDto.getEndDtTm()) > 0) {
                return STS_PAST;
            } else {
                return STS_NOW;
            }
        }
        return STS_NOEXIST;
    }

    /**
     * 抽奖
     * 
     * @param dbt
     *            数据库连接
     * @param ltrId
     *            抽奖ID
     * @param tel
     *            电话号码
     * @return 奖品名称(没中奖是返回空白)
     * @throws SQLException
     *             SQL异常
     */
    public static LotteryStatusDto lottery(Connection dbt, int ltrId, long tel)
            throws SQLException {

        LotteryStatusDto ltrStsDto = new LotteryStatusDto();
        ltrStsDto.setIfCanLottery(false);
        ltrStsDto.setSts(STS_USR_UNLUCK);

        // 缓存已抽奖
        String key = getLotteryKey(tel, ltrId);
        String today = DTUtils.getNowYMD();
        m_ltrTel.put(key, getLotteryValue(today, STS_USR_UNLUCK, 0));

        // 用户活动登记
        UsrActBean usrActBean = new UsrActBean();
        Map<String, Object> mapUsrActInf = UsrActDb.select(dbt, tel, ltrId,
                Code.BSNS_ZZ005_2);
        if (mapUsrActInf.isEmpty()) {
            usrActBean.setMblNum(tel);
            usrActBean.setActId(ltrId);
            usrActBean.setActTyp(Code.BSNS_ZZ005_2);
            usrActBean.setDelFlg(Code.BSNS_ZZ001_1);
            usrActBean.setAddUsr(OnlineKey.USER);
            usrActBean.setAddDtTm(DTUtils.getNow());
            UsrActDb.insert(dbt, usrActBean.bean2map());
        } else {
            usrActBean.map2bean(mapUsrActInf);
            usrActBean.setUptUsr(OnlineKey.USER);
            usrActBean.setUptDtTm(DTUtils.getNow());
            UsrActDb.update(dbt, usrActBean.bean2map());
        }

        // 奖品ID
        int przId = 0;

        // 奖品名称
        String przNm = "";

        // 生成随机数
        int rnd = (new Random()).nextInt(LTR_VAL_MAX) + 1;

        TreeSet<Integer> przInf = m_ltrId2PrzId.get(ltrId);
        for (int przIdTmp : przInf) {
            PrzDto przDto = m_przId2PrzDto.get(przIdTmp);

            // 递减判断
            rnd -= przDto.getWinPrb();

            // 几率范围外
            if (rnd > 0) {
                continue;
            }

            // 缓存中已经没有奖品
            if (przDto.getSrpPrzCnt() <= 0) {
                return ltrStsDto;
            }

            // 取得奖品信息并锁表
            Map<String, Object> przMap = LotteryDb.getTbPrz(dbt, ltrId,
                    przIdTmp);

            // DB内容覆盖缓存Bean
            przDto.map2dto(przMap);

            // 判断DB中是否还有剩余奖品
            if (przDto.getSrpPrzCnt() <= 0) {
                return ltrStsDto;
            }

            // 奖品ID
            przId = przIdTmp;

            // 奖品名称
            przNm = przDto.getPrzNm();

            // 奖品数量减1
            int srpPrzCnt = przDto.getSrpPrzCnt() - 1;
            przDto.setSrpPrzCnt(srpPrzCnt);

            // 更新奖品数量
            LotteryDb.updatePrzInf(dbt, srpPrzCnt, ltrId, przIdTmp);

            LtrStBean ltrStBean = new LtrStBean();
            // 手机号码
            ltrStBean.setMblNum(tel);
            // 抽奖ID
            ltrStBean.setLtrID(ltrId);
            // 奖品ID
            ltrStBean.setPrzID(przIdTmp);
            // 删除标识
            ltrStBean.setDelFlg(Code.BSNS_ZZ001_1);
            // 添加者
            ltrStBean.setAddUsr(OnlineKey.USER);
            // 添加日时
            ltrStBean.setAddDtTm(DTUtils.getNow());
            // 抽奖统计表登记
            LtrStDb.insert(dbt, ltrStBean.bean2map());

            ltrStsDto.setLtrID(ltrId);
            ltrStsDto.setPrzID(przId);
            ltrStsDto.setPrzNm(przNm);
            ltrStsDto.setSts(STS_USR_LUCK);

            // 中奖后跳出循环
            break;
        }

        // 缓存已抽奖
        if (ValidateUtils.isBlank(przNm)) {
            String sts = STS_USR_UNLUCK;
            m_ltrTel.put(key, getLotteryValue(today, sts, 0));
        } else {
            String sts = STS_USR_LUCK;
            m_ltrTel.put(key, getLotteryValue(today, sts, przId));
        }

        return ltrStsDto;
    }

    /**
     * 获得缓存key
     * 
     * @param tel
     *            手机号码
     * @param ltrId
     *            抽奖ID
     * @return String
     */
    public static String getLotteryKey(long tel, int ltrId) {
        StringBuilder sb = new StringBuilder();
        sb.append(tel);
        sb.append(SP);
        sb.append(ltrId);
        return sb.toString();
    }

    /**
     * 获得缓存value
     * 
     * @param day
     *            日期
     * @param sts
     *            抽奖状态
     * @param przId
     *            奖品ID(非中奖状态ID为0)
     * @return String
     */
    public static String getLotteryValue(String day, String sts, int przId) {
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append(SP);
        sb.append(sts);
        sb.append(SP);
        sb.append(przId);
        return sb.toString();
    }

    /**
     * cache code 初始化
     */
    public static void init() {

        Connection dbt = null;
        try {
            dbt = DataBaseFactory.getConnection();
            List<Map<String, Object>> lstLtrInf = LotteryDb.getTbLtr(dbt);
            // 渠道号:抽奖ID
            Map<String, Set<Integer>> sprd2LtrId = new ConcurrentSkipListMap<String, Set<Integer>>();
            // 抽奖ID:抽奖Dto
            Map<Integer, LotteryDto> ltrId2LtrDto = new ConcurrentSkipListMap<Integer, LotteryDto>();
            // 抽奖ID:奖品ID
            Map<Integer, TreeSet<Integer>> ltrId2PrzId = new ConcurrentSkipListMap<Integer, TreeSet<Integer>>();
            // 奖品ID:奖品Dto
            Map<Integer, PrzDto> przId2PrzDto = new ConcurrentSkipListMap<Integer, PrzDto>();
            setLotteryInf(dbt, lstLtrInf, sprd2LtrId, ltrId2LtrDto,
                    ltrId2PrzId, przId2PrzDto);

            m_sprd2LtrId = sprd2LtrId;
            m_ltrId2LtrDto = ltrId2LtrDto;
            m_ltrId2PrzId = ltrId2PrzId;
            m_przId2PrzDto = przId2PrzDto;

            // 当天以外的缓存数据删除
            Iterator<Map.Entry<String, String>> it = m_ltrTel.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String[] inf = entry.getValue().split(SP);
                String day = inf[0];
                String today = DTUtils.getNowYMD();
                if (!day.equals(today)) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            m_log.error(ExceptionUtils.getTrace(e));
        } finally {
            try {
                if (null != dbt) {
                    dbt.close();
                }
            } catch (Exception e) {
                m_log.error(ExceptionUtils.getTrace(e));
            }
        }
    }

    /**
     * 判断是否更新缓存
     */
    private static void updateCache() {
        boolean update = false;
        synchronized (m_timeStamp) {
            long now = DTUtils.getNowMillis();
            if ((0 == m_timeStamp) || ((now - m_timeStamp) > m_refreshInterval)) {
                update = true;
                m_timeStamp = now;
            }
        }

        if (update) {
            init();
        }
    }

    /**
     * 设置抽奖相关信息
     * 
     * @param dbt
     *            数据库连接
     * @param lstLtrInf
     *            抽奖信息列表(DB)
     * @param sprd2LtrId
     *            渠道号:抽奖ID
     * @param ltrId2LtrDto
     *            抽奖ID:抽奖dto
     * @param ltrId2PrzId
     *            抽奖ID:奖品ID
     * @param przId2PrzDto
     *            奖品ID:奖品dto
     * @throws SQLException
     *             SQL异常
     */
    private static void setLotteryInf(Connection dbt,
            List<Map<String, Object>> lstLtrInf,
            Map<String, Set<Integer>> sprd2LtrId,
            Map<Integer, LotteryDto> ltrId2LtrDto,
            Map<Integer, TreeSet<Integer>> ltrId2PrzId,
            Map<Integer, PrzDto> przId2PrzDto) throws SQLException {

        int cnt = lstLtrInf.size();
        for (int i = 0; i < cnt; i++) {
            Map<String, Object> mapLtrInf = lstLtrInf.get(i);
            LotteryDto ltrDto = new LotteryDto();
            ltrDto.map2dto(mapLtrInf);
            ltrDto.setAdPicInf(PicUtils.getCmsPicDto(dbt,
                    (int) mapLtrInf.get("AD_PIC_ID")));
            ltrDto.setBgPicInf(PicUtils.getCmsPicDto(dbt,
                    (int) mapLtrInf.get("BG_PIC_ID")));
            ltrDto.setBtnPicInf(PicUtils.getCmsPicDto(dbt,
                    (int) mapLtrInf.get("BTN_PIC_ID")));
            String sprdMd5 = ltrDto.getSprdMd5();
            int ltrId = ltrDto.getLtrID();

            // 渠道号:抽奖ID
            if (!sprd2LtrId.containsKey(sprdMd5)) {
                sprd2LtrId.put(sprdMd5, new HashSet<Integer>());
            }
            sprd2LtrId.get(sprdMd5).add(ltrId);

            // 抽奖ID:抽奖信息
            ltrId2LtrDto.put(ltrId, ltrDto);

            // 奖品信息
            List<Map<String, Object>> lstPrz = PrzDb.getPrzInf(dbt, ltrId);
            TreeSet<Integer> setPrzId = new TreeSet<Integer>();
            for (int j = 0; j < lstPrz.size(); j++) {
                PrzDto przDto = new PrzDto();
                przDto.map2dto(lstPrz.get(j));

                int przId = przDto.getPrzID();
                setPrzId.add(przId);

                // 奖品ID:奖品Dto
                przId2PrzDto.put(przId, przDto);
            }

            // 抽奖ID:奖品ID
            ltrId2PrzId.put(ltrId, setPrzId);
        }
    }

    /**
     * 是否能抽奖
     * 
     * @param dbt
     *            数据库连接
     * @param tel
     *            手机号码
     * @param ltrId
     *            抽奖ID
     * @return 抽奖状态Dto
     * @throws SQLException
     *             SQL异常
     * @throws ParseException
     *             类型转换异常
     */
    public static LotteryStatusDto ifCanLottery(Connection dbt, long tel,
            int ltrId) throws SQLException, ParseException {
        LotteryStatusDto ltrStsDto = new LotteryStatusDto();
        ltrStsDto.setIfCanLottery(false);
        ltrStsDto.setSts(STS_USR_NOT_ROLL);
        ltrStsDto.setLtrID(ltrId);
        String key = getLotteryKey(tel, ltrId);
        if (m_ltrTel.containsKey(key)) {
            String value = m_ltrTel.get(key);
            String[] inf = value.split(SP);
            String day = inf[0];
            String sts = inf[1];
            String przId = inf[2];
            if (DTUtils.getNowYMD().equals(day)) {

                // 缓存命中(已抽奖)
                ltrStsDto.setSts(sts);
                if (STS_USR_LUCK.equals(sts)) {
                    int intPrzId = Integer.parseInt(przId);
                    if (m_przId2PrzDto.containsKey(intPrzId)) {
                        PrzDto przDto = m_przId2PrzDto.get(intPrzId);
                        ltrStsDto.setPrzID(przDto.getPrzID());
                        ltrStsDto.setPrzNm(przDto.getPrzNm());
                    }
                }
                return ltrStsDto;
            } else {

                // 过期数据缓存删除
                m_ltrTel.remove(key);
            }
        }

        Map<String, Object> mapUsrActInf = UsrActDb.select(dbt, tel, ltrId,
                Code.BSNS_ZZ005_2);
        if (mapUsrActInf.isEmpty()) {

            // 没参加过的用户可抽奖
            ltrStsDto.setIfCanLottery(true);
            return ltrStsDto;
        }

        UsrActBean usrActBean = new UsrActBean();
        usrActBean.map2bean(mapUsrActInf);
        String addDay = usrActBean.getAddDtTm();
        if (addDay.length() > C8) {
            addDay = addDay.substring(0, C8);
        }
        String uptDay = usrActBean.getUptDtTm();
        if (uptDay.length() > C8) {
            uptDay = uptDay.substring(0, C8);
        }

        String today = DTUtils.getNowYMD();

        // 当日是否已抽奖
        boolean ifTodayHave = false;

        if (DTUtils.getDateInterval(addDay, today) == 0) {
            ifTodayHave = true;
        } else if (!ValidateUtils.isBlank(uptDay)
                && DTUtils.getDateInterval(uptDay, today) == 0) {
            ifTodayHave = true;
        }

        // 是否能抽奖
        boolean canLtr = true;

        // 当天已抽过
        if (ifTodayHave) {
            canLtr = false;
            setLtrStsDto(dbt, tel, ltrId, ltrStsDto);

            // 当天未抽过(每天只有一次抽奖机会)
        } else {

            // 已经中奖的用户不可抽奖
            if (setLtrStsDto(dbt, tel, ltrId, ltrStsDto)) {
                canLtr = false;
            }
        }

        // 是否能抽奖
        ltrStsDto.setIfCanLottery(canLtr);

        // 当天已抽过,缓存处理
        if (ifTodayHave) {
            String sts = ltrStsDto.getSts();
            if (STS_USR_LUCK.equals(sts)) {
                m_ltrTel.put(key,
                        getLotteryValue(today, sts, ltrStsDto.getPrzID()));
            } else {
                m_ltrTel.put(key, getLotteryValue(today, sts, 0));
            }
        }

        return ltrStsDto;
    }

    /**
     * 设置抽奖状态Dto
     * 
     * @param dbt
     *            数据库连接
     * @param tel
     *            手机号码
     * @param ltrId
     *            抽奖ID
     * @param ltrStsDto
     *            抽奖状态Dto
     * @return 是否中奖
     * @throws SQLException
     *             SQL异常
     */
    private static boolean setLtrStsDto(Connection dbt, long tel, int ltrId,
            LotteryStatusDto ltrStsDto) throws SQLException {

        boolean ifLuck = false;
        List<Map<String, Object>> lstLuck = LotteryDb.getLuckInf(dbt, tel,
                ltrId);

        // 抽中
        if (lstLuck.size() > 0) {
            LtrStBean ltrStBean = new LtrStBean();
            ltrStBean.map2bean(lstLuck.get(0));
            int przId = ltrStBean.getPrzID();
            ltrStsDto.setLtrID(ltrId);
            if (m_przId2PrzDto.containsKey(przId)) {
                ltrStsDto.setPrzID(przId);
                ltrStsDto.setPrzNm(m_przId2PrzDto.get(przId).getPrzNm());
            }

            ltrStsDto.setSts(STS_USR_LUCK);
            ifLuck = true;

            // 未抽中
        } else {
            ltrStsDto.setSts(STS_USR_UNLUCK);
        }

        return ifLuck;
    }
}
