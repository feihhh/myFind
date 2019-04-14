package per.fei.myFind.config;

import lombok.ToString;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@ToString
public class HandlePath {

    /**
     * 要包含的目录
     */
    private Set<String> includePath = new HashSet<>();
    /**
     * 要排除的目录
     */
    private Set<String> excludePath = new HashSet<>();

    public HandlePath() {
    }

    private void addIncludePath(String path)
    {
        this.includePath.add(path);
    }

    private void addExcludePath(String path)
    {
        this.excludePath.add(path);
    }

    public static HandlePath getDefaultPath()
    {
        HandlePath handlePath = new HandlePath();
        //获取windous下的磁盘
        Iterable<Path> path = FileSystems.getDefault().getRootDirectories();
//        将windows下的几个磁盘存入includePath中，就是要包含的目录
        for (Path path1 : path)
        {
            handlePath.addIncludePath(path1.toString());
        }

//        判断系统,默认以为除了windows就是Linux系统
        String systemPath = System.getProperty("os.name");
        if (systemPath.startsWith("Windows"))
        {
            //windows系统下要排除的目录
            handlePath.excludePath.add("C:\\Program Files");
            handlePath.excludePath.add("C:\\Program Files (x86)");
            handlePath.excludePath.add("C:\\Windows");
            handlePath.excludePath.add("C:\\ProgramData");

        }
        return handlePath;
    }

//    public static void main(String[] args) {
//        HandlePath handlePath = new HandlePath();
//        System.out.println(handlePath.getDefaultPath());
//    }
}
