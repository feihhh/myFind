package per.fei.myFind.core.common.date;

import java.text.SimpleDateFormat;

/**
 * 输出当前系统时间
 */
public class MyDate {
    public static String getTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new java.util.Date());
    }

//    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println(date.returnDate());
//    }
}
