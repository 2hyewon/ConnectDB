import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		DBConnection connectTest = new DBConnection();
		connectTest.deleteTable();
		connectTest.createTable();
		
		String prom = "> ";
		
		boolean isLoop = true;
		while(isLoop) {
			System.out.println("\n\n Select one : 1.input 2.quit");
			System.out.print(prom);
			Scanner scanner = new Scanner(System.in);
			int selected = scanner.nextInt();
			
			switch(selected) {
			case 1:
				System.out.println("Input date");
				int date = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Input str");
				String str = scanner.nextLine();
				int num = 1;
				int nextNum = connectTest.isExistThenGetNum(date);
				
				if(nextNum != 0) {
					connectTest.insertPlans(date, nextNum, str);
				}
				else {
					connectTest.insertPlans(date, num, str);
				}
				break;
			case 2:
				isLoop = false;
				break;
			}
	}

		connectTest.selectPlans();
		connectTest.shutdown();
	}
}
