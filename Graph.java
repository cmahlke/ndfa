package Project;

// Non-Deterministic Finite Automata Code
//
// File:		graph.java
// Author:		Chris Mahlke 
//
// The code is based in part on Sun's demonstration code for the JDK.  For Sun's
// claims, see notice below
/*
 * @(#)Graph.java       1.7 98/07/17
 *
 * Copyright (c) 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class node {
	// graphics related data members
	double x;
	double y;

	double dx;
	double dy;

	boolean initial;
	boolean last;
	boolean current;
}

class transition {
	int from;
	int to;
	char character;

	double len;
}

class GraphPanel extends Panel implements MouseListener, MouseMotionListener,
		Runnable {

	Thread runner;
	graph gr;
	NDFA nd;
	boolean ready;

	int nnodes;
	node nodes[] = new node[state_table.MAXSTATES];

	int ntransitions;
	transition transitions[] = new transition[state_table.MAXSTATES * 2];

	GraphPanel(graph gr) {
		ready = false;
		this.gr = gr;
		addMouseListener(this);
		runner = new Thread(this);
		runner.start();
	}

	// run method, for refreshing the screen
	public void run() {
		while (true) {
			try {
				repaint();
				runner.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	// returns an index given a node
	int findnode(node n) {
		for (int i = 0; i < nnodes; i++) {
			if (nodes[i] == n) {
				return i;
			}
		}
		return -1;
	}

	int addnode(int index) {
		node n = new node();
		n.x = 10 + 380 * Math.random();
		n.y = 10 + 380 * Math.random();
		n.initial = n.last = false;
		nodes[index] = n;
		nnodes++;
		return index;
	}

	void addtransition(int from, int to, char c, int len) {
		transition e = new transition();
		e.from = from;
		e.to = to;
		e.character = c;
		e.len = len;
		transitions[ntransitions++] = e;
	}

	public void set_ready(boolean b) {
		ready = b;
	}

	public void clear() {
		for (int i = 0; i < nnodes; i++) {
			nodes[i] = null;
		}
		for (int i = 0; i < ntransitions; i++) {
			transitions[i] = null;
		}
		nnodes = 0;
		ntransitions = 0;
		ready = false;
	}

	public void set_new_ndfa(NDFA n) {
		clear();
		for (int i = 0; i < state_table.states_used(); i++) {
			State st = state_table.the_state_table[i];
			int len = 50;
			int to;
			// add nodes and their edges to the table
			addnode(i);
			if (st.empty_transition != null) {
				to = state_table.get_index(st.empty_transition);
				addtransition(i, to, Parser.EMPTY, len);
			}
			if (st.character_transition != null) {
				to = state_table.get_index(st.character_transition);
				addtransition(i, to, st.character, len);
			}
		}
		// mark the start and end nodes special
		nodes[state_table.get_index(n.initial)].initial = true;
		nodes[state_table.get_index(n.last)].last = true;
		this.nd = n;
		set_ready(true);
	}

	// update the states in the current set
	public void updateCurrent(boolean css[]) {
		for (int i = 0; i < nnodes; i++) {
			nodes[i].current = css[i];
		}
	}

	node pick;
	boolean pickcurrent;
	Image offscreen;
	Dimension offscreensize;
	Graphics offgraphics;

	final Color currentColor = Color.red;
	final Color selectColor = Color.pink;
	final Color transitionColor = Color.black;
	final Color nodeColor = new Color(250, 220, 100);
	final Color stressColor = Color.darkGray;
	final Color arcColor1 = Color.black;
	final Color arcColor2 = Color.pink;
	final Color arcColor3 = Color.red;

	public void paintnode(Graphics g, node n, FontMetrics fm) {
		int x = (int) n.x;
		int y = (int) n.y;
		g.setColor((n == pick) ? selectColor : (n.current ? currentColor
				: nodeColor));

		String lbl = "" + findnode(n);
		int w = fm.stringWidth(lbl) + 10;
		int h = fm.getHeight() + 4;
		g.fillRect(x - w / 2, y - h / 2, w, h);
		g.setColor(Color.black);
		g.drawRect(x - w / 2, y - h / 2, w - 1, h - 1);
		// initial node
		if (n.initial) {
			g.drawLine(x - w, y - h / 2, x - w / 2, y);
			g.drawLine(x - w, y + h / 2, x - w / 2, y);
		}
		// final node
		if (n.last) {
			g.drawOval(x - w / 2, y - h / 2, w - 1, h - 1);
		}
		g.drawString(lbl, x - (w - 10) / 2, (y - (h - 4) / 2) + fm.getAscent());
	}

	public synchronized void update(Graphics g) {
		Dimension d = getSize();
		if ((offscreen == null) || (d.width != offscreensize.width)
				|| (d.height != offscreensize.height)) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			offgraphics = offscreen.getGraphics();
			offgraphics.setFont(getFont());
		}

		offgraphics.setColor(getBackground());
		offgraphics.fillRect(0, 0, d.width, d.height);
		for (int i = 0; i < ntransitions; i++) {
			transition e = transitions[i];
			int x1 = (int) nodes[e.from].x;
			int y1 = (int) nodes[e.from].y;
			int x2 = (int) nodes[e.to].x;
			int y2 = (int) nodes[e.to].y;
			int len = (int) Math.abs(Math.sqrt((x1 - x2) * (x1 - x2)
					+ (y1 - y2) * (y1 - y2))
					- e.len);
			offgraphics.setColor((len < 10) ? arcColor1 : (len < 20 ? arcColor2
					: arcColor3));
			offgraphics.drawLine(x1, y1, x2, y2);
			// draw the transitions' letters
			String lbl = "" + e.character;
			offgraphics.setColor(stressColor);
			offgraphics.drawString(lbl, x1 + (x2 - x1) / 3, y1 + (y2 - y1) / 3);
			offgraphics.setColor(transitionColor);

		}

		FontMetrics fm = offgraphics.getFontMetrics();
		for (int i = 0; i < nnodes; i++) {
			paintnode(offgraphics, nodes[i], fm);
		}
		g.drawImage(offscreen, 0, 0, null);
	}

	// 1.1 event handling
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (!ready)
			return;
		addMouseMotionListener(this);
		double bestdist = Double.MAX_VALUE;
		int x = e.getX();
		int y = e.getY();
		for (int i = 0; i < nnodes; i++) {
			node n = nodes[i];
			double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
			if (dist < bestdist) {
				pick = n;
				bestdist = dist;
			}
		}
		pickcurrent = pick.current;
		pick.current = true;
		pick.x = x;
		pick.y = y;
		repaint();
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
		if (!ready)
			return;
		removeMouseMotionListener(this);
		pick.x = e.getX();
		pick.y = e.getY();
		pick.current = pickcurrent;
		pick = null;
		repaint();
		e.consume();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		pick.x = e.getX();
		pick.y = e.getY();
		repaint();
		e.consume();
	}

	public void mouseMoved(MouseEvent e) {
	}

}

public class graph extends Panel implements ActionListener {

	GraphPanel panel;
	Panel controlPanel = new Panel();
	Panel northSubPanel = new Panel();
	Panel southSubPanel = new Panel();
	Parser parse = new Parser();
	NDFA nd = null;
	NDFA_Computation nc = null;

	Label status = new Label("Regular Expression Parser");
	TextField regexp = new TextField("a*b*", 20);
	Button compile = new Button("Compile");
	TextField string = new TextField("aaabbb", 20);
	Button next = new Button("Next");
	Button reset = new Button("Start/Reset");

	// main
	public static void main(String[] args) {
		graph g = new graph();
		Frame applicationFrame = new Frame("rep test");
		applicationFrame.add(g, "Center");
		applicationFrame.pack();
		applicationFrame.show();
		applicationFrame.setSize(400, 400);
		applicationFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public graph() {
		panel = new GraphPanel(this);

		this.setLayout(new BorderLayout());
		controlPanel.setLayout(new BorderLayout());

		northSubPanel.add(regexp);
		northSubPanel.add(compile);
		compile.addActionListener(this);
		southSubPanel.add(string);
		southSubPanel.add(reset);
		reset.addActionListener(this);
		southSubPanel.add(next);
		next.addActionListener(this);

		controlPanel.add(status, "North");
		controlPanel.add(northSubPanel, "Center");
		controlPanel.add(southSubPanel, "South");

		this.add("Center", panel);
		this.add("South", controlPanel);
	}

	// event handler: based on which button the user pressed, perform
	// the necessary operations.
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == compile) {
			nc = null;
			try {
				state_table.clear_state_table();
				parse.set_parser_target(regexp.getText());
				nd = parse.RE();
			} catch (Exception ex) {
			}
			panel.set_new_ndfa(nd);
			panel.repaint();
			return;
		}

		if (src == next) {
			try {
				if (nc != null) {
					status.setText(nc.get_string());
					nc.next_step();
					panel.updateCurrent(nc.get_current_set());
					panel.repaint();
				}
			} catch (Exception ex) {
			}
			this.repaint();
			return;
		}

		if (src == reset) {
			nc = new NDFA_Computation(nd, string.getText());
			status.setText(nc.get_string());
			panel.updateCurrent(nc.get_current_set());
			panel.repaint();
			return;
		}
	}
}
