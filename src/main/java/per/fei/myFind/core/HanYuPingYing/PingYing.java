package per.fei.myFind.core.HanYuPingYing;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PingYing {

    /**
     * 汉语拼音输出格式
     */
    private HanyuPinyinOutputFormat format = null;

    public PingYing() {
        this.format = new HanyuPinyinOutputFormat();
        //设置拼音格式为不带声调的格式
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 单个字符转为拼音
     * @param c 汉语字符
     * @return 如果字符c是汉字，返回汉字的汉语拼音（如果是多音字，返回第一个拼音）；如果不是汉字，返回null
     */
    private String wordToPinYin(char c)
    {
        String[] arr = null;
        try {
            arr = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
//        如果不是汉字，转的结果是空
        return arr==null||arr.length==0?null:arr[0];
    }

    public String wordsToPinYin(String words)
    {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<words.length(); i++)
        {
            char ch = words.charAt(i);
            String pinYin = this.wordToPinYin(ch);
            sb.append(pinYin==null?ch:pinYin);
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        PingYing p = new PingYing();
//        System.out.println(p.wordsToPinYin("hfjks合法卡萨丁你付款很费劲&……*&%……￥……%"));
//    }
}
