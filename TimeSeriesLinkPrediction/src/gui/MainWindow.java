package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import learning.SupervisedLearning;
import learning.UnsupervisedLearning;
import metric.SimilarityMetric;
import configuration.Configuration;
import configuration.Paths;
import controller.Facade;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	private final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_HEIGHT = 600;

	private final static int LABEL_HEIGHT = 30;
	private final static int COMBO_BOX_HEIGHT = 30;
	private final static int TEXT_FIELD_HEIGHT = 30;
	private final static int CHECK_BOX_HEIGHT = 23;
	private final static int SEPARATOR_HEIGHT = 12;
	private final static int BUTTON_HEIGHT = 50;

	private final static Color PANEL_COLOR = new Color(255, 228, 225);
	private final static Font FONT_HUGE = new Font("Lucida Grande", Font.PLAIN, 24);
	private final static Font FONT_BIG = new Font("Lucida Grande", Font.PLAIN, 15);
	private final static Font FONT_SMALL = new Font("Lucida Grande", Font.PLAIN, 13);

	private Facade facade;
	private JPanel configurationPanel;
	private JPanel resultsPanel;

	private JComboBox comboDataBase;
	private JComboBox comboBeginYear;
	private JComboBox comboEndYear;

	private JTextField textWindow;
	private JTextField textFolds;

	private JCheckBox[] checkMetrics;
	private JCheckBox[] checkForecasters;
	private JCheckBox[] checkLearningMethods;
	private JCheckBox checkStatistics;

	public MainWindow(){
		super();
		this.init();
		this.fillConfigurationPanel();
	}

	private void init(){
		this.setTitle("Time Series Based Link Prediction");
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.facade = new Facade();
		this.configurationPanel = this.createPanel(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.resultsPanel = this.createPanel(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	private JPanel createPanel(int x, int y, int width, int height){
		JPanel panel = new JPanel();
		panel.setBackground(PANEL_COLOR);
		panel.setBounds(x, y, width, height);
		panel.setLayout(null);
		return panel;
	}

	private JLabel createLabel(int x, int y, int width, int height, String label, Font font){
		JLabel temp = new JLabel(label);
		temp.setFont(font);
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JComboBox createComboBox(int x, int y, int width, int height, ComboBoxItem[] objects){
		JComboBox temp;
		if(objects != null){
			temp = new JComboBox(objects);
		}else{
			temp = new JComboBox();
		}
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JTextField createTextField(int x, int y, int width, int height){
		JTextField temp = new JTextField();
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JSeparator createSeparator(int x, int y, int width, int height){
		JSeparator temp = new JSeparator();
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JButton createButton(int x, int y, int width, int height, String label, Font font){
		JButton temp = new JButton(label);
		temp.setFont(font);
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JCheckBox createCheckBox(int x, int y, int width, int height, String label){
		JCheckBox temp = new JCheckBox(label);
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private ComboBoxItem[] dataBases(){
		int n = Configuration.categoryListDiscription.length;
		ComboBoxItem[] itens = new ComboBoxItem[n];
		for(int i = 0; i < n; i++){
			itens[i] = new ComboBoxItem(Configuration.categoryListDiscription[i], Configuration.categoryListId[i]);
		}
		return itens;
	}

	private ComboBoxItem[] years(){
		int[] years = Paths.yearsAvaliable();
		ComboBoxItem[] itens = new ComboBoxItem[years.length];
		for(int i = 0; i < years.length; i++){
			itens[i] = new ComboBoxItem(Integer.toString(years[i]), years[i]);
		}
		return itens;
	}

	private void updateDataInfo(){
		Configuration.category = (String) ((ComboBoxItem) this.comboDataBase.getSelectedItem()).getValue();
		ComboBoxItem[] years = this.years();
		this.comboBeginYear.setModel(new DefaultComboBoxModel(years));
		this.comboEndYear.setModel(new DefaultComboBoxModel(years));
		this.comboBeginYear.setSelectedIndex(0);
		this.comboEndYear.setSelectedIndex(years.length - 1);

	}

	private void dataInfo(){
		JLabel label1 = this.createLabel(30, 40, 80, LABEL_HEIGHT, "Data Base:", FONT_BIG);
		this.comboDataBase = this.createComboBox(122, 42, 135, COMBO_BOX_HEIGHT, this.dataBases());
		this.comboDataBase.setSelectedIndex(0);
		this.comboDataBase.addItemListener(new ItemListener(){  
			public void itemStateChanged(ItemEvent e) {  
				updateDataInfo();
			}  
		});  

		JLabel label2 = this.createLabel(395, 40, 60, LABEL_HEIGHT, "From:", FONT_BIG);
		JLabel label3 = this.createLabel(553, 40, 20, LABEL_HEIGHT, "to", FONT_BIG);
		this.comboBeginYear = this.createComboBox(451, 42, 90, COMBO_BOX_HEIGHT, null);
		this.comboEndYear = this.createComboBox(585, 42, 90, COMBO_BOX_HEIGHT, null);

		updateDataInfo();

		this.configurationPanel.add(label1);
		this.configurationPanel.add(label2);
		this.configurationPanel.add(label3);
		this.configurationPanel.add(this.comboDataBase);
		this.configurationPanel.add(this.comboBeginYear);
		this.configurationPanel.add(this.comboEndYear);
	}

	private JCheckBox[] checkBoxArea(int x, int y, int width, int lines, String[] labels){
		final JCheckBox[] checks = new JCheckBox[labels.length];

		int xPadding = width + 5;

		for(int i = 0; i < labels.length; i++){
			checks[i] = this.createCheckBox(x, y+(i%lines)*35, width, CHECK_BOX_HEIGHT, labels[i]);
			if((i+1)%lines == 0){
				x += xPadding;
			}			
		}
		final JCheckBox checkAll = this.createCheckBox(x, y+(labels.length%lines)*35, width, CHECK_BOX_HEIGHT, "Select All");
		checkAll.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton = (AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				if(buttonModel.isSelected()){
					for(JCheckBox check: checks){
						check.setSelected(true);
					}
				}
			}
		});

		for(JCheckBox check: checks){
			check.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent changeEvent) {
					AbstractButton abstractButton = (AbstractButton)changeEvent.getSource();
					ButtonModel buttonModel = abstractButton.getModel();
					if(!buttonModel.isSelected()){
						checkAll.setSelected(false);
					}
				}
			});
			this.configurationPanel.add(check);
		}
		this.configurationPanel.add(checkAll);

		return checks;
	}

	private void similarityMetrics(){
		JLabel label1 = this.createLabel(30, 143, 150, LABEL_HEIGHT, "Similarity Metrics:", FONT_BIG);
		String[] labels = new String[Configuration.metrics.length];
		for(int i = 0; i < Configuration.metrics.length; i++){
			labels[i] = Configuration.metrics[i].getFullName();
		}
		this.checkMetrics = this.checkBoxArea(30, 182, 260, 2, labels);
		this.configurationPanel.add(label1);		
	}

	private void forecastingModels(){
		JLabel label1 = this.createLabel(30, 252, 150, LABEL_HEIGHT, "Forecasting Models:", FONT_BIG);
		String[] labels = new String[Configuration.forecasters.length];
		for(int i = 0; i < Configuration.forecasters.length; i++){
			labels[i] = Configuration.forecasters[i].getFullName();
		}
		this.checkForecasters = this.checkBoxArea(30, 291, 260, 3, labels);
		this.configurationPanel.add(label1);		
	}

	private void frameworkSettings(){
		JLabel label1 = this.createLabel(30, 104, 170, LABEL_HEIGHT, "Window of Prediction:", FONT_BIG);
		this.textWindow = this.createTextField(200, 104, 45, TEXT_FIELD_HEIGHT);

		this.similarityMetrics();
		this.forecastingModels();

		this.configurationPanel.add(label1);
		this.configurationPanel.add(textWindow);

	}

	private void learningMethods(){
		JLabel label1 = this.createLabel(30, 420, 150, LABEL_HEIGHT, "Learning Methods:", FONT_BIG);
		String[] labels = {UnsupervisedLearning.FULL_NAME, SupervisedLearning.FULL_NAME};
		this.checkLearningMethods = this.checkBoxArea(30, 459, 260, 2, labels);
		this.configurationPanel.add(label1);		
	}

	private void statistics(){
		JLabel label1 = this.createLabel(420, 420, 170, LABEL_HEIGHT, "Statistical Analysis:", FONT_BIG);
		this.checkStatistics = this.createCheckBox(420, 459, 60, CHECK_BOX_HEIGHT, "Yes");

		final JPanel panel = new JPanel();
		panel.setBackground(PANEL_COLOR);
		panel.setBounds(438, 479, 115, LABEL_HEIGHT + 20);
		panel.setLayout(null);
		panel.setVisible(true);
		JLabel label2 = this.createLabel(10, 10, 50, LABEL_HEIGHT, "Folds:", FONT_SMALL);
		this.textFolds = this.createTextField(60, 10, 45, TEXT_FIELD_HEIGHT);
		panel.add(label2);
		panel.add(this.textFolds);

		this.checkStatistics.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton = (AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				if(buttonModel.isSelected()){
					panel.setVisible(true);
				}else{
					panel.setVisible(false);
				}
			}
		});

		this.configurationPanel.add(label1);
		this.configurationPanel.add(this.checkStatistics);
		this.configurationPanel.add(panel);
	}

	private void classification(){
		this.learningMethods();
		this.statistics();
	}

	private void go(){
		JButton goButton = this.createButton(620, 464, 99, BUTTON_HEIGHT, "Go!", FONT_HUGE);
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					facade.setDataBaseInfo(category, beginYear, endYear);
					facade.setFrameworkSettings(windowOfPrediction, metrics, forecasters);
					facade.setLearningMethods(learningMethods, statistics, folds);
					facade.go();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		this.configurationPanel.add(goButton);
	}

	private void fillConfigurationPanel() {
		this.dataInfo();
		this.configurationPanel.add(this.createSeparator(30, 80, 740, SEPARATOR_HEIGHT));
		this.frameworkSettings();
		this.configurationPanel.add(this.createSeparator(30, 396, 740, SEPARATOR_HEIGHT));
		this.classification();
		this.go();
		
		this.setContentPane(this.configurationPanel);
	}

	private void fillResultsPanel(){
		//Recuperar resultados e escrever no panel.
	}

	public static void main(String[] args) {
		new MainWindow();
	}

}
