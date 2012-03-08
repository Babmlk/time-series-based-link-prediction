package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Facade;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_HEIGHT = 600;
	
	private Facade facade;
	private JPanel configurationPanel;
	private JPanel resultsPanel;

	public MainWindow(){
		super();
		this.init();
		this.fillConfigurationPanel();
	}
	
	private void init(){
		this.setTitle("Time Series Based Link Prediction");
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.facade = new Facade();
		this.configurationPanel = new JPanel();
		this.configurationPanel.setLayout(null);
		this.resultsPanel = new JPanel();
		this.resultsPanel.setLayout(null);
		this.setContentPane(this.configurationPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void fillConfigurationPanel() {
		//Preencher tela principal e colocar acoes nos bot›es.		
	}
	
	private void fillResultsPanel(){
		//Recuperar resultados e escrever no panel.
	}
	
	
}
