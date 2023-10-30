package io.github.util.convert;

/**
 * @author Genius
 * @date 2023/10/28 02:58
 **/
public class ZhConvertUtil {

    /**
     * 中文数字单位转Int
     * @param zhNumber 例如（3以）
     * @return
     */
    public static int zhNumericUnitsToInt(String zhNumber){
        String[] parts = zhNumber.split("(?<=\\d\\.\\d)(?=\\D)");
        if(parts.length==2){
            float num = Float.parseFloat(parts[0]);
            switch (parts[1]){
                case "亿":
                    return (int)num*100000;
                case "万":
                    return (int)num*10000;
                case "千":
                    return (int)num*1000;
                case "百":
                    return (int)num*100;
                case "个":
                    return (int)num;
            }
        }
        return Integer.parseInt(zhNumber);
    }
}
