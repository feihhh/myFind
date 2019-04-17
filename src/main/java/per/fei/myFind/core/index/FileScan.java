package per.fei.myFind.core.index;

import per.fei.myFind.core.index.Impl.FileScanImpl;

import java.io.File;

public interface FileScan {

    /**
     * 将文件存入数据库
     * @param path
     */
    void index(String path);

//    测试代码：
//    static void main(String[] args) {
//        FileScan fileScan = new FileScanImpl();
//        fileScan.index("E:\\");
//    }
}
