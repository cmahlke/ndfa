package Project;

public class state_table {

	// class data
	public static final int MAXSTATES = 200;
	// the one and only state table

	public static State[] the_state_table;

	public static int states_used;

	// static initializer
	static {
		the_state_table = new State[MAXSTATES];
		for (int i = 0; i < MAXSTATES; i++) {
			the_state_table[i] = new State();
		}
		states_used = 0;
	}

	// state-related helper functions
	// print the state_table
	public static void print_state_table() {
		System.out.println("The State Table:");
		for (int i = 0; i < states_used; i++) {
			System.out.print("     " + i);
			State st = the_state_table[i];
			if (st.character_transition != null) {
				System.out.print("    " + st.character);
				System.out.print(" --. ");
				System.out.print("" + get_index(st.character_transition));
			}
			if (st.empty_transition != null) {
				System.out.print("    # --. ");
				System.out.print("" + get_index(st.empty_transition));
			}
			System.out.println("");
		}
	}

	// clear the state_table
	public static void clear_state_table() {
		for (int i = 0; i < MAXSTATES; i++)
			the_state_table[i].init();
		states_used = 0;
	}

	// returns next unused state
	public static State new_state() {
		State ret = the_state_table[states_used++];
		ret.init();
		return ret;
	}

	// finds index of a given state in the state table
	public static int get_index(State s) {
		for (int i = 0; i < states_used; i++) {
			if (the_state_table[i] == s) {
				return i;
			}
		}
		return -1;
	}

	// find a state given an index
	public static State get_state(int i) {
		return the_state_table[i];
	}

	// returns number of states used
	public static int states_used() {
		return states_used;
	}
}
