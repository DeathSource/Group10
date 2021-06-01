package gui.client.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class StudentEnterCodeController implements Initializable {

	// JAVAFX INSTNCES ******************************************************
	@FXML
	private Button sbStartExamBtn;

	@FXML
	private TextField sbCodeTf;

	// STATIC JAVAFX INSTANCES **********************************************
	private static Button startExamBtn;
	private static TextField codeTf;

	// STATIC  INSTANCES ****************************************************
	private static String code;
	private static String examType;

	// INITIALIZE METHOD ****************************************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startExamBtn = sbStartExamBtn;
		codeTf = sbCodeTf;
	}

	// ACTION METHODS *******************************************************
	@FXML
	void btnPressStartExam(ActionEvent event) throws IOException {
		// go to [Computerized / Manual] exam screen
		System.out.println("StudentEnterCode::btnPressStartExam");

		setCode("","");
		System.out.println(code);
		if (code.equals(codeTf.getText()))
		{
			if(examType.equals("computerized"))
				ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentTakeComputerizedExam.fxml")));
			if(examType.equals("manual"))
				ClientUI.mainScene.setRoot(FXMLLoader.load(getClass().getResource("/gui/client/student/StudentTakeExamManually.fxml")));
		}

		// if the student didn't insert the correct code
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initStyle(StageStyle.UTILITY);
			alert.setTitle("Code inserting failed");
			alert.setHeaderText("the code you inserted is wrong");
			alert.setContentText("Please try again");
			alert.showAndWait();
			codeTf.setText("");
		}


		//*******************************************************************
	}

	// EXTERNAL USE METHODS *************************************************
	/**
	 * sets code and exam's type of current exam
	 * @param codeFromTeacher the code of the exam taken
	 * @param et the type of the exam (manual or computerized)
	 * TODO get current code and exam-type from teacher to all active students
	 */
	public void setCode(String codeFromTeacher,String et) {
		if(!codeFromTeacher.equals("") && codeFromTeacher!=null)
			code=codeFromTeacher;
		else code="1111"; //default exam code for now
		if(!et.equals("") && et!=null)
			examType=et;
		else examType="computerized"; //default exam type for now
	}

}
