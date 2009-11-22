package gil.jr.ecf;

/**
 * @author Gil jr Data: 20/10/2009
 * 
 * Inicia o aplicativo, chamando o 'Splash'
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Iniciar {

	public static void main(String[] args){
		
		showSplash(1500);
		
		Aplicativo.main(null);
		
	}

	private static void showSplash(int duration) {
		JWindow splash = new JWindow();
		JPanel content = (JPanel) splash.getContentPane();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 240;
		int height = 120;
		int x = (screen.width-width)/2;
		int y = (screen.height-height)/2;
		splash.setBounds(x,y,width,height);		
		JLabel label = new JLabel(new ImageIcon("pic/java1.jpg"));		
		JLabel program = new JLabel("Carregando...", JLabel.LEADING);
		program.setForeground(Color.lightGray);
		JLabel copyrt = new JLabel("Ji-Paraná 2009, Gil Jr", JLabel.CENTER);
		copyrt.setForeground(Color.lightGray);
		copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		content.add(label, BorderLayout.CENTER);		
		content.add(copyrt, BorderLayout.NORTH);
		content.add(program, BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(12, 12, 12),2));
		splash.setVisible(true);
		
		try {
			Thread.sleep(duration);
		} catch(Exception e){ } 
		
		splash.setVisible(false);
		
	}
	
}
