package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class UIPath {

	private static int agentW = 15;
	private static int agentH = 25;

	private final static String wreckPath = "src/res/wreck2.png";
	private final static String agentPath = "src/res/agent2.png";
	private final static String shipPath = "src/res/ship2.png";
	private final static String stationPath = "src/res/station2.png";

	private static int mainIconW = 40;
	private static int mainIconH = 30;

	private static int textW = 35;
	private static int textH = 20;

	private static int cellW = 60;
	private static int cellH = 50;

	private final static Color backGround = new Color(186, 236, 255);

	private static int rows;
	private static int cols;

	private final static int frameW = 1000;
	private final static int frameH = 800;

	private static int shifX;
	private static int shifY;

	private static JFrame f;

	private static State[] goalPath;

	private int currentState;

	private static Stack<JComponent> component = new Stack<>();

	public UIPath(State[] goalPath) {
		this.goalPath = goalPath;
		rows = goalPath[0].getGrid().length;
		cols = goalPath[0].getGrid()[0].length;
		calcVars();
		shifX = frameW / 2 - (cols * cellW / 2);
		shifY = frameH / 2 - (rows * cellH / 2) + 25;
		this.currentState = 0;
		JButton next = new JButton("next");
		next.addActionListener(nextAction);
		next.setBounds(1000 - 120, 10, 100, 30);

		JButton previous = new JButton("previous");
		previous.addActionListener(perAction);
		previous.setBounds(1000 - 240, 10, 100, 30);

		f = new JFrame();
		f.getContentPane().setBackground(backGround);
		f.add(next);
		f.add(previous);
		draw();
		f.setLayout(null);
		f.setSize(frameW + 10, frameH + 40);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private static void calcVars() {
		if (rows <= 5 && cols <= 5) {
			cellW = 150;
			cellH = 140;
		} else if (rows <= 10 && cols <= 10) {
			cellW = 80;
			cellH = 70;
		}

		mainIconW = (int) (cellW * 0.6);
		mainIconH = (int) (cellH * 0.6);

		agentW = (int) (cellW * 0.25);
		agentH = (int) (cellH * 0.5);

		textH = (int) (cellW * 0.75);
		textH = (int) (cellH * 0.4);

	}

	private void draw() {
		clear();
		drawStateGrid(goalPath[currentState]);
	}

	private void clear() {
		while (!component.isEmpty()) {
			f.remove(component.pop());// or remove(JComponent)
			f.revalidate();
			f.repaint();
		}
	}

	private static ImageIcon resize(ImageIcon image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(// ww w . jav a2 s. c o m
				new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		return new ImageIcon(bi);
	}

	private static JLabel mainIconLabel(int x, int y, String iconPath) {
		ImageIcon wreckIcon = new ImageIcon(iconPath);
		wreckIcon = resize(wreckIcon, mainIconW, mainIconH);
		JLabel shipLabel = new JLabel();
		shipLabel.setOpaque(true);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		shipLabel.setBounds(x, y, mainIconW, mainIconH);
		shipLabel.setBackground(backGround);
		// shipLabel.setBorder(blackline);
		shipLabel.setIcon(wreckIcon);
		return shipLabel;

	}

	private static JLabel agentLabel(int x, int y) {
		ImageIcon agentIcon = new ImageIcon(agentPath);
		agentIcon = resize(agentIcon, agentW, agentH);
		JLabel agentLabel = new JLabel();
		agentLabel.setOpaque(true);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		agentLabel.setBounds(x, y, agentW, agentH);
		agentLabel.setBackground(backGround);
		// agentLabel.setBorder(blackline);
		agentLabel.setIcon(agentIcon);
		return agentLabel;
	}

	private static JLabel textLabel(int x, int y, String s) {
		JLabel textLabel = new JLabel(s);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setVerticalAlignment(SwingConstants.CENTER);
		textLabel.setOpaque(true);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		textLabel.setBounds(x, y, textW, textH);
		textLabel.setBackground(backGround);
		// textLabel.setBorder(blackline);
		return textLabel;
	}

	private static void addCellLabel(int x, int y, int w, int h, int cellCode, boolean agent, String text) {

		JLabel mainLabel = new JLabel();
		mainLabel.setOpaque(true);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		mainLabel.setBounds(x, y, w, h);
		mainLabel.setBackground(backGround);
		mainLabel.setBorder(blackline);

		JLabel mainIconLabel = null;
		if (cellCode == 1)
			mainIconLabel = mainIconLabel(x + (w / 2) - (mainIconW / 2), y + h - mainIconH - 2, shipPath);
		else if (cellCode == 2)
			mainIconLabel = mainIconLabel(x + (w / 2) - (mainIconW / 2), y + h - mainIconH - 2, wreckPath);
		else if (cellCode == 3)
			mainIconLabel = mainIconLabel(x + (w / 2) - (mainIconW / 2), y + h - mainIconH - 2, stationPath);

		JLabel agentLabel = null;
		if (agent)
			agentLabel = agentLabel(x + 2, y + 2);

		JLabel textLabel = null;
		if (text != null)
			textLabel = textLabel(x + agentW + 2, y + 2, text);

		if (textLabel != null) {
			f.add(textLabel);
			component.push(textLabel);
		}

		if (agentLabel != null) {
			f.add(agentLabel);
			component.push(agentLabel);
		}

		if (mainIconLabel != null) {
			f.add(mainIconLabel);
			component.push(mainIconLabel);
		}

		f.add(mainLabel);
		component.push(mainLabel);

	}

	private static void drawStateGrid(State s) {
		Object[][][] stateParsing = s.stateToUI();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// System.out.println((int)(j * this.cellW)+" "+(int)(i * cellH));
				// System.out.println(Arrays.toString(stateParsing[i][j]));
				addCellLabel(shifX + j * cellW, shifY + i * cellH, cellW, cellH, (int) stateParsing[i][j][0],
						(boolean) stateParsing[i][j][1], (String) stateParsing[i][j][2]);
			}
		}
		
		JLabel text = new JLabel(s.textToUI());
		text.setBounds(200, 20, 700, textH);
		f.add(text);
		component.push(text);
	}

	ActionListener nextAction = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			if (currentState < UIPath.goalPath.length - 1) {
				currentState++;
				draw();
			}

		}
	};

	ActionListener perAction = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			if (currentState > 0) {
				currentState--;
				draw();
			}

		}
	};

}