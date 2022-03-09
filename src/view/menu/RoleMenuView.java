package view.menu;

import java.sql.SQLException;
import java.util.Scanner;

import exception.NotFoundException;

public class RoleMenuView {
    private static Scanner sc = new Scanner(System.in);

    public static void roleMenu()  {
        while (true){
            RoleMenuView.printMenu();
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1: // 회원
                    UserMenuView.userMenu();
                    break;
                case 2: // 비회원
                    OrderMenuView.orderMenu();
                    break;
                case 3: // 매니저
                    ManagerMenuView.managerMenu();
                    break;
                case 4: // 프로그램종료
                	System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
            }
        }
    }

    private static void printMenu(){
        System.out.println("---- ★JAVA BURGER★에 오신 것을 환영합니다 ^^ ----");
        System.out.println("1. 회원   |   2. 비회원   |   3. 매니저   |   4. 프로그램 종료");

    }
}
