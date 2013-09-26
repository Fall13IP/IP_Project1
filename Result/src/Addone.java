import java.util.Scanner;


public class Addone {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int p=0;
		for(int i=0;i<50;i++)
		{
			int x = scanner.nextInt();
			p+=x;
			System.out.println("Time: "+i+"is :"+p);
			
		}
	}

}
