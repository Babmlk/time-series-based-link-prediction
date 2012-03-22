package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import learning.LearningMethod;
import metric.SimilarityMetric;
import model.ResultsBoard;
import configuration.Configuration;
import configuration.Paths;
import controller.Facade;
import forecasting.Forecaster;
import format.Formater;

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
	private final static Font FONT_TINY = new Font("Lucida Grande", Font.PLAIN, 10);

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

	private JButton buttonForward;

	private JLabel labelProgress;

	//private boolean processing;
	
	Timer timer;

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
		if(font != null){
			temp.setFont(font);
		}
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JCheckBox createCheckBox(int x, int y, int width, int height, String label){
		JCheckBox temp = new JCheckBox(label);
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JTabbedPane createTabbedPane(int x, int y, int width, int height){
		JTabbedPane temp = new JTabbedPane();
		temp.setBounds(x, y, width, height);
		return temp;
	}

	private JScrollPane createScroolPane(int x, int y, int width, int height, Component view){
		JScrollPane temp = new JScrollPane();
		temp.setBounds(x, y, width, height);
		temp.setViewportView(view);
		return temp;
	}

	private JTable createTable(ResultsBoard resultsBoard, boolean statistics){
		JTable temp = new JTable();
		temp.setColumnSelectionAllowed(true);
		temp.setCellSelectionEnabled(true);
		temp.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		temp.setBorder(null);
		String[][] data = new String[resultsBoard.getLinesCount()][resultsBoard.getColumnsCount() + 1];
		for(int i = 0; i < resultsBoard.getLinesCount(); i++){
			for(int j = 0; j < resultsBoard.getColumnsCount() + 1; j++){
				if(j == 0){
					data[i][j] = resultsBoard.getLineNames()[i];
				}else{
					data[i][j] = resultsBoard.getFormatedResult(i, j - 1, statistics);
				}
			}
		}
		String[] headers = new String[resultsBoard.getColumnsCount() + 1];
		headers[0] = "";
		for(int i = 1; i < resultsBoard.getColumnsCount() + 1; i++){
			headers[i] = resultsBoard.getColumnNames()[i-1];
		}
		temp.setModel(new DefaultTableModel(data, headers));	
		return temp;
	}

	private ComboBoxItem[] dataBases(){
		int n = Configuration.CATEGORY_LIST_DISCRIPTION.length;
		ComboBoxItem[] itens = new ComboBoxItem[n];
		for(int i = 0; i < n; i++){
			itens[i] = new ComboBoxItem(Configuration.CATEGORY_LIST_DISCRIPTION[i], Configuration.CATEGORY_LIST_ID[i]);
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

		final JCheckBox checkAll = this.createCheckBox(x, y+(labels.length%lines)*35, 100, CHECK_BOX_HEIGHT, "Select All");
		checkAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				if(abstractButton.getModel().isSelected()){
					for(JCheckBox check: checks){
						check.setSelected(true);
					}
				}
			}
		});

		for(JCheckBox check: checks){
			check.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
					if(!abstractButton.getModel().isSelected()){
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
		String[] labels = new String[Configuration.METRICS.length];
		for(int i = 0; i < Configuration.METRICS.length; i++){
			labels[i] = Configuration.METRICS[i].getFullName();
		}
		this.checkMetrics = this.checkBoxArea(30, 182, 260, 2, labels);
		this.configurationPanel.add(label1);		
	}

	private void forecastingModels(){
		JLabel label1 = this.createLabel(30, 252, 150, LABEL_HEIGHT, "Forecasting Models:", FONT_BIG);
		String[] labels = new String[Configuration.FORECASTERS.length];
		for(int i = 0; i < Configuration.FORECASTERS.length; i++){
			labels[i] = Configuration.FORECASTERS[i].getFullName();
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
		String[] labels = new String[Configuration.LEARNING_METHODS.length];
		for(int i = 0; i < Configuration.LEARNING_METHODS.length; i++){
			labels[i] = Configuration.LEARNING_METHODS[i].getFullName();
		}
		this.checkLearningMethods = this.checkBoxArea(30, 459, 260, 2, labels);
		this.configurationPanel.add(label1);		
	}

	private void statistics(){
		JLabel label1 = this.createLabel(420, 420, 170, LABEL_HEIGHT, "Statistical Analysis:", FONT_BIG);
		this.checkStatistics = this.createCheckBox(420, 459, 60, CHECK_BOX_HEIGHT, "Yes");

		final JPanel foldsPanel = new JPanel();
		foldsPanel.setBackground(PANEL_COLOR);
		foldsPanel.setBounds(438, 479, 115, LABEL_HEIGHT + 20);
		foldsPanel.setLayout(null);
		foldsPanel.setVisible(false);
		JLabel label2 = this.createLabel(10, 10, 50, LABEL_HEIGHT, "Folds:", FONT_SMALL);
		this.textFolds = this.createTextField(60, 10, 45, TEXT_FIELD_HEIGHT);
		foldsPanel.add(label2);
		foldsPanel.add(this.textFolds);

		this.checkStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				if(abstractButton.getModel().isSelected()){
					foldsPanel.setVisible(true);
				}else{
					foldsPanel.setVisible(false);
				}
			}
		});

		this.configurationPanel.add(label1);
		this.configurationPanel.add(this.checkStatistics);
		this.configurationPanel.add(foldsPanel);
	}

	private void classification(){
		this.learningMethods();
		this.statistics();
	}

	private void goAction() throws Exception{
		String category = (String) ((ComboBoxItem) comboDataBase.getSelectedItem()).getValue();
		int beginYear = (Integer) ((ComboBoxItem) comboBeginYear.getSelectedItem()).getValue();
		int endYear = (Integer) ((ComboBoxItem) comboEndYear.getSelectedItem()).getValue();
		int windowOfPrediction = Integer.parseInt(textWindow.getText());//Try catch
		ArrayList<SimilarityMetric> selectedMetrics = new ArrayList<SimilarityMetric>();
		for(int i = 0; i < checkMetrics.length; i++){
			if(checkMetrics[i].isSelected()){
				selectedMetrics.add(Configuration.METRICS[i]);
			}
		}
		ArrayList<Forecaster> selectedForecasters = new ArrayList<Forecaster>();
		for(int i = 0; i < checkForecasters.length; i++){
			if(checkForecasters[i].isSelected()){
				selectedForecasters.add(Configuration.FORECASTERS[i]);
			}
		}	
		ArrayList<LearningMethod> selectedLearning = new ArrayList<LearningMethod>();
		for(int i = 0; i < checkLearningMethods.length; i++){
			if(checkLearningMethods[i].isSelected()){
				selectedLearning.add(Configuration.LEARNING_METHODS[i]);
			}
		}
		boolean statistics = checkStatistics.isSelected();
		int folds = 0;
		if(statistics){ 
			folds = Integer.parseInt(textFolds.getText());//try catch
		}
		facade.setDataBaseInfo(category, beginYear, endYear);
		facade.setFrameworkSettings(windowOfPrediction, selectedMetrics, selectedForecasters);
		facade.setLearningMethods(selectedLearning, statistics, folds);
		facade.go();
	}

	private void go(){
		JButton goButton = this.createButton(620, 464, 99, BUTTON_HEIGHT, "Go!", FONT_HUGE);
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					goAction();
					fillResultsPanel();
					setContentPane(resultsPanel);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		this.configurationPanel.add(goButton);
	}

	private void fillConfigurationPanel() {
		this.buttonForward = this.createButton(707, 6, 75, 29, "forward", null);
		this.buttonForward.setVisible(false);
		this.buttonForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(resultsPanel);			
			}
		});
		this.configurationPanel.add(this.buttonForward);

		this.dataInfo();
		this.configurationPanel.add(this.createSeparator(30, 80, 740, SEPARATOR_HEIGHT));
		this.frameworkSettings();
		this.configurationPanel.add(this.createSeparator(30, 396, 740, SEPARATOR_HEIGHT));
		this.classification();
		this.go();
		this.configurationPanel.add(this.createSeparator(30, 530, 740, SEPARATOR_HEIGHT));
		this.labelProgress = this.createLabel(35, 535, 400, LABEL_HEIGHT, "", FONT_TINY);
		this.configurationPanel.add(this.labelProgress);

		this.setContentPane(this.configurationPanel);
						
	}

	private JPanel tablePanel(int x, int y, String label, ResultsBoard resultsBoard, boolean statistics){
		JPanel panel1 = this.createPanel(x, y, 747, 210);
		JLabel label1 = this.createLabel(40, 30, 200, LABEL_HEIGHT, label, FONT_BIG);

		JTable table = this.createTable(resultsBoard, statistics);
		final JScrollPane scroll1 = this.createScroolPane(74, 80, 600, 85, table);

		JTextArea textArea = new JTextArea();
		textArea.setText(Formater.tableToLatex(resultsBoard, statistics));
		final JScrollPane scroll2 = this.createScroolPane(74, 60, 600, 105, textArea);
		scroll2.setVisible(false);

		final JButton button1 = this.createButton(73, 177, 117, 29, "Show Table", null);
		button1.setEnabled(false);		
		final JButton button2 = this.createButton(203, 177, 117, 29, "Show LaTeX", null);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scroll1.setVisible(true);	
				scroll2.setVisible(false);
				button1.setEnabled(false);
				button2.setEnabled(true);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scroll1.setVisible(false);	
				scroll2.setVisible(true);
				button2.setEnabled(false);
				button1.setEnabled(true);
			}
		});

		panel1.add(label1);
		panel1.add(scroll1);
		panel1.add(scroll2);
		panel1.add(button1);
		panel1.add(button2);

		return panel1;
	}

	private JPanel absoluteResultsPanel(ResultsBoard resultsBoard){
		return this.tablePanel(6, 24, "Absolute Results:", resultsBoard, false);
	}

	private JPanel relativeResultsPanel(ResultsBoard resultsBoard){
		return this.tablePanel(6, 270, "Relative Results:", resultsBoard, true);
	}

	private void fillResultsPanel(){
		this.resultsPanel.removeAll();
		
		this.buttonForward.setVisible(true);

		JButton button1 = this.createButton(18, 6, 75, 29, "back", null);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(configurationPanel);			
			}
		});

		JTabbedPane tabbed1 = this.createTabbedPane(10, 40, 780, 530);
		int i = 0;
		for(LearningMethod learning : this.facade.getLearningMethods()){
			JPanel panel = new JPanel();
			panel.setBackground(PANEL_COLOR);
			panel.setLayout(null);
			tabbed1.addTab(learning.getFullName(), null, panel, null);
			tabbed1.setEnabledAt(i++, true);
			panel.add(this.absoluteResultsPanel(learning.getAbsoluteResults()));

			JSeparator separator1 = this.createSeparator(6, 246, 747, SEPARATOR_HEIGHT);
			panel.add(separator1);

			if(facade.isStatistics()){
				panel.add(this.relativeResultsPanel(learning.getRelativeResults()));
			}
		}

		this.resultsPanel.add(button1);
		this.resultsPanel.add(tabbed1);
	}

	public static void main(String[] args) {
		new MainWindow();
	}

}
