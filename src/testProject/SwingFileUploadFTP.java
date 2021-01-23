package testProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class SwingFileUploadFTP implements ActionListener, PropertyChangeListener  {


	private JFrame frame = new JFrame("Swing File Upload to FTP Server");
	private JPanel panel = new JPanel();
	
	private JLabel labelHost = new JLabel("Host:");
	private JLabel labelPort = new JLabel("Port:");
	private JLabel labelUsername = new JLabel("Username:");
	private JLabel labelPassword = new JLabel("Password:");
	private JLabel labelUploadPath = new JLabel("Upload path:");
	private JLabel labelChooseFile = new JLabel("Choose a file:");
	
	private JTextField fieldHost = new JTextField(40);
	private JTextField fieldPort = new JTextField(5);
	private JTextField fieldUsername = new JTextField(30);
	private JPasswordField fieldPassword = new JPasswordField(30);
	private JTextField fieldUploadPath = new JTextField(30);
	private JTextField fieldChooseFile = new JTextField(50);
	
	private JFileChooser fileChooser = new JFileChooser();
	private JButton buttonUpload = new JButton("Upload");
	private JButton buttonBrowse = new JButton("Browse");
	private JLabel labelProgress = new JLabel("Progress:");
	private JProgressBar progressBar = new JProgressBar(0, 100);	
	

	File file;
	Scanner fileIn;
	int response;
	
	public SwingFileUploadFTP() {
		
			
		// response = fileChooser.showOpenDialog(null);
		buttonUpload.addActionListener(new ActionListener() {
			
			@Override
		   public void actionPerformed(ActionEvent event) {
				
				buttonUploadActionPerformed(event);
			}
			
		});
		
		frame.setSize(600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);   
		
		panel.setLayout(null);
		
		labelHost.setBounds(10, 20, 80, 25);
		panel.add(labelHost);		
		fieldHost.setBounds(120, 20, 400, 25);
		panel.add(fieldHost);
		
		labelPort.setBounds(10, 60, 80, 25);
		panel.add(labelPort);
		fieldPort.setBounds(120, 60, 400, 25);
		panel.add(fieldPort);
		
		labelUsername.setBounds(10, 100, 80, 25);
		panel.add(labelUsername);
		fieldUsername.setBounds(120, 100, 400, 25);
		panel.add(fieldUsername);
		
		labelPassword.setBounds(10, 140, 80, 25);
		panel.add(labelPassword);
		fieldPassword.setBounds(120, 140, 400, 25);
		panel.add(fieldPassword);
		
		labelUploadPath.setBounds(10, 180, 80, 25);
		panel.add(labelUploadPath);
		fieldUploadPath.setBounds(120, 180, 400, 25);
		panel.add(fieldUploadPath);
		
		//panel.add(fileChooser);
		//response = fileChooser.showOpenDialog(null);
		
		labelChooseFile.setBounds(20, 220, 80, 25);
		panel.add(labelChooseFile);
		fieldChooseFile.setBounds(120, 220, 280, 25);
		panel.add(fieldChooseFile);
		
		buttonBrowse.setBounds(405, 220, 80, 25);
		buttonBrowse.addActionListener(this);
		panel.add(buttonBrowse);
		
		buttonUpload.setBounds(240, 260, 80, 25);		
		panel.add(buttonUpload);
		
		labelProgress.setBounds(10, 320, 80, 25);
		panel.add(labelProgress);
		
		progressBar.setBounds(120, 320, 400, 25);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
		
		frame.setVisible(true);

	}
	

	

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = fileChooser.showOpenDialog(null);
		
		if (response == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			
			if(file.isFile()) {
				fieldChooseFile.setText(file.getAbsolutePath());
				System.out.println(file.getAbsolutePath());
			} else {
				System.out.println("That was not a file! ");
			}
			
		}
		
	}

	
	
	/**
     * handle click event of the Upload button
     */
    private void buttonUploadActionPerformed(ActionEvent event) {
        String host = fieldHost.getText();
        int port = Integer.parseInt(fieldPort.getText());
        String username = fieldUsername.getText();
        String password = new String(fieldPassword.getPassword());
        String uploadPath = fieldUploadPath.getText();
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
 
        File uploadFile = new File(filePath);
        progressBar.setValue(0);
        UploadTask task = new UploadTask(host, port, username, password,
                uploadPath, uploadFile);
        task.addPropertyChangeListener(this);
        task.execute();
    }
    
    
  /**
   * Update the progress bar's state whenever the progress of upload changes.
   */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
            // set look and feel to system dependent
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingFileUploadFTP();				
			}			
		});		
	}
	




} // class
