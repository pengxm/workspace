lotteryfactory.001= \
select MBL_NUM                                  \ # 手机号码
       ,LTR_ID                                  \ # 抽奖ID
       ,PRZ_ID                                  \ # 奖品ID
  from TB_LTR_ST                                \ # 抽奖统计
 where MBL_NUM = ?                              \ # 手机号码
   and LTR_ID = ?                                 # 抽奖ID

lotteryfactory.002= \
select PRZ_ID                                   \ # 抽奖ID
       ,LTR_ID                                  \ # 奖品ID
       ,PRZ_NM                                  \ # 奖品名称
       ,WIN_PRB                                 \ # 中奖概率
       ,MAX_PRZ_CNT                             \ # 数量上限
       ,SRP_PRZ_CNT                             \ # 剩余数量
       ,DEL_FLG                                 \ # 删除标识
       ,ADD_USR                                 \ # 添加者
       ,ADD_DT_TM                               \ # 添加日时
       ,UPT_USR                                 \ # 修改者
       ,UPT_DT_TM                               \ # 修改日时
  from TB_PRZ                                   \ # 奖品
 where LTR_ID =?                                \ # 抽奖ID
   and PRZ_ID=?                                 \ # 奖品ID
  with rs use and keep update locks

lotteryfactory.003= \
select LTR_ID                                     \ # 抽奖ID
       ,SPRD_MD5                                  \ # 渠道号
       ,LTR_TTL                                   \ # 抽奖标题
       ,LTR_TYP                                   \ # 抽奖类型
       ,LTR_CON                                   \ # 抽奖内容
       ,END_CON                                   \ # 后记
       ,BGN_DT_TM                                 \ # 开始日时
       ,END_DT_TM                                 \ # 结束日时
       ,AD_PIC_ID                                 \ # 宣传图片ID
       ,BG_PIC_ID                                 \ # 背景图片ID
       ,BTN_PIC_ID                                \ # 按钮图片ID
       ,DEL_FLG                                   \ # 删除标识
       ,ADD_USR                                   \ # 添加者
       ,ADD_DT_TM                                 \ # 添加日时
       ,UPT_USR                                   \ # 修改者
       ,UPT_DT_TM                                 \ # 修改日时
  from TB_LTR                                     \ # 抽奖
 where DEL_FLG= '1'                                 # 删除标识

lotteryfactory.004= \
update TB_PRZ                                     \ # 奖品
   set SRP_PRZ_CNT = ?                            \ # 剩余数量
 where LTR_ID = ?                                 \ # 抽奖ID
   and PRZ_ID = ?                                   # 奖品ID