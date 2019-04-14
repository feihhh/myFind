package per.fei.myFind.config;

import lombok.Getter;
import lombok.ToString;
import per.fei.myFind.core.model.Condition;

@ToString
@Getter
public class DefaultConfig {

    /**
     * 查找的结果集中最大的返回个数为30
     */
    private Integer maxLimit = 30;

    /**
     * 处理路径 为默认处理路径
     */
    private HandlePath handlePath = HandlePath.getDefaultPath();

    /**
     * 查找出来的结果按深度排序 默认为降序
     * false 表示降序
     */
    private Boolean resultDefaultSortDesc = false;

    /**
     * 是否默认建立索引 默认不建立索引
     */
    private Boolean ifBuildIndex = false;

    private static volatile DefaultConfig config ;

    private DefaultConfig() {
    }

    public static DefaultConfig getConfig ()
    {
        if (config == null)
        {
            synchronized (DefaultConfig.class)
            {
                if (config == null)
                {
                    config = new DefaultConfig();
                }
            }
        }
        return config;
    }

//    public static void main(String[] args)
//    {
//        System.out.println(DefaultConfig.getConfig());
//    }
}
