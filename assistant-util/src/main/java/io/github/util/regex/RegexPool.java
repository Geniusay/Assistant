package io.github.util.regex;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Genius

 **/
@Data
@Component
@ConfigurationProperties(prefix = "assistant.regex.pool")
public class RegexPool {
    /**
     * 用户信息的一些基本表达式
     */
    private  String email = RegexPattern.EMAIL;                            //邮箱
    private  String phone = RegexPattern.PHONE;                            //手机号
    private  String strictPhone = RegexPattern.STRICT_PHONE;               //严格的手机号
    private  String telPhone = RegexPattern.TEL_PHONE;                     //手机号或者座机号
    private  String qq = RegexPattern.QQ;                                  //QQ号从10000开始
    private  String password = RegexPattern.PASSWORD;                      //密码
    private  String idCard = RegexPattern.ID_CARD;                         //身份证
    private  String idCard2 = RegexPattern.ID_CARD2;                       //二代居民身份证
    private  String username = RegexPattern.USERNAME;                      //用户名
    private  String nickname = RegexPattern.NICKNAME;                      //昵称
    private  String passPort = RegexPattern.PASSPORT;                      //护照

    /**
     * 以下为中文、字母、数字相关的正则表达式
     */
    private  String chinese = RegexPattern.CHINESE; //中文
    private  String number = RegexPattern.NUMBER; //数字
    private  String letter = RegexPattern.LETTER; //字母
    private  String chineseLetter = RegexPattern.CHINESE_LETTER; //中文和字母
    private  String chineseLetterNumber = RegexPattern.CHINESE_LETTER_NUMBER; //中文和字母和数字
    private  String chineseName = RegexPattern.CHINESE_NAME; //中文姓名

    /**
     * 以下为日期和时间相关的正则表达式
     */
    private  String date = RegexPattern.DATE; //日期
    private  String time = RegexPattern.TIME; //时间
    private  String dateTime = RegexPattern.DATE_TIME; //日期和时间
    private  String dateTimeMills = RegexPattern.DATE_TIME_MILLS; //日期和时间和毫秒

    /**
     * 以下为文件相关的正则表达式
     */
    private  String fileName = RegexPattern.FILE_NAME; //文件名
    private  String filePath = RegexPattern.FILE_PATH; //文件路径
    private  String linuxPath = RegexPattern.LINUX_PATH; //Linux路径
    private  String windowsPath = RegexPattern.WINDOWS_PATH; //Windows路径

    /**
     * 以下为URL相关的正则表达式
     */
    private  String url = RegexPattern.URL; //URL
    private  String urlPort = RegexPattern.URL_PORT; //带端口号的URL
    private  String ip = RegexPattern.IP; //IP

    /**
     * 以下为其他相关的正则表达式
     */
    private  String color = RegexPattern.COLOR; //颜色
    private  String html = RegexPattern.HTML; //HTML标记
    private  String carID = RegexPattern.CAR_ID; //车牌号(新能源+非新能源)

    private  String province = RegexPattern.PROVINCE; //中国省


}
