package Project;

// Parser implementing the grammar:
//
// RE ::= RT { | RT }*
// RT ::= RF { RF }*
// RF ::= <alpha> | # | ? | RF* | RF+ | ( RE )
public class Parser {
	// constant characters
	public static final char EMPTY = '#';
	public static final char WILD = '?';
	public static final char LEFT_PAREN = '(';
	public static final char RIGHT_PAREN = ')';
	public static final char OR = '|';
	public static final char STAR = '*';
	public static final char PLUS = '+';

	// string to parse
	private String expression;

	// position of the string
	private int pointer;

	public Parser() {
	}

	// gives the Parser something to parse
	public void set_parser_target(String s) {
		expression = s;
		pointer = 0;
	}

	// RE ::= RT { | RT }*
	public NDFA RE() throws Rep_Exception {
		// take the first term
		NDFA graph = RT();

		// Continue to take terms separated by OR, |
		while (character() == OR) {
			next();
			graph = NDFA.alternate(graph, RT());
		}
		return graph;
	}

	// RT ::= RF RF*
	private NDFA RT() throws Rep_Exception {
		// Take first factor
		NDFA graph = RF();

		// Continue to take factors while they are valid factors
		while (validFactorBegin()) {
			graph = NDFA.concatenate(graph, RF());
		}
		return graph;
	}

	// RF ::= <alpha> | # | ? | RF* | RF+ | ( RE )
	private NDFA RF() throws Rep_Exception {
		NDFA factor = null; // factor

		// If a left parenthesis, call RE on the expression inside paren
		if (character() == LEFT_PAREN) {
			next();
			factor = RE();

			// There must be a right parenthesis as well
			if (character() != RIGHT_PAREN) {
				System.out.print("Unmatched open paren in RE: ");
				System.out.println(expression);
				System.out.println();
				Interface.error("Expected a '" + RIGHT_PAREN + "', but found "
						+ character() + "in RE: ", expression);
			}
			next();
		}
		// The factor could be a lexeme
		else if (Character.isLowerCase(character())) {
			factor = NDFA.single_character(character());
			next();
		}
		// The factor could be ? or #
		else if ((character() == WILD) || (character() == EMPTY)) {
			factor = NDFA.single_character(character());
			next();
		}
		// Otherwise, the factor is not valid and indicate
		// to the user the error
		else {
			System.out
					.println("Interface halting on RE for the following reason:");
			System.out.print("Unexpected character '" + character());
			System.out.println("' in RE: " + expression);
			System.out.println();
			Interface.error("Unexpected character '" + character()
					+ "' in RE: ", expression);
		}

		// Following the factor my be STARs and/or PLUSes
		while ((character() == STAR) || (character() == PLUS)) {
			if (character() == STAR) {
				factor = NDFA.star(factor);
				next();
			}
			if (character() == PLUS) {
				factor = NDFA.plus(factor);
				next();
			}
		}
		return factor;
	}

	// Returns current character being pointed at
	private char character() {
		if (!go()) {
			return ' ';
		} else {
			return expression.charAt(pointer);
		}
	}

	// advance pointer
	private void next() {
		pointer++;
	}

	// check for more chars in the string
	private boolean go() {
		return (pointer < expression.length());
	}

	// Checks that the character is a valid beginning of a factor
	private boolean validFactorBegin() {
		if (Character.isLowerCase(character()) || (character() == EMPTY)
				|| (character() == WILD) || (character() == LEFT_PAREN)) {
			return true;
		} else {
			return false;
		}
	}
}
