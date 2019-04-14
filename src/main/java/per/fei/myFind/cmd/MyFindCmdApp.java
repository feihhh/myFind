package per.fei.myFind.cmd;

import per.fei.myFind.core.dao.DataSourceFactory;
import per.fei.myFind.core.dao.FileDao;
import per.fei.myFind.core.dao.FileDaoImpl.FileDaoImpl;
import per.fei.myFind.core.index.FileScan;
import per.fei.myFind.core.index.Impl.FileScanImpl;
import per.fei.myFind.core.model.FileType;

import java.util.Scanner;

public class MyFindCmdApp {

    public static void myFind()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String str = scanner.nextLine();

            switch (str)
            {
                case "help":
                {

                    break;
                }
                case "quit":
                {

                    break;
                }
                case "index":
                {

                    break;
                }
            }

        }
    }

}