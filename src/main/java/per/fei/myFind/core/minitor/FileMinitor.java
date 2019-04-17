package per.fei.myFind.core.minitor;

import per.fei.myFind.config.HandlePath;

public interface FileMinitor {

    /**
     * 开始监听
     */
    void start ();

    /**
     * 要监听的目录
     * @param handlePath
     */
    void minitor(HandlePath handlePath);

    /**
     * 停止监听
     */
    void stop();

}
