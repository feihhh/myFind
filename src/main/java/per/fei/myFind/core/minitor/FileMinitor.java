package per.fei.myFind.core.minitor;

import per.fei.myFind.config.HandlePath;

public interface FileMinitor {

    /**
     * 开始监听
     */
    void start ();

    /**
     * 开始监听
     * @param handlePath 要监听的目录
     */
    void minitor(HandlePath handlePath);

    /**
     * 停止监听
     */
    void stop();

}
