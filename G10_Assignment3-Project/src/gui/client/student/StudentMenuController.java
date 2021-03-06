package gui.client.student;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.CommonMethodsHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller that control the Student menu Screen (Form). 
 * @author Tuval Zitelbach,Meitar El-Ezra, Michael Malka
 *
 */
public class StudentMenuController implements Initializable {
	public static StudentMenuController smController;
	// JAVAFX INSTANCES ******************************************************
	@FXML
	private Hyperlink sbStudentLnk;

	@FXML
	private Hyperlink sbLogoutLnk;

	@FXML
	private Label sbWelcomeLbl;

	@FXML
	private Button sbTakeExamBtn;

	@FXML
	private Button sbViewExamResultsBtn;

	@FXML
	private Button sbSettingsBtn;

	@FXML
	private ImageView sbTeacherBgImg;

	// STATIC JAVAFX INSTANCES **********************************************
	private static Hyperlink studentLnk;
	private static Hyperlink logoutLnk;
	private static Label welcomeLbl;
	private static Button takeExamBtn;
	private static Button viewExamResultsBtn;
	private static Button settingsBtn;
	private static ImageView teacherBgImg;


	// STATIC INSTANCES *****************************************************
	protected static String examID;
	protected static String examType;
	protected static String examCode;
	protected static String examiningTeacherID;
	private static StudentEnterCodeController secController;

	// INITIALIZE METHOD ****************************************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		smController = new StudentMenuController();
		ClientUI.mainStage.setWidth(1150);
		ClientUI.mainStage.setHeight(650);
		ClientUI.mainStage.setTitle("Computerized Exam Management System (Student)");
		studentLnk = sbStudentLnk;
		studentLnk.setText(ChatClient.user.getFirstname() + " " + ChatClient.user.getLastname());
		logoutLnk = sbLogoutLnk;
		welcomeLbl = sbWelcomeLbl;
		welcomeLbl.setText("Welcome, " + ChatClient.user.getFirstname());
		takeExamBtn = sbTakeExamBtn;
		viewExamResultsBtn = sbViewExamResultsBtn;
		settingsBtn = sbSettingsBtn;
		teacherBgImg = sbTeacherBgImg;
		teacherBgImg.setImage(new Image("/img_bg.png"));
		teacherBgImg.setPreserveRatio(false);
		teacherBgImg.setFitHeight(620);
		teacherBgImg.setFitWidth(1135);
		ClientUI.mainStage.show();
	}

	// ACTION METHODS *******************************************************
	@FXML
	public void lnkPressLogout(ActionEvent event) throws Exception {
		System.out.println("StudentMenuBar::LnkLogout");
		ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentMenuBar.fxml")));
		StudentMenuBarController.smbController.lnkPressLogout(event);
	}

	@FXML
	public void btnPressSettings(ActionEvent event) throws Exception {
		System.out.println("StudentMenuBar::btnPressSettings/lnkStudentName");
		ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentMenuBar.fxml")));
		StudentMenuBarController.smbController.btnPressSettings(event);
	}

	@FXML
	public void btnPressTakeExam(ActionEvent event) throws Exception {
		System.out.println("StudentMenu ::btnPressTakeExam");
		if(examID==null)//||examType==null||examCode==null||examID.isEmpty()||examType.isEmpty()||examCode.isEmpty())
		{
			CommonMethodsHandler.getInstance().getNewAlert(AlertType.INFORMATION,
					"Error : cannot start any exam","There is no exam running.", "Please try again some other time").showAndWait();
		}
		else {
			ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentMenuBar.fxml")));
			StudentMenuBarController.smbController.btnPressTakeAnExam(event);
		}
	}

	@FXML
	public void btnPressViewExamResults(ActionEvent event) throws Exception {
		System.out.println("StudentMenuBar::btnPressViewExamResults");
		ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentMenuBar.fxml")));
		StudentMenuBarController.smbController.btnPressViewExamResults(event);
	}

	// EXTERNAL USE METHODS *************************************************
	/**
	 * sets all the details of the ready exam.
	 * @param msg, Represent an array of details of ready exam.
	 */
	public void setReadyExam(String[] msg) {
		examID = msg[1];
		examType = msg[2];
		examCode = msg[3];
		examiningTeacherID=msg[4];
		System.out.println(Arrays.toString(msg));
	}

	// EXTERNAL USE METHODS *************************************************
		/**
		 * Return all the details about the ready exam.
		 */
	public String[] getReadyExam() {
		return new String[] {examCode,examType,examID,examiningTeacherID};
	}
	
	// EXTERNAL USE METHODS *************************************************
		/**
		 * Lock the exam for the teacher and for the students.
		 * @param msg, Represent an array of details of ready exam.
		 */
	public void lockExam(String[] msg) {
		System.out.println("examID gotten from echoserver : "+ msg[1]);
		if (examID.equals(msg[1])) {
			try {
				if(examType.equals("C"))
					StudentTakeComputerizedExamController.stceController.setSubmitButtonWhenLockInvoked();
				else// if(examType.equals("M"))
					StudentTakeExamManuallyController.stemController.setSubmitButtonWhenLockInvoked();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
