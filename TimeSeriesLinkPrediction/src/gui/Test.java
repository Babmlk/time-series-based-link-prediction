package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;

public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 228, 225));
		panel.setBounds(0, 0, 800, 600);
		panel.setLayout(null);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Data Base:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setSize(80, 27);
		lblNewLabel.setLocation(30, 40);
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"hep-th:arXiv", "hep-lat:arXiv"}));
		comboBox.setBounds(122, 42, 135, 27);
		panel.add(comboBox);
		
		JLabel lblPeriod = new JLabel("From:");
		lblPeriod.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPeriod.setBounds(395, 40, 60, 27);
		panel.add(lblPeriod);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1991", "1992", "1993", "1994", "1995", "1996"}));
		comboBox_1.setBounds(451, 42, 90, 27);
		panel.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"1991", "1992", "1993", "1994", "1995", "1996"}));
		comboBox_2.setBounds(585, 42, 90, 27);
		panel.add(comboBox_2);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblTo.setBounds(553, 40, 20, 27);
		panel.add(lblTo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 80, 740, 12);
		panel.add(separator);
		
		JLabel lblWindowOfPrediction = new JLabel("Window of Prediction:");
		lblWindowOfPrediction.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblWindowOfPrediction.setBounds(30, 104, 170, 27);
		panel.add(lblWindowOfPrediction);
		
		textField = new JTextField();
		textField.setBounds(212, 104, 45, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSimilarityMetrics = new JLabel("Similarity Metrics:");
		lblSimilarityMetrics.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblSimilarityMetrics.setBounds(30, 143, 150, 27);
		panel.add(lblSimilarityMetrics);
		
		JCheckBox chckbxPreferentialAttachment = new JCheckBox("Preferential Attachment (PA)");
		chckbxPreferentialAttachment.setBounds(30, 182, 227, 23);
		panel.add(chckbxPreferentialAttachment);
		
		JCheckBox chckbxCommonNeighborscn = new JCheckBox("Common Neighbors (CN)");
		chckbxCommonNeighborscn.setBounds(30, 217, 227, 23);
		panel.add(chckbxCommonNeighborscn);
		
		JCheckBox chckbxAdamicadaraa = new JCheckBox("Adamic/Adar (AA)");
		chckbxAdamicadaraa.setBounds(269, 182, 210, 23);
		panel.add(chckbxAdamicadaraa);
		
		JCheckBox chckbxJaccardsCoefficientjc = new JCheckBox("Jaccard's Coefficient (JC)");
		chckbxJaccardsCoefficientjc.setBounds(269, 217, 210, 23);
		panel.add(chckbxJaccardsCoefficientjc);
		
		JCheckBox chckbxSelectAll = new JCheckBox("Select All");
		chckbxSelectAll.setBounds(481, 182, 210, 23);
		panel.add(chckbxSelectAll);
		
		JLabel lblForecastingModels = new JLabel("Forecasting Models:");
		lblForecastingModels.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblForecastingModels.setBounds(30, 252, 150, 27);
		panel.add(lblForecastingModels);
		
		JCheckBox chckbxRandomWalk = new JCheckBox(" Random Walk (RW)");
		chckbxRandomWalk.setBounds(30, 291, 227, 23);
		panel.add(chckbxRandomWalk);
		
		JCheckBox chckbxMovingAveragema = new JCheckBox("Moving Average [n = 2] MA(2)");
		chckbxMovingAveragema.setBounds(30, 326, 227, 23);
		panel.add(chckbxMovingAveragema);
		
		JCheckBox chckbxAverage = new JCheckBox("Average (Av)");
		chckbxAverage.setBounds(30, 361, 210, 23);
		panel.add(chckbxAverage);
		
		JCheckBox chckbxLinearRegressionlr = new JCheckBox("Linear Regression (LR)");
		chckbxLinearRegressionlr.setBounds(269, 291, 210, 23);
		panel.add(chckbxLinearRegressionlr);
		
		JCheckBox chckbxLinearExponentialSmoothing = new JCheckBox("Linear Exponential Smoothing (LES)");
		chckbxLinearExponentialSmoothing.setBounds(269, 326, 270, 23);
		panel.add(chckbxLinearExponentialSmoothing);
		
		JCheckBox chckbxSimpleExponentialSmoothing = new JCheckBox("Simple Exponential Smoothing (SES)");
		chckbxSimpleExponentialSmoothing.setBounds(269, 361, 270, 23);
		panel.add(chckbxSimpleExponentialSmoothing);
		
		JCheckBox checkBox = new JCheckBox("Select All");
		checkBox.setBounds(553, 291, 210, 23);
		panel.add(checkBox);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(30, 396, 740, 12);
		panel.add(separator_1);
		
		JLabel lblLearningMethods = new JLabel("Learning Methods:");
		lblLearningMethods.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblLearningMethods.setBounds(30, 420, 150, 27);
		panel.add(lblLearningMethods);
		
		JCheckBox chckbxUnsupervisedLearning = new JCheckBox("Unsupervised Learning");
		chckbxUnsupervisedLearning.setBounds(30, 459, 227, 23);
		panel.add(chckbxUnsupervisedLearning);
		
		JCheckBox chckbxSupervisedLearning = new JCheckBox("Supervised Learning");
		chckbxSupervisedLearning.setBounds(30, 494, 227, 23);
		panel.add(chckbxSupervisedLearning);
		
		JRadioButton rdbtnYes = new JRadioButton("Statistical Analysis");
		rdbtnYes.setBounds(306, 459, 170, 23);
		panel.add(rdbtnYes);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 228, 225));
		panel_1.setBounds(306, 484, 149, 47);
		panel_1.setLayout(null);
		panel.add(panel_1);
		
		JLabel lblRelativeResults = new JLabel("Folds:");
		lblRelativeResults.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblRelativeResults.setBounds(10, 5, 66, 27);
		panel_1.add(lblRelativeResults);		
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(70, 5, 45, 28);
		panel_1.add(textField_1);		
		
		JButton btnGo = new JButton("Go!");
		btnGo.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		btnGo.setBounds(585, 484, 99, 47);
		panel.add(btnGo);
		
	}
}
