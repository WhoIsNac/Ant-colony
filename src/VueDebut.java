import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class VueDebut extends Vue {
	private JSlider fourmis, nourriture, taille, densite;
	private JButton generer, charger, commencer, sauvegarder;

	public VueDebut() {
		menuHaut.setLayout(new GridLayout(4, 1));
		menuCentre.setLayout(new GridLayout(3, 1));
		menuBas.setLayout(new GridLayout(1, 1));

		menuHaut.setPreferredSize(new Dimension(menu.getWidth(), (int)(menu.getHeight()*0.6)));
		menuCentre.setPreferredSize(new Dimension(menu.getWidth(), (int)(menu.getHeight()*0.25)));
		menuBas.setPreferredSize(new Dimension(menu.getWidth(), (int)(menu.getHeight()*0.15)));

		fourmis = new JSlider(0, 100);
		nourriture = new JSlider(0, 10);
		taille = new JSlider(10, 100);
		densite = new JSlider(0, 10);

		fourmis.setBorder(BorderFactory.createTitledBorder("Fourmis"));
		fourmis.setMinorTickSpacing(5);
		fourmis.setMajorTickSpacing(25);
		fourmis.setPaintTicks(true);
		fourmis.setPaintLabels(true);
		fourmis.setSnapToTicks(true);

		nourriture.setBorder(BorderFactory.createTitledBorder("Nourriture"));
		nourriture.setMinorTickSpacing(1);
		nourriture.setMajorTickSpacing(2);
		nourriture.setPaintTicks(true);
		nourriture.setPaintLabels(true);
		nourriture.setSnapToTicks(true);

		taille.setBorder(BorderFactory.createTitledBorder("Taille"));
		taille.setMinorTickSpacing(5);
		taille.setMajorTickSpacing(30);
		taille.setPaintTicks(true);
		taille.setPaintLabels(true);
		taille.setSnapToTicks(true);

		densite.setBorder(BorderFactory.createTitledBorder("Densite"));
		densite.setMinorTickSpacing(1);
		densite.setMajorTickSpacing(2);
		densite.setPaintTicks(true);
		densite.setPaintLabels(true);
		densite.setSnapToTicks(true);

		menuHaut.add(fourmis);
		menuHaut.add(nourriture);
		menuHaut.add(taille);
		menuHaut.add(densite);

		generer = new JButton("Generer");
		charger = new JButton("Charger");
		sauvegarder = new JButton("Sauvegarder");

		JPanel p = new JPanel();
		generer.setFont(new Font("Arial", Font.PLAIN, 20));
		generer.setPreferredSize(new Dimension(180, 40));
		menuCentre.add(p);
		p.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		p.add(generer);

		generer.addActionListener( e -> {
			int FOURMIS = fourmis.getValue();
			int NOURRITURE = nourriture.getValue();
			int TAILLE = taille.getValue();
			int DENSITE = densite.getValue();

			setModele(new Modele(new Grille(TAILLE, TAILLE, DENSITE+1, NOURRITURE), FOURMIS-1));
			afficher(true, false);
			grilleGUI.repaint();
		});

		p = new JPanel();
		charger.setFont(new Font("Arial", Font.PLAIN, 20));
		charger.setPreferredSize(new Dimension(180, 40));
		menuCentre.add(p);
		p.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		p.add(charger);

		charger.addActionListener( e -> {
			File dossier = new File("Terrains/");
			File[] fichiers = dossier.listFiles();
			LinkedList<String> listeNoms = new LinkedList<String>();

			for(File f : fichiers) {
				if(f.isFile()){
					String nom = f.getName();
					int i = nom.lastIndexOf(".");
					String type = "";

					if(i > 0) {
						type = nom.substring(i);
					}

					if(type.equals(".ser")) {
						listeNoms.add(nom.substring(0, i));
					}
				}
			}

			Object[] noms = listeNoms.toArray();

			String choix = (String) JOptionPane.showInputDialog(this, "Choisissez un fichier:", "Charger", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), noms, "");

			if(choix != null) {
				revalidate();
				deserialiser(choix);
				afficher(true, false);
			} else {
				JOptionPane.showMessageDialog(this, "Aucun fichier n'a été choisi", "Erreur", JOptionPane.WARNING_MESSAGE);
			}
		});

		p = new JPanel();
		sauvegarder.setFont(new Font("Arial", Font.PLAIN, 20));
		sauvegarder.setPreferredSize(new Dimension(180, 40));
		menuCentre.add(p);
		p.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		p.add(sauvegarder);

		sauvegarder.addActionListener( e -> {
			if(mod == null) {
				JOptionPane.showMessageDialog(this, "Le terrain n'a pas été généré.", "Erreur", JOptionPane.WARNING_MESSAGE);
			} else {
				String nom = JOptionPane.showInputDialog(this, "Nom du fichier:", "Sauvegarder", JOptionPane.PLAIN_MESSAGE);

				if(nom != null){
					if(!nom.equals("")) mod.serialiser(nom);
					else JOptionPane.showMessageDialog(this, "Entrez un nom pour le fichier.", "Erreur", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		commencer = new JButton("Commencer");

		p = new JPanel();
		commencer.setFont(new Font("Arial", Font.PLAIN, 20));
		commencer.setPreferredSize(new Dimension(180, 50));
		menuBas.add(p);
		p.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		p.add(commencer);
		commencer.addActionListener( e -> {
			if(mod != null) {
				SwingUtilities.invokeLater( () -> {
					new VueSimulation(mod);

					setVisible(false);
					dispose();
				});
			} else {
				JOptionPane.showMessageDialog(this, "Le terrain n'a pas été généré.", "Erreur", JOptionPane.WARNING_MESSAGE);
			}
		});

		grilleGUI.setBackground(new Color(0.7f, 0.7f, 0.7f));
	}
}
