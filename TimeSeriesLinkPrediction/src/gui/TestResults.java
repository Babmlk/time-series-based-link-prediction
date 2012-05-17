package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class TestResults extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestResults frame = new TestResults();
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
	public TestResults() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 40, 780, 530);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Unsupervised Learning", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 24, 747, 210);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Absolute Results:");
		lblNewLabel.setBounds(40, 30, 125, 19);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 80, 600, 85);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBorder(null);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"PA", "-", "-", "-", "-", "-", "-", "-"},
				{"CN", "-", "-", "-", "-", "-", "-", "-"},
				{"AA", "-", "-", "-", "-", "-", "-", "-"},
				{"JC", "-", "-", "-", "-", "-", "-", "-"},
			},
			new String[] {
				"", "RW", "MA(2)", "Av", "LR", "LES", "SES", "Traditional"
			}
		));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(74, 60, 600, 105);
		scrollPane_2.setVisible(false);
		panel_1.add(scrollPane_2);
		
		JTextArea textArea = new JTextArea();
		scrollPane_2.setViewportView(textArea);
		
		JButton btnShowTable = new JButton("Show Table");
		btnShowTable.setBounds(73, 177, 117, 29);
		panel_1.add(btnShowTable);
		
		JButton btnShowLatex = new JButton("Show LaTeX");
		btnShowLatex.setBounds(202, 177, 117, 29);
		panel_1.add(btnShowLatex);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 246, 747, 12);
		panel.add(separator);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(6, 270, 747, 210);
		panel.add(panel_2);
		
		JLabel lblRelativeResults = new JLabel("Relative Results:");
		lblRelativeResults.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblRelativeResults.setBounds(40, 30, 125, 19);
		panel_2.add(lblRelativeResults);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(74, 80, 600, 85);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		
		table_1.setColumnSelectionAllowed(true);
		table_1.setCellSelectionEnabled(true);
		scrollPane_1.setViewportView(table_1);
		table_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table_1.setBorder(null);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"PA", "-", "-", "-", "-", "-", "-", "-"},
				{"CN", "-", "-", "-", "-", "-", "-", "-"},
				{"AA", "-", "-", "-", "-", "-", "-", "-"},
				{"JC", "-", "-", "-", "-", "-", "-", null},
			},
			new String[] {
				"", "RW", "MA(2)", "Av", "LR", "LES", "SES", "Traditional"
			}
		));
		
		JButton btnShowTable_1 = new JButton("Show Table");
		btnShowTable_1.setBounds(74, 177, 117, 29);
		panel_2.add(btnShowTable_1);
		
		JButton btnShowLatex_1 = new JButton("Show LaTeX");
		btnShowLatex_1.setBounds(203, 177, 117, 29);
		panel_2.add(btnShowLatex_1);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(74, 60, 600, 105);
		scrollPane_3.setVisible(false);
		panel_2.add(scrollPane_3);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_3.setViewportView(textArea_1);
		
		JButton btnBack = new JButton("back");
		btnBack.setBounds(18, 6, 75, 29);
		contentPane.add(btnBack);
	}
}
