package per.fei.myFind.core.index;

import per.fei.myFind.core.index.Impl.FileScanImpl;

import java.io.File;

public interface FileScan {

    /**
     * 将文件存入数据库
     * @param file
     */
    void index(File file);

//    测试代码：
//    static void main(String[] args) {
//        File file = new File("D:\\WorkPlace");
//        FileScan fileScan = new FileScanImpl();
//        fileScan.index(file);
//    }
}
