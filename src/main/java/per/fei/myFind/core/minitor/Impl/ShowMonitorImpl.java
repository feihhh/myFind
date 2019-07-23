package per.fei.myFind.core.minitor.Impl;

import per.fei.myFind.core.minitor.ShowMonitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShowMonitorImpl implements ShowMonitor {
    /**
     * 查看文件近期变化情况
     */
    @Override
    public List<String> show() {
        List<String> list = new ArrayList<>();
        String path = System.getProperty("user.dir")+File.separator+"monitor.txt";
        // 获取file对象
        File file = new File(path);
//        通过子类实例化对象
        if (!file.exists())
        {
            return null;
        }
        InputStream in = null;
        BufferedReader buffer = null;
        try {
            in = new FileInputStream(file);
            buffer = new BufferedReader(new InputStreamReader(in));
            String read = null;
            while ((read=buffer.readLine()) != null)
            {
                list.add(read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (buffer != null)
            {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
