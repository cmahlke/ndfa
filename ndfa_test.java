package Project;

public class ndfa_test {

	public static void main(String[] args) throws Rep_Exception {
		ndfa_test nt = new ndfa_test();

		nt.test0();
		nt.test1();
		nt.test2();
		nt.test3();
		nt.test4();
		nt.test5();
		nt.test6();
		nt.test7();
		nt.test8();
		nt.test9();
		nt.test10();
		return;
	}

	// BEGIN TEST 0
	public void test0() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.println("State table for regular expression `a'");
		System.out.println("");

		NDFA n = NDFA.single_character('a');

		// print the table
		state_table.print_state_table();

		// test
		System.out.println("");
		System.out.println("Test \"a\"");
		System.out.println("");
		if (n.run("a")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"aa\"");
		System.out.println("");
		if (n.run("aa")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"xbc\"");
		System.out.println("");
		if (n.run("xbc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"b\"");
		System.out.println("");
		if (n.run("b")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 1
	public void test1() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `abc'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA n = NDFA.concatenate(a, NDFA.concatenate(b, c));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"abc\"");
		System.out.println("");
		if (n.run("abc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abx\"");
		System.out.println("");
		if (n.run("abx")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"xbc\"");
		System.out.println("");
		if (n.run("xbc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"ab\"");
		System.out.println("");
		if (n.run("ab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

	}

	// BEGIN TEST 2
	public void test2() throws Rep_Exception {
		state_table.clear_state_table();
		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `a|b|c'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA n = NDFA.alternate(a, NDFA.alternate(b, c));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"a\"");
		System.out.println("");
		if (n.run("a")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"b\"");
		System.out.println("");
		if (n.run("b")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"c\"");
		System.out.println("");
		if (n.run("c")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"x\"");
		System.out.println("");
		if (n.run("x")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"ab\"");
		System.out.println("");
		if (n.run("ab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 3
	public void test3() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `ab*c'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA n = NDFA.concatenate(a, NDFA.concatenate(NDFA.star(b), c));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"ac\"");
		System.out.println("");
		if (n.run("ac")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abc\"");
		System.out.println("");
		if (n.run("abc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abbc\"");
		System.out.println("");
		if (n.run("abbc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"axc\"");
		System.out.println("");
		if (n.run("axc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEIGN TEST 4
	public void test4() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `ab+c'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA n = NDFA.concatenate(a, NDFA.concatenate(NDFA.plus(b), c));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"ac\"");
		System.out.println("");
		if (n.run("ac")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abc\"");
		System.out.println("");
		if (n.run("abc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abbc\"");
		System.out.println("");
		if (n.run("abbc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"axc\"");
		System.out.println("");
		if (n.run("axc")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 5
	public void test5() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `ab(c|#)d'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA d = NDFA.single_character('d');
		NDFA e = NDFA.single_character('#');
		NDFA n = NDFA.concatenate(a,
				NDFA.concatenate(b, NDFA.concatenate(NDFA.alternate(c, e), d)));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"abcd\"");
		System.out.println("");
		if (n.run("abcd")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abd\"");
		System.out.println("");
		if (n.run("abd")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abxd\"");
		System.out.println("");
		if (n.run("abxd")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abcx\"");
		System.out.println("");
		if (n.run("abcx")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// TEST 6
	public void test6() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `a?b'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA q = NDFA.single_character('?');
		NDFA b = NDFA.single_character('b');
		NDFA n = NDFA.concatenate(a, NDFA.concatenate(q, b));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"abb\"");
		System.out.println("");
		if (n.run("abb")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"ab\"");
		System.out.println("");
		if (n.run("ab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"xbb\"");
		System.out.println("");
		if (n.run("xbb")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"axbb\"");
		System.out.println("");
		if (n.run("axbb")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 7
	public void test7() throws Rep_Exception {
		state_table.clear_state_table();
		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `a(a|b)*b'");
		System.out.println("");

		NDFA a1 = NDFA.single_character('a');
		NDFA a2 = NDFA.single_character('a');
		NDFA b1 = NDFA.single_character('b');
		NDFA b2 = NDFA.single_character('b');
		NDFA n = NDFA.concatenate(a1,
				NDFA.concatenate(NDFA.star(NDFA.alternate(a2, b2)), b1));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"ab\"");
		System.out.println("");
		if (n.run("ab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abbbcsde\"");
		System.out.println("");
		if (n.run("abbbcsde")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abbbbbb\"");
		System.out.println("");
		if (n.run("abbbbbb")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abababa\"");
		System.out.println("");
		if (n.run("abababa")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 8
	public void test8() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `a*b*a*'");
		System.out.println("");

		NDFA a1 = NDFA.single_character('a');
		NDFA a2 = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA n = NDFA.concatenate(NDFA.star(a1),
				NDFA.concatenate(NDFA.star(b), NDFA.star(a2)));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"a\"");
		System.out.println("");
		if (n.run("a")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"b\"");
		System.out.println("");
		if (n.run("b")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"bba\"");
		System.out.println("");
		if (n.run("bba")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"aabba\"");
		System.out.println("");
		if (n.run("aabba")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abab\"");
		System.out.println("");
		if (n.run("abab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"aaa\"");
		System.out.println("");
		if (n.run("aaa")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEGIN TEST 9
	public void test9() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println(" `a*b+a*'");
		System.out.println("");

		NDFA a1 = NDFA.single_character('a');
		NDFA a2 = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA n = NDFA.concatenate(NDFA.star(a1),
				NDFA.concatenate(NDFA.plus(b), NDFA.star(a2)));

		// Print table
		state_table.print_state_table();

		// Test
		System.out.println("");
		System.out.println("Test \"a\"");
		System.out.println("");
		if (n.run("a")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"b\"");
		System.out.println("");
		if (n.run("b")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"bba\"");
		System.out.println("");
		if (n.run("bba")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"aabba\"");
		System.out.println("");
		if (n.run("aabba")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"abab\"");
		System.out.println("");
		if (n.run("abab")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"aaa\"");
		System.out.println("");
		if (n.run("aaa")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"\"");
		System.out.println("");
		if (n.run("")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}

	// BEIGN TEST 10
	public void test10() throws Rep_Exception {
		state_table.clear_state_table();

		System.out.println("");
		System.out.println("");
		System.out.print("State table for regular expression");
		System.out.println("\n\t`(a | bc | d)* (? | #)'");
		System.out.println("");

		NDFA a = NDFA.single_character('a');
		NDFA b = NDFA.single_character('b');
		NDFA c = NDFA.single_character('c');
		NDFA d = NDFA.single_character('d');
		NDFA qm = NDFA.single_character('?');
		NDFA em = NDFA.single_character('#');
		NDFA n = NDFA.concatenate(
				NDFA.star(NDFA.alternate(a,
						NDFA.alternate(NDFA.concatenate(b, c), d))),
				NDFA.alternate(qm, em));

		// Print table
		state_table.print_state_table();

		// Test

		System.out.println("");
		System.out.println("Test \"ax\"");
		System.out.println("");
		if (n.run("ax")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"adbcbca\"");
		System.out.println("");
		if (n.run("adbcbca")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"adb\"");
		System.out.println("");
		if (n.run("adb")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}

		System.out.println("");
		System.out.println("Test \"adbcea\"");
		System.out.println("");
		if (n.run("adbcea")) {
			System.out.println("     ACCEPTED");
		} else {
			System.out.println("     REJECTED");
		}
	}
}
