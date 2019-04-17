package per.fei.myFind.cmd;

import per.fei.myFind.core.AllManager;
import java.util.Scanner;

public class MyFindCmdApp {

    public static void main(String[] args)
    {
        System.out.println("欢迎使用everything...");
        Scanner scanner = new Scanner(System.in);
        while (true)
        {

            System.out.print(">>");
            String str = scanner.nextLine();

            switch (str)
            {
                case "help":
                {
                    AllManager.getManager().help();
                    break;
                }
                case "quit":
                {
                    System.out.println("感谢使用...");
                    AllManager.getManager().quit();
                    break;
                }
                case "index":
                {
                    AllManager.getManager().index();
                    break;
                }
                default:
                {
                    if (str.startsWith("search"))
                    {
                        String[] cons = str.split(" ");
                        if (cons.length > 1)
                        {
                            AllManager.getManager().search(cons);
                        }
                        else
                        {
                            AllManager.getManager().help();
                        }
                    }
                    else
                    {
                        AllManager.getManager().help();
                    }
                }
            }
        }
    }
}