package com.me.core.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xunice on 08/03/2017.
 */

public class StringUtils extends BaseUtils {

    public StringUtils() {
        super();
    }

    /**
     * The pyvalue.
     */
    private static int[] pyvalue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032,
            -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728,
            -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261,
            -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961,
            -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490,
            -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988,
            -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692,
            -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733,
            -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419,
            -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959,
            -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631,
            -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180,
            -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941,
            -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857,
            -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353,
            -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094,
            -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831,
            -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329,
            -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860,
            -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300,
            -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358,
            -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014,
            -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309,
            -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};

    /**
     * The pystr.
     */
    public static String[] pystr = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian",
            "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che",
            "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan",
            "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du",
            "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang",
            "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang",
            "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian",
            "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken",
            "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng",
            "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai",
            "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai",
            "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan",
            "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
            "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re",
            "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha",
            "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun",
            "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao",
            "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi",
            "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi",
            "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha",
            "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui",
            "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};


    public static int getChsAscii(String chs) {
        int asc = 0;
        try {
            byte[] bytes = chs.getBytes("gb2312");
            /*if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
                throw new RuntimeException("illegal resource string");
            }*/
            if (bytes.length == 1) {
                asc = bytes[0];
            }
            if (bytes.length == 2) {
                int hightByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                asc = (256 * hightByte + lowByte) - 256 * 256;
            }
        } catch (Exception e) {
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
        }
        return asc;
    }

    public static String convert(String str) {
        String result = null;
        int ascii = getChsAscii(str);
        if (ascii > 0 && ascii < 160) {
            result = String.valueOf((char) ascii);
        } else {
            for (int i = (pyvalue.length - 1); i >= 0; i--) {
                if (pyvalue[i] <= ascii) {
                    result = pystr[i];
                    break;
                }
            }
        }
        return result;
    }

    public String getSelling(String chs) {
        String key, value;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < chs.length(); i++) {
            key = chs.substring(i, i + 1);
            if (key.getBytes().length >= 2) {
                value = convert(key);
                if (value == null) {
                    value = "unknown";
                }
            } else {
                value = key;
            }
            buffer.append(value);
        }
        return buffer.toString();
    }

    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    valueLength += 2;
                } else {
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                isChinese = temp.matches(chinese);
            }
        }
        return isChinese;
    }

    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                String temp = str.substring(i, i + 1);
                isChinese = temp.matches(chinese);
            }
        }
        return isChinese;
    }

    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int convert2Int(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value.toString()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static String decimalFormat(String s, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(s);
    }


    /**
     * 特殊字符判断
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[@#$%€￥·‖&*]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static class SpecialCharFilter implements InputFilter {

        //https://github.com/delight-im/Emoji/blob/master/Android/src/AndroidEmoji.java
        HashSet<String> emojiFilter = new HashSet<>();
        HashSet<String> specialCharfilter = new HashSet<>();

        public SpecialCharFilter() {
            addEmojiFilter(emojiFilter);
//            specialCharfilter.add("#");
//            specialCharfilter.add("%");
//            specialCharfilter.add("$");
//            specialCharfilter.add("￥");
//            specialCharfilter.add("£");
//            specialCharfilter.add("&");
//            specialCharfilter.add("@");
//            specialCharfilter.add("*");
//            specialCharfilter.add("‖");
//            specialCharfilter.add("·");
//            specialCharfilter.add("€");
//            specialCharfilter.add("￡");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            int codePoint;
            boolean isSurrogatePair;
            if (source.length() == 1) {
                if (emojiFilter.contains(source.toString())) {
                    return "";
                }
                if (specialCharfilter.contains(source.toString())) {
                    return "";
                }
                if (dstart == 0) { //首字母不能是空格，也不能是回车换行
                    if (source.toString().equals(" ")|| source.toString().equals("\n") || source.toString().equals("\t")) {
                        return "";
                    }
                }
            } else if (source.length() > 1) {
                char[] chars = source.toString().toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    // if the char is a leading part of a surrogate pair (Unicode)

                    if (Character.isHighSurrogate(chars[i])) {
                        // just ignore it and wait for the trailing part
                        continue;
                    }
                    // if the char is a trailing part of a surrogate pair (Unicode)
                    else if (Character.isLowSurrogate(chars[i])) {
                        // if the char and its predecessor are indeed a valid surrogate pair
                        if (i > 0 && Character.isSurrogatePair(chars[i - 1], chars[i])) {
                            // get the Unicode code point for the surrogate pair
                            codePoint = Character.toCodePoint(chars[i - 1], chars[i]);
                            // remember that we have a surrogate pair here (which counts as two characters)
                            isSurrogatePair = true;
                        }
                        // if the char and its predecessor are not actually a valid surrogate pair
                        else {
                            // just ignore it
                            continue;
                        }
                    }
                    // if the char is not part of a surrogate pair, i.e. a simple char from the BMP
                    else {
                        // get the Unicode code point by simply casting the char to int
                        codePoint = (int) chars[i];
                        // remember that we have a BMP symbol here (which counts as a single character)
                        isSurrogatePair = false;
                    }

                    // if the detected code point is a known emoji
                    if (isSurrogatePair) {
                        // change the font for this symbol (two characters if surrogate pair) to the emoji font
//                        ssb.setSpan(new CustomTypefaceSpan(FontProvider.getInstance(context).getFontEmoji()), isSurrogatePair ? i-1 : i, i+1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        if (emojiFilter.contains(new String(new int[]{codePoint}, 0, 1))) {
                            return "";
                        }
                    } else {
                        if (specialCharfilter.contains(chars[i] + "")) {
                            return "";
                        }
                    }
                }

            }
            return source;
        }
    }

    public static class MaxLengthShowToastFilter implements InputFilter {

        Context context;
        private final int mMax;
        private String title;

        public MaxLengthShowToastFilter(Context context, int mMax,String title) {
            this.context = context;
            this.mMax = mMax;
            this.title=title;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                ToastBarUtils.showToast(context, title+"最多输入" + mMax + "字");
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        ToastBarUtils.showToast(context, title+"最多输入" + mMax + "字");
                        return "";
                    }
                }
                ToastBarUtils.showToast(context, title+"最多输入" + mMax + "字");
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMax;
        }
    }

    /**
     * 添加表情
     */
    public static void addEmojiFilter(Set<String> filterSet) {
        // See http://apps.timwhitlock.info/emoji/tables/unicode
        // https://www.coder4.com/archives/4729
        // 1F601 - 1F64F
        addUnicodeRangeToSet(filterSet, 0x1F601, 0X1F64F);

        // 2702 - 27B0
        addUnicodeRangeToSet(filterSet, 0x2702, 0X27B0);

        // 1F680 - 1F6C0
        addUnicodeRangeToSet(filterSet, 0X1F680, 0X1F6C0);

        // 24C2 - 1F251
        addUnicodeRangeToSet(filterSet, 0X1F170, 0X1F251);

        // 1F600 - 1F636
        addUnicodeRangeToSet(filterSet, 0X1F600, 0X1F636);

        // 1F681 - 1F6C5
        addUnicodeRangeToSet(filterSet, 0X1F681, 0X1F6C5);

        // 1F30D - 1F567
        addUnicodeRangeToSet(filterSet, 0X1F30D, 0X1F567);

        filterSet.add(new String(new int[]{129315}, 0, 1));
    }

    private static void addUnicodeRangeToSet(Set<String> set, int start, int end) {
        if (set == null) {
            return;
        }
        if (start > end) {
            return;
        }

        for (int i = start; i <= end; i++) {
            set.add(new String(new int[]{
                    i
            }, 0, 1));
        }
    }

    /**
     * 价格格式改为0.00,如果是0，直接显示0
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        if(price <= 0) return "0.00";
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(price)+"";
    }

    /**
     * 答疑用户名加密
     * @param str
     * @return
     */
    public static String changeNameShow(String str) {
        if (StringUtils.isEmpty(str))return "";
        String limit = "*************";
        if (isNumeric(str)) {
            if (str.length() == 11) {
                str = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            } else {
                if (str.length() > 12) {
                    str = str.substring(0, 2) + limit;
                } else if (str.length() > 3) {
                    str = str.substring(0, 2) + limit.substring(0, str.length() - 2);
                }
            }
        } else {
            if (str.length() > 12) {
                if (checkFirst(str)){
                    str = str.substring(0, 2) + limit;
                }else{
                    str = str.substring(0, 1) + limit+"*";
                }
            }else if (str.length()>2){
                if (checkFirst(str)){
                    if (str.length()<2)return str;
                    str = str.substring(0, 2) + limit.substring(0, str.length() - 2);
                }else{
                    str = str.substring(0, 1) + limit.substring(0, str.length() - 1);
                }
            }
        }
        return str;
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    private static boolean checkFirst(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }
}
