package per.fei.myFind.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
@Getter
@Setter
public class Condition {
    //按名字查找
    private String name;
    //按文件类型查找
    private FileType fileType;
    //查找结果返回的最大数量
    private Integer maxLimit;
}
