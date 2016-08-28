package searcheng;

/**
* @author David Howick
* @version 8 update 101 (build 1.8.0_101-b13)
*/

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import java.awt.event.ActionEvent;

public class Display {

	private JFrame frmFilesKeywordsSearch;
	private JTextField textField_1;
	private JFileChooser fileChooser;
	int returnVal;
	protected LinkedList<String> files = new LinkedList<>();
	JTextArea textArea;
	boolean exists;
	ArrayList<String> arrayList = new ArrayList<>();
	TreeSet<String> document = new TreeSet<>();
	int counter = 0;
	int count = 0;
	int colSize = 2;
	Scanner in = new Scanner(System.in);
	protected SortRanking ranking;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			/*
			 * @throws my exception
			 */
			public void run() {
				try {
					Display window = new Display();
					window.frmFilesKeywordsSearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Display() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFilesKeywordsSearch = new JFrame();
		frmFilesKeywordsSearch.setTitle("Files: Keywords Search");
		frmFilesKeywordsSearch.setBounds(100, 100, 450, 308);
		frmFilesKeywordsSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilesKeywordsSearch.getContentPane().setLayout(null);
		fileChooser = new JFileChooser();

		JLabel lblNewLabel = new JLabel("Add files ");
		lblNewLabel.setBounds(28, 40, 89, 14);
		frmFilesKeywordsSearch.getContentPane().add(lblNewLabel);

		/**
		 * JTextField for displaying the added files and keyword hits results
		 */
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(28, 133, 367, 90);
		frmFilesKeywordsSearch.getContentPane().add(textArea_1);

		/**
		 * Add button adds files to be searched
		 */
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// arg0.getSource();
				fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					String fileName = file.getAbsolutePath();
					files.add(fileName);
					arrayList.add(fileName);
					textArea_1.setText(" ");
					for (Object obj : arrayList) {
						textArea_1.append(obj.toString() + "\n");
					}
				}
			}
		});
		btnNewButton.setBounds(127, 36, 89, 23);
		frmFilesKeywordsSearch.getContentPane().add(btnNewButton);

		JSeparator separator = new JSeparator();
		separator.setBounds(28, 95, 367, 2);
		frmFilesKeywordsSearch.getContentPane().add(separator);

		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		textField_1.setBounds(127, 64, 268, 20);
		frmFilesKeywordsSearch.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Enter Keywords");
		lblNewLabel_1.setBounds(28, 66, 101, 18);
		frmFilesKeywordsSearch.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("The added files which will be searched");
		lblNewLabel_2.setBounds(28, 108, 359, 14);
		frmFilesKeywordsSearch.getContentPane().add(lblNewLabel_2);

		/**
		 * Once the search button is pressed, the files are searched through,
		 * one by one, for keywords.
		 * 
		 * @throws IOException
		 */
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_2.setText("The files with the most hits in ascending order are: ");
				SortRanking ranking = new SortRanking(files.size(), colSize);
				String keywords = textField_1.getText().toLowerCase();
				String[] separatedKeywords = keywords.split(" ");
				Scanner s = null;
				PrintWriter out = null;
				for (int i = 0; i <= files.size() - 1; i++) {
					try {
						s = new Scanner(new BufferedReader(new FileReader(files.get(i))));
						while (s.hasNext()) {
							// read the document tokens into a binary tree data
							// structure
							document.add(s.next().replaceAll("[^a-zA-Z]", "").toLowerCase());
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} finally {
						if (s != null) {
							s.close();
						}
						if (out != null) {
							out.close();
						}
						if (in != null) {
							in.close();
						}
					}
					// Separates the keywords line into individual word tokens
					for (int p = 0; p <= separatedKeywords.length - 1; p++) {
						exists = document.contains(separatedKeywords[p]);
						if (exists == true) {
							count++; // increments each time a keyword is found
										// in the document
						}
					}
					// stores the keyword hits count in the file and the file
					// name, in the 2D array
					ranking.add(Integer.toString(count), files.get(counter));
					counter++; // increments through the linked linked list (to
								// next text file)
					document.clear();
					count = 0;
				}
				ranking.partition(ranking.getArray(), 0, ranking.getArray().length - 1);
				// StringBuilder used to to concatenate the hits count and the
				// file stored in the 2D array
				StringBuilder sb = new StringBuilder();
				for (int i = ranking.getArray().length - 1; i >= 0; i--) {
					for (int j = 0; j < 2; j++) {
						sb.append(String.valueOf(ranking.getArray()[i][j]));
						sb.append(' ');
					}
					sb.append('\n');
				}
				textArea_1.setText(sb.toString());
			}
		});
		btnSearch.setBounds(28, 234, 89, 23);
		frmFilesKeywordsSearch.getContentPane().add(btnSearch);

		/**
		 * Clear button resets variables to initial state so, the application
		 * can be used again
		 */
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				arrayList.clear();
				files.clear();
				System.out.println(files.size() + " the file size is");
				textField_1.setText("");
				counter = 0;
				files.clear();
				lblNewLabel_2.setText("The added files which will be searched");
			}
		});
		btnClear.setBounds(127, 234, 89, 23);
		frmFilesKeywordsSearch.getContentPane().add(btnClear);
	}
}
