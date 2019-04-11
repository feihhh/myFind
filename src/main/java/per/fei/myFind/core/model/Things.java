package per.fei.myFind.core.model;


import lombok.Data;

/**
 * 将电脑中的文件file抽象成一个类Things
 */
@Data
public class Things {

    /**
     * 文件名字
     */
    private String name;
    /**
     * 文件类型
     */
    private FileType fileType;
    /**
     * 文件深度
     */
    private Integer depth;
    /**
     * 文件路径
     */
    private String path;

}
