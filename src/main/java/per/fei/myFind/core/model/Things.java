package per.fei.myFind.core.model;


import lombok.Data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 将电脑中的文件file抽象成一个类Things
 */
@Data
public class Things implements Comparable<Things> {

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

    @Override
    public int compareTo(Things o) {
        return this.getName().length()-o.getName().length();
    }

//    public static void main(String[] args) {
//
//        Things th1 = new Things();
//        Things th2 = new Things();
//        th1.setFileType(FileType.lookUpByName("DOC"));
//        th1.setName("haha");
//        th1.setPath("D://");
//        th1.setDepth(1);
//
//        th2.setFileType(FileType.lookUpByName("DOC"));
//        th2.setName("haha");
//        th2.setPath("D://");
//        th2.setDepth(1);
//
//        Things th3 = new Things();
//        th3.setFileType(FileType.lookUpByName("DOC"));
//        th3.setName("haaaha");
//        th3.setPath("D://");
//        th3.setDepth(1);
//
//        Set<Things> set = new TreeSet<>();
//        set.add(th1);
//        set.add(th2);
//        set.add(th3);
//        Iterator iterator = set.iterator();
//        while (iterator.hasNext())
//        {
//            System.out.println(iterator.next());
//        }
//    }
//

}
