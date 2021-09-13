import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.*;
import java.io.*;

public class VueSimulation extends Vue {
	private JRadioButton enlever, ajObstacle, ajNourriture, ajEau;
	private JToggleButton affPM, affPB;
	private JButton sauvegarder, quitter;
	private JSlider vitesse;
	private int v;
	private Timer timer;

	VueSimulation(Modele m){
		setModele(m);
		v = 100;
		timer = new Timer();

		menuHaut.setLayout(new GridLayout(4,1));

		enlever = new JRadioButton("Enlever un element");
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		menuHaut.add(p);
		p.add(enlever);

		// On fait en sorte que un seul boutton puisse être selectionné à la fois
		enlever.addActionListener( e -> {
			if(ajObstacle.isSelected()) ajObstacle.setSelected(false);
			if(ajNourriture.isSelected()) ajNourriture.setSelected(false);
			if(ajEau.isSelected()) ajEau.setSelected(false);
		});

		ajObstacle = new JRadioButton("Ajouter des obstacles");
		p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		menuHaut.add(p);
		p.add(ajObstacle);

		ajObstacle.addActionListener( e -> {
			if(enlever.isSelected()) enlever.setSelected(false);
			if(ajNourriture.isSelected()) ajNourriture.setSelected(false);
			if(ajEau.isSelected()) ajEau.setSelected(false);
		});

		ajNourriture = new JRadioButton("Ajouter des nourritures");
		p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		menuHaut.add(p);
		p.add(ajNourriture);

		ajNourriture.addActionListener( e -> {
			if(enlever.isSelected()) enlever.setSelected(false);
			if(ajObstacle.isSelected()) ajObstacle.setSelected(false);
			if(ajEau.isSelected()) ajEau.setSelected(false);
		});

		ajEau = new JRadioButton("Ajouter de l'eau");
		p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		menuHaut.add(p);
		p.add(ajEau);

		ajEau.addActionListener( e -> {
			if(enlever.isSelected()) enlever.setSelected(false);
			if(ajObstacle.isSelected()) ajObstacle.setSelected(false);
			if(ajNourriture.isSelected()) ajNourriture.setSelected(false);
		});

		//On ajoute les boutons d'affichage de phéromones
		affPB = new JToggleButton("Pheromone B");
		affPM = new JToggleButton("Pheromone M");

		affPB.setSelected(true);

		affPB.addActionListener( e -> {
			if(affPM.isSelected()) affPM.setSelected(false);
		});

		affPM.addActionListener( e -> {
			if(affPB.isSelected()) affPB.setSelected(false);
		});

		JPanel pp = new JPanel();
		pp.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

		pp.add(affPB);
		pp.add(affPM);

		// On règle la vitesse
		vitesse = new JSlider(1, 3);
		JLabel l = new JLabel("Vitesse");
		l.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		l.setAlignmentX(CENTER_ALIGNMENT);

		vitesse.setMajorTickSpacing(1);
		vitesse.setPaintTicks(true);
		vitesse.setBorder(BorderFactory.createEmptyBorder(20, 0, 80, 0));

		vitesse.addChangeListener( e -> {
			setVitesse();
			timer.cancel();
			run();
		});

		menuCentre.setLayout(new BoxLayout(menuCentre, BoxLayout.Y_AXIS));
		menuCentre.add(pp);
		menuCentre.add(l);
		menuCentre.add(vitesse);

		//On ajoute les derniers boutons
		sauvegarder = new JButton("Sauvegarder");
		quitter = new JButton("Quitter");

		menuBas.setLayout(new GridLayout(2, 1));

		p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		menuBas.add(p);
		sauvegarder.setFont(new Font("Arial", Font.PLAIN, 20));
		sauvegarder.setPreferredSize(new Dimension(180, 50));
		p.add(sauvegarder);

		sauvegarder.addActionListener( e -> {
			if(mod == null) {
				JOptionPane.showMessageDialog(this, "Le terrain n'a pas ete initialise.", "Erreur", JOptionPane.WARNING_MESSAGE);
			} else {
				String nom = JOptionPane.showInputDialog(this, "Nom du fichier:", "Sauvegarder", JOptionPane.PLAIN_MESSAGE);

				if(nom != null){
					if(!nom.equals("")) mod.serialiser(nom);
					else JOptionPane.showMessageDialog(this, "Entrez un nom.", "Erreur", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		menuBas.add(p);
		quitter.setFont(new Font("Arial", Font.PLAIN, 20));
		quitter.setPreferredSize(new Dimension(180, 50));
		p.add(quitter);

		quitter.addActionListener( e -> {
			SwingUtilities.invokeLater( () -> {
				new VueDebut();

				setVisible(false);
				dispose();
			});
		});

		menuHaut.setPreferredSize(new Dimension(menuCentre.getWidth(), (int)(menuCentre.getHeight()*0.4)));
		menuCentre.setPreferredSize(new Dimension(menuCentre.getWidth(), (int)(menuCentre.getHeight()*0.3)));
		menuBas.setPreferredSize(new Dimension(menuCentre.getWidth(), (int)(menuCentre.getHeight()*0.3)));

		revalidate();
		afficher(affPB.isSelected(), affPM.isSelected());

		run();
	}

	public void run() {
		timer = new Timer();

		TimerTask tache = new TimerTask() {
			public void run() {
				update();
			}
		};

		timer.scheduleAtFixedRate(tache, 0, v);
	}

	public void setVitesse() {
		switch(vitesse.getValue()) {
			case 1 :
				v = 150;
				break;
			case 2 :
				v = 100;
				break;
			case 3 :
				v = 50;
				break;
		}
	}

	public void update() {
		resetCases();
		mod.updateAll();
		grilleGUI.repaint();
		afficher(affPB.isSelected(), affPM.isSelected());
	}

	@Override
	public void setModele(Modele m) {
		mod = m;
		grilleGUI.removeAll();
		grilleGUI.revalidate();
		grilleGUI.setLayout(new GridLayout(mod.getLargeur(), mod.getHauteur()));

		for(int y=0; y<mod.getHauteur(); y++) {
			for(int x=0; x<mod.getLargeur(); x++) {
				JPanel p = new JPanel();
				p.setName(x+"/"+y);
				p.addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}

					//Utiliser mousePressed au lieu de mouseClicked pour faire une action est plus rapide (avec mouseClicked des fois tu cliques plusieurs fois et rien ne se passe)
					public void mousePressed(MouseEvent e) {
						//On recupere le JPanel sur lequel on a clique
						JPanel tmpJPanel = (JPanel) e.getSource();

						// Nice hack you got there
						String[] coordPanel = p.getName().split("/");
						int xPanel = Integer.valueOf(coordPanel[0]);
						int yPanel = Integer.valueOf(coordPanel[1]);

						//On change la couleur du JPanel et l'etat de sa case dans le modele
						if(enlever.isSelected()) {
							p.setBackground(Color.WHITE);
							mod.getTerrain().setCaseNormale(yPanel,xPanel);
						} else if(ajObstacle.isSelected()) {
							p.setBackground(new Color(0.5f, 0.5f, 0.5f));
							mod.getTerrain().setObstacle(yPanel,xPanel);
						} else if (ajNourriture.isSelected()){
							p.setBackground(new Color(0.1f, 0.6f, 0f));
							mod.getTerrain().caseNourriture(yPanel, xPanel);
						} else if (ajEau.isSelected()){
							p.setBackground(new Color(0.4f, 0.9f, 1f));
							mod.getTerrain().setEau(yPanel,xPanel);
						} else {
							System.out.println("Arrète de vouloir tuer un être vivant qui t'a rien fait.");
						}

						//On doit appeller cette foction pour que les changements soient faits
						grilleGUI.revalidate();
						grilleGUI.repaint();
					}
				});

				grilleGUI.add(p);
			}
		}
	}

}
