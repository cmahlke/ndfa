package Project;

import java.io.*;

public class Interface {
	// input from stdin
	private static BufferedReader input = new BufferedReader(
			new InputStreamReader(System.in));

	// error method
	public static void error(String message, String re) throws Rep_Exception {
		throw new Rep_Exception(message + re);
	}

	public static void error(String message) throws Rep_Exception {
		throw new Rep_Exception(message);
	}

	public static void main(String[] args) throws IOException {
		// Does the user want to enter an new string and test?
		String repeatExpres = new String("y");

		String repeatTest = new String("y");

		// the expression
		String regExpres = new String();

		// "test string" against expression
		String testExpres = new String();

		// initialize NDFA used to test string
		NDFA graph = null;

		// instance of parser
		Parser grep = new Parser();

		// Whether the test string matches the expression
		boolean answer;

		// repeat test until the user indicates to stop
		while (repeatExpres.charAt(0) == 'y') {
			repeatTest = "y";
			System.out.println("Please enter a regular expression:");
			// get the RE
			regExpres = input.readLine();
			// set Parser
			grep.set_parser_target(regExpres);
			// eye candy for user
			System.out.println();
			System.out.println("The RE is: " + regExpres);
			System.out.println();

			// parse the expression
			try {
				graph = grep.RE();
			} catch (Rep_Exception e) {
				repeatTest = ("n"); // If there is an exception do not ask for
									// test strings
			}

			// Get test strings and test them against expression
			while (repeatTest.charAt(0) == 'y') {
				System.out.print("Please enter a string ");
				System.out.println("to test against that RE:");
				try {
					testExpres = input.readLine();
					answer = graph.run(testExpres);
					System.out.println();
					if (answer) {
						System.out.println("The string \"" + testExpres
								+ "\" matches the RE.");
					} else {
						System.out.println("The string \"" + testExpres
								+ "\" does not match the RE.");
					}
					System.out.println();
				} catch (Rep_Exception e) {
				}
				System.out.print("Do you want to test ");
				System.out.println("another string? (y/[n])");
				repeatTest = input.readLine();
				System.out.println();
			}
			System.out.print("Do you want to enter a different ");
			System.out.println("regular expression? (y/[n])");
			repeatExpres = input.readLine();
			System.out.println();
		}
		System.out.println("Thank you.");
		System.out.println("");
		return;
	}
}
