package Assignments;
import java.util.*;

public class PigGame {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number_of_turns = 5;
		int count = 0;
		int i =1;
		while(i<=number_of_turns) {
			System.out.println("TURN: "+i);
			System.out.println("Roll or Hold?(r/h)");
			String decision = sc.nextLine();
			if (decision.equalsIgnoreCase("h")) {
				i++;
			} else {
				if(decision.equalsIgnoreCase("r")) {
					int random_number = (int) (Math.random()* 6 +1 );
					System.out.println("DIE: "+random_number);
					if(random_number==1) {
						System.out.println("Turn Over. No score.");
						count =0;
						i++;
					} else {
						count += random_number;
						if(count > 20) {
							System.out.println("Total Score: "+count);
							System.out.println("You finished in " + i +" turns!");
							break;
						}
					}
				}
			}
		}
		System.out.println("Game over");
	}
}
