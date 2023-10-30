package io.github.util.regex;

/**
 *    Genius

 **/

/**
 * 正则表达式静态类
 */
public class RegexPattern {

    /**
     * 用户信息的一些基本表达式
     */
    public final static String EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";  //邮箱
    public final static String PHONE = "^1[3|4|5|7|8][0-9]\\d{8}$";                            //手机号

    public final static String STRICT_PHONE = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|" +
           "(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$";                //严格手机号(根据工信部规定)

    public final static String TEL_PHONE = "^(?:(?:\\d{3}-)?\\d{8}|^(?:\\d{4}-)" +
                                                    "?\\d{7,8})(?:-\\d+)?$";                   //座机(CHINA)
    public final static String QQ = "^[1-9][0-9]{4,10}$";                                      //QQ号从10000开始
    public final static String PASSWORD = "^[a-zA-Z0-9_-]{6,16}$";                             //密码

    public final static String ID_CARD = "^[1-9]\\d{7}(?:0\\d|10|11|12)" +
            "(?:0[1-9]|[1-2][\\d]|30|31)\\d{3}$";                                              //1代身份证
    public final static String ID_CARD2 = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";       //2代身份证

    public final static String USERNAME = "^[a-zA-Z0-9_-]{4,16}$";                              //用户名

    public final static String NICKNAME = "^[a-zA-Z0-9_-]{4,16}$";                              //昵称

    //护照
    public final static String PASSPORT = "(^[EeKkGgDdSsPpHh]\\d{8}$)|(^(([Ee][a-fA-F])" +
            "|([DdSsPp][Ee])|([Kk][Jj])|([Mm][Aa])|(1[45]))\\d{7}$)";                           //护照

    /**
     * 以下为中文、字母、数字相关的正则表达式
     */
    public final static String CHINESE = "[\\u4e00-\\u9fa5]"; //中文
    public final static String NUMBER = "^[0-9]*$"; //数字
    public final static String LETTER = "^[A-Za-z]+$"; //字母
    public final static String CHINESE_LETTER = "^[A-Za-z\\u4e00-\\u9fa5]+$"; //中文和字母
    public final static String CHINESE_LETTER_NUMBER = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$"; //中文和字母和数字
    public final static String CHINESE_NAME = "^[\\u4e00-\\u9fa5]{2,4}$"; //中文姓名

    /**
     * 以下为日期和时间相关的正则表达式
     */
    public final static String DATE = "^(\\d{4})-(\\d{2})-(\\d{2})$"; //日期
    public final static String TIME = "^(\\d{2}):(\\d{2}):(\\d{2})$"; //时间
    public final static String DATE_TIME = "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$"; //日期和时间
    public final static String DATE_TIME_MILLS = "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})$"; //日期和时间和毫秒

    /**
     * 以下为文件相关的正则表达式
     */
    public final static String FILE_NAME = "^[^\\\\/:*?\"<>|]+$"; //文件名
    public final static String FILE_PATH = "^[^\\*\\?\"<>|]+$"; //文件路径
    public final static String LINUX_PATH = "^(/[^/]+)+$"; //Linux路径
    public final static String WINDOWS_PATH = "^([a-zA-Z]:\\\\[^\\\\/:*?\"<>|]+)+$"; //Windows路径

    /**
     * 以下为URL相关的正则表达式
     */
    public final static String URL = "^(https|http)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$"; //URL
    public final static String URL_PORT = "^(https|http)://([\\w-]+\\.)+[\\w-]+(:[\\d]{1,5})?(/[\\w- ./?%&=]*)?$"; //带端口号的URL
    public final static String IP = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))"; //IP

    /**
     * 以下为其他相关的正则表达式
     */
    public final static String COLOR = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$"; //颜色
    public final static String HTML = "<(\\S*?)[^>]*>.*?|<.*? />"; //HTML标记
    public final static String CAR_ID = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-HJ-NP-Z][A-HJ-NP-Z0-9]{4,5}[A-HJ-NP-Z0-9挂学警港澳]$"; //车牌号(新能源+非新能源)

    public final static String PROVINCE = "^(北京市|天津市|河北省|山西省|内蒙古自治区|辽宁省|吉林省|黑龙江省|上海市|江苏省|浙江省" +
            "|安徽省|福建省|江西省|山东省|河南省|湖北省|湖南省|广东省|广西壮族自治区|海南省|重庆市|四川省" +
            "|贵州省|云南省|西藏自治区|陕西省|甘肃省|青海省|宁夏回族自治区|新疆维吾尔自治区|台湾省|香港特别行政区|澳门特别行政区)$"; //中国省
}
