import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class GUI {

	private JFrame frmTeamScheduler;
	private JButton btnGo;
	private JTextField txtPlayerInfocsv;
	private JLabel lblEnterTheName;
	private JLabel lblPressGoWhen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmTeamScheduler.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTeamScheduler = new JFrame();
		frmTeamScheduler.setResizable(false);
		frmTeamScheduler.setTitle("Team Scheduler");
		frmTeamScheduler.setBounds(100, 100, 450, 377);
		frmTeamScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTeamScheduler.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("450px:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("278px"),}));
		
		lblEnterTheName = new JLabel(" Enter the name of the file with player info (don't forget .csv)");
		frmTeamScheduler.getContentPane().add(lblEnterTheName, "1, 2");
		
		txtPlayerInfocsv = new JTextField();
		txtPlayerInfocsv.setText("Player Info.csv");
		frmTeamScheduler.getContentPane().add(txtPlayerInfocsv, "1, 4, fill, default");
		txtPlayerInfocsv.setColumns(10);
		
		lblPressGoWhen = new JLabel(" Press Go when ready");
		frmTeamScheduler.getContentPane().add(lblPressGoWhen, "1, 6");
		
		btnGo = new JButton("Go");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String source = txtPlayerInfocsv.getText();
					InfoGetter.operations(source);
					JOptionPane.showMessageDialog(null,"Check the output folder");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"Invalid file");
					e1.printStackTrace();
				}
				
			}
		});
		btnGo.setToolTipText("Magic is about to happen");
		frmTeamScheduler.getContentPane().add(btnGo, "1, 7, fill, fill");
	}

}
