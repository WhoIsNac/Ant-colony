import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.lang.*;

public abstract class Vue extends JFrame {
	protected Modele mod;
	protected JPanel grilleGUI, menu, menuHaut, menuCentre, menuBas;

	public Vue() {
		mod = null;

		// On récupère la taille de l'écran avec la méthoede getScreenSize();
		setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Fourmis");
		setLayout(new BorderLayout());

		grilleGUI = new JPanel();
		menu = new JPanel();

		menu.setLayout(new BorderLayout());

		add(grilleGUI, BorderLayout.CENTER);
		add(menu, BorderLayout.EAST);

		menuHaut = new JPanel();
		menuCentre = new JPanel();
		menuBas = new JPanel();

		menu.add(menuHaut, BorderLayout.NORTH);
		menu.add(menuCentre, BorderLayout.CENTER);
		menu.add(menuBas, BorderLayout.SOUTH);

		menu.setBorder(new MatteBorder(0, 1, 0, 0, Color.GRAY));

		setVisible(true);

		menu.setPreferredSize(new Dimension((int)(getWidth()*0.3), getHeight()));
	}

	public void setModele(Modele m) {
		mod = m;
		grilleGUI.removeAll();
		grilleGUI.revalidate();
		grilleGUI.setLayout(new GridLayout(mod.getLargeur(), mod.getHauteur()));

		for(int y=0; y<mod.getHauteur(); y++) {
			for(int x=0; x<mod.getLargeur(); x++) {
				JPanel p = new JPanel();
				p.setName(x+"/"+y);
				grilleGUI.add(p);
			}
		}
	}

	public JPanel getJPanelAt(int x, int y) {
		return (JPanel) grilleGUI.getComponent(y*mod.getHauteur() + x);
	}

	public void afficher(boolean pheroBouffe, boolean pheroMaison) {
		for(int y=0; y<mod.getHauteur(); y++) {
			for(int x=0; x<mod.getLargeur(); x++) {
				Case c = mod.getCase(x, y);
				JPanel p = getJPanelAt(x, y);

				if(c instanceof Obstacle) {
					if(c instanceof Eau){
						p.setBackground(new Color(0.4f, 0.9f, 1f));
					} else {
						p.setBackground(new Color(0.5f, 0.5f, 0.5f));
					}
				} else {
					CaseNormale cm = (CaseNormale) c;

					if(cm.getNourriture()){
						p.setBackground(new Color(0.1f, 0.6f, 0f));
					} else if(cm.getColony()) {
						p.setBackground(Color.RED);
					} else if(pheroBouffe) {
						int pb = cm.getNiveauDePheromoneChercheBouffe();
						float limitPB = (float) cm.getLimitPheromoneBouffe();

						float r = 1f - pb / limitPB;
						float g = 1f;
						float b = 1f - pb /limitPB;
						p.setBackground(new Color(r, g, b));
					} else if(pheroMaison) {
						int pm = cm.getNiveauDePheromoneChercheMaison();
						float limitPM = (float) cm.getLimitPheromoneNid();

						float r = 1f - pm / limitPM;
						float g = 1f - pm /limitPM;
						float b = 1f;

						p.setBackground(new Color(r, g, b));
					} else {
						p.setBackground(Color.WHITE);
					}
				}
			}
		}

		revalidate();

		for(Insecte i : mod.getInsectes()) {
			int x = i.getX();
			int y = i.getY();
			JPanel p = getJPanelAt(y, x);

			JLabel l = new JLabel();
			p.setLayout(new BorderLayout(0, 0));
			p.add(l);

			x = p.getWidth();
			y = p.getHeight();

			BufferedImage im = new BufferedImage(y/2, x/2, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) im.getGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, y/2, x/2);

			ImageIcon icon = new ImageIcon(im);

			l.setIcon(icon);
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setVerticalAlignment(JLabel.CENTER);
		}
	}

	public void resetCases() {
		for(Insecte i : mod.getInsectes()) {
			int x = i.getX();
			int y = i.getY();
			JPanel p = getJPanelAt(y, x);
			p.removeAll();
		}

		grilleGUI.revalidate();
		grilleGUI.repaint();
	}

	public void deserialiser(String nom){
		try {
        	FileInputStream fis = new FileInputStream("Terrains/"+nom+".ser");
        	ObjectInputStream ois = new ObjectInputStream(fis);
        	Modele mo = (Modele) ois.readObject();
        	ois.close();
        	fis.close();

        	setModele(mo);
    	} catch (Exception e) {
        	e.printStackTrace();
      	}
    }
}
