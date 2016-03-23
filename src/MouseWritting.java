import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import HandDraw.Character.CharacterDescription;


public class MouseWritting extends JFrame{
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final Color LINE_COLOR = Color.BLACK;

	private List<CharacterStroke> strokeCharacters = new ArrayList<CharacterStroke>();
	private CharacterStroke currentStroke;
	private List<CharacterDescription> characterStroke = new ArrayList<CharacterDescription>();

	// Set up gui
	public MouseWritting() {
		DrawCanvas canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Begin new line
				currentStroke = new CharacterStroke();
				currentStroke.addPoint(e.getPoint());
				strokeCharacters.add(currentStroke);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				CharacterDescription curCharacter = new CharacterDescription();
				curCharacter.setPoints(currentStroke.getPoints());
				characterStroke.add(curCharacter);
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				currentStroke.addPoint(e.getPoint());
				repaint();
			}
			
		});
		setContentPane(canvas);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Paint Chinese Stroke");
		pack();
		setVisible(true);
	}

	public class DrawCanvas extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(LINE_COLOR);
			for (CharacterStroke chStroke : strokeCharacters) {
				chStroke.draw(g);
			}
			for (CharacterDescription stroke: characterStroke) {
				stroke.drawCurve(g);
				System.out.println("--");
				stroke.calculateSubStroke();
			}
		}
	}
	/** The entry main method */
	   public static void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	         // Run the GUI codes on the Event-Dispatching thread for thread safety
	         @Override
	         public void run() {
	            new MouseWritting(); // Let the constructor do the job
	         }
	      });
	   }
}
