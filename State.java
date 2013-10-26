package Project;

/**
 * File: State.java
 * 
 * Name: Chris Mahlke
 * 
 * Description: If the character is at the head of the input string,
 * character_transition is taken. If character is '?', any character at the head
 * of the input string is matched. But if character is '#', then
 * character_transition is a (second) empty string transition. If the
 * character_transition is null, it is unused.
 */
public class State {
	public char character;
	public State character_transition;

	// The empty_transition is taken on the empty string. If the
	// empty_transition is null, it is unused.
	public State empty_transition;

	// Set the empty_transition to State s.
	public void on_empty_goto(State s) {
		empty_transition = s;
	}

	// Set the character to c and the character_transition to State s.
	public void on_character_goto(char c, State s) {
		character = c;
		character_transition = s;
	}

	// Reinitializes State. Sets transitions to null.
	public void init() {
		character = 0;
		character_transition = empty_transition = null;
	}
}
