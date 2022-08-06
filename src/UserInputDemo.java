import java.util.Scanner;

public class UserInputDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.println("----------------------------------------------------- \nhello");
        int a = sc.nextInt();
        if (a < 3) {
            System.out.println("Number can not smaller than 3, You enter: " + a + ", Please rerun function again to correct it");
            return;
        }
        System.out.print("Enter second number- ");
        int b = sc.nextInt();
        System.out.print("Enter third number- ");
        int c = sc.nextInt();
        int d = a + b + c;
        System.out.println("Total= " + d);

        // If the scanner type is not same, Has to redefine a Scanner
        Scanner sc1 = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter a string: ");
        String str = sc1.nextLine();              //reads string
        System.out.print("You have entered: " + str);
    }
}
