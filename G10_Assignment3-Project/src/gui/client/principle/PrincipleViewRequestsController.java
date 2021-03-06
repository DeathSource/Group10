package gui.client.principle;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.exam.Request;
/**
 * A controller class which controls the "View Requests" option screen of the Principle user.
 * @author Michael Malka, Tuval Zitelbach & Meitar El Ezra
 */
public class PrincipleViewRequestsController implements Initializable {

	// JAVAFX INSTANCES ******************************************************
	@FXML
	private TableView<Request> sbRequestsTv;

	@FXML
	private TableColumn<Request, String> sbTeacherTc;

	@FXML
	private TableColumn<Request, String> sbExamIdTc;

	@FXML
	private TableColumn<Request, String> sbOriginalAllocatedTimeTc;

	@FXML
	private TableColumn<Request, String> sbNewAllocatedTimeTc;

	@FXML
	private Button sbAcceptRequestBtn;

	@FXML
	private Button sbDeclineRequestBtn;

	// STATIC JAVAFX INSTANCES **********************************************
	private static TableView<Request> requestsTv;
	private static TableColumn<Request, String> teacherTc;
	private static TableColumn<Request, String> examIdTc;
	private static TableColumn<Request, String> origTimeTc;
	private static TableColumn<Request, String> newTimeTc;
	private static Button acceptRequestBtn;
	private static Button declineRequestBtn;
	private static ObservableList<Request> requestList = FXCollections.observableArrayList();
	public static PrincipleViewRequestsController pvrController;
	private static String teacherUserName;

	// INITIALIZE METHOD ****************************************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pvrController = new PrincipleViewRequestsController();
		setUpTableProperties();
		setUpButtonsProperties();
		ClientUI.chat.accept(new String[] { "sbViewRequests"});
	}

	// ACTION METHODS *******************************************************
	@FXML
	void btnPressAcceptRequest(ActionEvent event)
	{
		Request request = requestsTv.getSelectionModel().getSelectedItem();
		System.out.println("PrincipleViewRequests::btnPressAcceptRequest");
		deleteRequest();
		ClientUI.chat.accept(new Object[] { "SendMessageRequestAccepted",request});
	}

	@FXML
	void btnPressDeclineRequest(ActionEvent event)
	{
		System.out.println("PrincipleViewRequests::btnPressAcceptRequest");
		deleteRequest();
		ClientUI.chat.accept(new String[] { "SendMessageRequestDeclined",teacherUserName});

	}

	// EXTERNAL METHODS *******************************************************
	/**
	 * Sets The Table Properties
	 */
	void setUpTableProperties()
	{
		requestsTv = sbRequestsTv;
		sbTeacherTc.setCellValueFactory(new PropertyValueFactory<Request, String>("usernameT"));
		sbExamIdTc.setCellValueFactory(new PropertyValueFactory<Request, String>("examID"));
		sbOriginalAllocatedTimeTc.setCellValueFactory(new PropertyValueFactory<Request, String>("allocatedTime"));
		sbNewAllocatedTimeTc.setCellValueFactory(new PropertyValueFactory<Request, String>("newAllocatedTime"));
	}
	//======================================================================================================================
	/**
	 * Sets The Buttons Properties
	 */
	void setUpButtonsProperties()
	{
		acceptRequestBtn = sbAcceptRequestBtn;
		declineRequestBtn = sbDeclineRequestBtn;
	}
	//======================================================================================================================
	/**
	 * This method puts the tuples of the requests from the DB into the tableView
	 * @param  List<Request> of the request in the table from DB
	 */
	public void fillRequstTable(Request requestFromDataBase)
	{
		requestList.clear();
		requestList.addAll(requestFromDataBase);
		try {
			requestsTv.setItems(requestList);
		} catch (IllegalStateException e) {}
	}

	/**
	 * calls for a query to delete tupple from Requests table
	 */
	public void deleteRequest()
	{
		ClientUI.chat.accept(new String[] { "sbDeleteRequests"});
	}

	/**
	 *this function sets the requesting teacher's ID
	 * @param usernameT the teacher's ID (username)
	 */
	public void setTeacherUserName(String usernameT)
	{
		teacherUserName = usernameT;
	}
}
