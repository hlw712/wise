package com.zl.wise.common.validator;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * <p>校验工具</p>
 *
 * @author huanglinwei
 * @date 2023/9/5 20:57
 * @version 1.0.0
 */
public class ValidateUtils {

	private static final String REGEX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	
	public static boolean isMobile(String mobile) {
		return Mobile.check(mobile);
	}

	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	public static boolean isValidLong(String str) {
		try {
			Long.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Getter
	public enum Mobile {
		AE("+971", "^\\+971-[5]{1}\\d{8}$"),
		CN("+86", "^\\+86-[1]{1}\\d{10}$"),
		OTHER("other", "^\\+[1-9]{1}[0-9]{0,5}\\-\\d{4,13}$");

		private String suffix;
		private String pattern;
		Mobile(String suffix, String pattern) {
			this.suffix = suffix;
			this.pattern = pattern;
		}

		public static Boolean check(String content) {
			if (content == null || content.isEmpty()) {
				return null;
			}
			for (Mobile mobile : Mobile.values()) {
				if (content.contains(mobile.getSuffix())) {
					return Pattern.matches(mobile.getPattern(), content);
				}
			}
			return Pattern.matches(OTHER.getPattern(), content);
		}

//        AC--上升岛
//        AD--安道尔
//        AE--阿拉伯联合酋长国
//        AF--阿富汗
//        AG--安提瓜和巴布达
//        AI--安圭拉
//        AL--阿尔巴尼亚
//        AM--亚美尼亚
//        AN--荷兰安的列斯群岛
//        AO--安哥拉
//        AQ--南极洲
//        AR--阿根廷
//        AS--美属萨摩亚群岛
//        AT--奥地利
//        AU--澳大利亚
//        AW--阿卢巴
//        AZ--阿塞拜疆
//        BA--波士尼亚共和国和
//        BB--巴巴多斯
//        BD--孟加拉
//        BE--比利时
//        BF--BurkinaFaso
//        BG--保加利亚
//        BH--巴林
//        BI--布隆迪
//        BJ--贝宁湾
//        BM--百慕达群岛
//        BN--汶莱
//        BO--玻利维亚
//        BR--巴西
//        BS--巴哈马
//        BT--不丹
//        BV--布威岛
//        BW--博茨瓦纳
//        BY--白俄罗斯
//        BZ--伯利兹
//        CA--加拿大
//        CC--椰子树(装以龙骨)岛
//        CD--扎伊尔
//        CF--中非共和国
//        CG--刚果
//        CH--瑞士
//        CI--棚dIvoire
//        CK--科克群岛
//        CL--智利
//        CM--喀麦隆
//        CN--中国
//        CO--哥伦比亚
//        CR--哥斯达黎加
//        CU--古巴
//        CV--维德岛
//        CX--圣诞岛
//        CY--赛普勒斯
//        CZ--捷克
//        DE--德国
//        DJ--吉布地
//        DK--丹麦
//        DM--多米尼加共和国
//        DO--多米尼加共和国
//        DZ--阿尔及利亚
//        EC--厄瓜多尔
//        EE--爱沙尼亚
//        EG--埃及
//        EH--西撒哈拉
//        ER--厄立特里安
//        ES--西班牙
//        ET--埃塞俄比亚
//        EU--欧盟
//        FI--芬兰
//        FJ--斐济
//        FK--福克兰群岛
//        FM--密克罗尼西亚
//        FO--法罗群岛
//        FR--法国
//        GA--加彭
//        GB--英国
//        GD--格林纳达
//        GE--格鲁吉亚州
//        GF--法属圭亚那
//        GG--格恩西岛
//        GH--迦纳
//        GI--直布罗陀
//        GL--格陵兰
//        GM--冈比亚
//        GN--几内亚
//        GP--哥德普洛岛
//        GQ--赤道几内亚
//        GR--希腊
//        GS--南乔治亚州和南方插入岛
//        GT--危地马拉
//        GU--关岛
//        GW--几内亚比绍共和国
//        GY--圭亚那
//        HK--香港
//        HM--听到和麦当劳岛
//        HN--洪都拉斯
//        HR--克罗埃西亚
//        HT--海地
//        HU--匈牙利
//        ID--印尼
//        IE--爱尔兰
//        IL--以色列
//        IM--岛
//        IN--印度
//        IO--英国的印度洋领土
//        IQ--伊拉克
//        IR--伊朗王国
//        IS--冰岛
//        IT--意大利
//        JE--泽西
//        JM--牙买加
//        JO--约旦
//        JP--日本
//        KE--肯尼亚
//        KG--Kyrgystan
//        KH--高棉
//        KI--吉尔巴斯
//        KM--科摩洛
//        KN--圣吉斯和尼维斯
//        KP--朝鲜民主主义人民共和国
//        KR--韩国大韩民国
//        KW--科威特
//        KY--开曼群岛
//        KZ--哈萨克斯坦
//        LA--老挝
//        LB--黎巴嫩
//        LC--圣卢西亚
//        LI--列支敦士登
//        LK--斯里兰卡
//        LR--利比里亚
//        LS--莱索托
//        LT--立陶宛
//        LU--卢森堡
//        LV--拉脱维亚
//        LY--利比亚
//        MA--摩洛哥
//        MC--摩纳哥
//        MD--摩尔多瓦
//        MG--马达加斯加
//        MH--马绍尔群岛
//        MK--马其顿
//        ML--马里
//        MM--缅甸
//        MN--蒙古
//        MO--澳门
//        MP--北马里亚纳群岛
//        MQ--马提尼克岛
//        MR--毛里塔尼亚
//        MS--蒙特色纳
//        MT--马尔他
//        MU--毛里求斯
//        MV--马尔代夫
//        MW--马拉维
//        MX--墨西哥
//        MY--马来西亚
//        MZ--莫桑比克
//        NA--那米比亚
//        NC--新加勒多尼亚
//        NE--尼日尔
//        NF--诺福克岛
//        NG--尼日利亚
//        NI--尼加拉瓜
//        NL--荷兰
//        NO--挪威
//        NP--尼泊尔
//        NR--瑙鲁
//        NU--纽鄂岛
//        NZ--新西兰
//        OM--阿曼
//        PA--巴拿马
//        PE--秘鲁
//        PF--法属玻里尼西亚
//        PG--巴布亚新几内亚
//        PH--菲律宾共和国
//        PK--巴基斯坦
//        PL--波兰
//        PM--圣皮埃尔和密克罗
//        PN--皮特凯恩岛
//        PR--波多黎各
//        PS--巴勒斯坦
//        PT--葡萄牙
//        PW--帛琉
//        PY--巴拉圭
//        QA--卡塔尔
//        RO--罗马尼亚
//        RU--俄罗斯
//        RW--卢旺达
//        SA--沙特阿拉伯
//        SB--索罗门群岛
//        SC--塞锡尔群岛
//        SD--苏丹
//        SE--瑞典
//        SG--新加坡
//        SH--圣海伦娜
//        SI--斯洛文尼亚
//        SJ--冷岸和央麦恩群岛岛
//        SK--斯洛伐克
//        SL--塞拉利昂
//        SM--桑河Marino
//        SN--塞内加尔
//        SO--索马里
//        SR--苏利南
//        ST--圣多美和普林西比
//        SU--苏联
//        SV--萨尔瓦多
//        SY--叙利亚
//        SZ--斯威士兰
//        TD--乍得
//        TG--多哥
//        TH--泰国
//        TJ--塔吉克
//        TK--托克劳
//        TL--东帝汶
//        TM--土库曼
//        TN--北非的共和国
//        TO--汤加
//        TP--东帝汶
//        TR--土耳其
//        TT--千里达托贝哥共和国
//        TV--图瓦卢
//        TW--台湾
//        TZ--坦桑尼亚
//        UA--乌克兰
//        UG--乌干达
//        UK--英国
//        UM--美国辅修在外的岛
//        US--美国
//        UY--乌拉圭
//        UZ--乌兹别克斯坦
//        VA--神圣的见到(罗马教庭)
//        VC--圣文森和格林纳丁斯
//        VE--委内瑞拉
//        VG--维京群岛 UK
//        VI--维京群岛 US
//        VN--越南
//        VU--万那度
//        WF--沃利斯和富图纳群岛
//        WS--美属萨摩亚
//        YE--也门
//        YT--马约特
//        YU--南斯拉夫
//        ZA--南非
//        ZM--赞比亚
//        ZR--扎伊尔
//        ZW--津巴布韦
	}
}
