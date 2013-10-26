package Project;

import java.util.BitSet;

public class NDFA {
	// instance data
	public State initial;
	public State last;

	// constructor method
	public NDFA(State s, State e) {
		initial = s;
		last = e;
	}

	// In any NDFA, the empty_transition of the last State must be null.
	// Build NDFA accepting a single character.
	public static NDFA single_character(char ch) {
		// new initial State and end State
		State ini = state_table.new_state();
		State end = state_table.new_state();

		// connect the states
		ini.on_character_goto(ch, end);

		// create a new NDFA
		return new NDFA(ini, end);
	}

	// Build an NDFA concatenating two NDFAs.
	public static NDFA concatenate(NDFA prefix, NDFA suffix) {
		// Connect the end of the first to the beginning of the last
		prefix.last.on_empty_goto(suffix.initial);
		return new NDFA(prefix.initial, suffix.last);
	}

	// Build an NDFA that is the alternation (union) of two NDFAs.
	public static NDFA alternate(NDFA n1, NDFA n2) {
		// new initial State and end State
		State ini = state_table.new_state();
		State end = state_table.new_state();

		// Two choices, alternatives
		ini.on_empty_goto(n1.initial);
		ini.on_character_goto(Parser.EMPTY, n2.initial);

		n1.last.on_empty_goto(end);
		n2.last.on_empty_goto(end);

		// create new NDFA
		return new NDFA(ini, end);
	}

	// Build an NDFA that is the Kleene star of another NDFA.
	public static NDFA star(NDFA n) {
		// new initial State
		State ini = state_table.new_state();

		// Create the loop
		ini.on_character_goto(Parser.EMPTY, n.initial);
		n.last.on_empty_goto(ini);

		// The new initial State is also the last State
		return new NDFA(ini, ini);
	}

	public static NDFA plus(NDFA n) {
		// new initial State and end State
		State ini = state_table.new_state();
		State end = state_table.new_state();

		// New initial State connects to n
		ini.on_character_goto(Parser.EMPTY, n.initial);
		n.last.on_empty_goto(end);
		end.on_character_goto(Parser.EMPTY, ini);

		return new NDFA(ini, end);
	}

	// Execute an NDFA on an input string s. Return true or false if the
	// string is accepted or not.
	public boolean run(String s) throws Rep_Exception {
		int inputPoint = 0; // points ot char of string s

		// set current State
		BitSet currentStateSet = new BitSet();
		currentStateSet.set(state_table.get_index(initial));

		for (;;) {
			// A next states and processed states
			BitSet processedStateSet = new BitSet();
			BitSet nextStateSet = new BitSet();

			while (!currentStateSet.equals(processedStateSet)) {
				// Find a memeber of the current State set that is not
				// a member of the processed State set
				int bitIndex = 0;
				while (!currentStateSet.get(bitIndex)
						|| processedStateSet.get(bitIndex)) {
					++bitIndex;
				}
				State pState = state_table.get_state(bitIndex);

				// The State connected to pState through
				// a character transition
				State qState = pState.character_transition;

				// The transistion out of pState is through the character in s
				if ((inputPoint < s.length()) && (qState != null)
						&& (pState.character == s.charAt(inputPoint))) {
					nextStateSet.set(state_table.get_index(qState));
				}

				// The transistion out of pState is the wildcard, ?
				if (pState.character == Parser.WILD) {
					nextStateSet.set(state_table.get_index(qState));
				}

				// The transition is the empty character transition
				if ((pState.character == Parser.EMPTY) && (qState != null)) {
					currentStateSet.set(state_table.get_index(qState));
				}

				// The transition is the empty transition
				if (pState.empty_transition != null) {
					qState = pState.empty_transition;
					currentStateSet.set(state_table.get_index(qState));
				}
				processedStateSet.set(bitIndex);
			}

			// If no more chars in string, then we're done
			if (inputPoint == s.length()) {
				break;
			}

			// increment to next char in string
			++inputPoint;

			// update current State
			currentStateSet = nextStateSet;
		}

		// Test if the final State is in the current State set
		if (currentStateSet.get(state_table.get_index(last))) {
			return true;
		} else {
			return false;
		}
	}
}
