package application;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public GridPane newgrid() {
		GridPane gp1 = new GridPane();
		gp1.setAlignment(Pos.CENTER);
		gp1.setHgap(10);
		gp1.setVgap(10);
		gp1.setPadding(new Insets(25, 25, 25, 25));
		return gp1;
	}

	public void showAlert(Alert.AlertType at, Window o, String t, String m) {
		Alert alt = new Alert(at);
		alt.setTitle(t);
		alt.setHeaderText(null);
		alt.setContentText(m);
		alt.initOwner(o);
		alt.show();
	}

	public Button button() {
		Button subBut = new Button("Submit");
		subBut.setPrefHeight(40);
		subBut.setDefaultButton(true);
		subBut.setPrefWidth(100);
		return subBut;
	}

	public void start(Stage Stage1) {
		Jdbccontroller connectNow = new Jdbccontroller();
		Connection connectDb = connectNow.getConnection();
		if (connectDb == null)
			return;

		Stage1.setTitle("LOGIN DETAILS");
		GridPane grid1 = newgrid();
		Scene scene1 = new Scene(grid1, 400, 400);

		BackgroundFill bg_fill = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY);
		Background bg = new Background(bg_fill);
		grid1.setBackground(bg);

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		grid1.add(scenetitle, 0, 0, 1, 1);

		Label adminName = new Label("User Name:");
		adminName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid1.add(adminName, 0, 1);
		TextField adminTextField = new TextField();
		grid1.add(adminTextField, 1, 1);

		Label password = new Label("Password:");
		password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid1.add(password, 0, 2);
		PasswordField passwordBox = new PasswordField();
		grid1.add(passwordBox, 1, 2);

		Button btn = new Button("Sign in");
		VBox hbBtn = new VBox(30);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid1.add(hbBtn, 1, 5);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (adminTextField.getText() == "" || passwordBox.getText() == "") {
					showAlert(Alert.AlertType.ERROR, grid1.getScene().getWindow(), "Form Error!",
							"Please fill all the details");
					return;
				} else {
					try {
						String query1 = "insert into login(username,password)values(?,?)";
						PreparedStatement ps1 = connectDb.prepareStatement(query1);
						ps1.setString(1, adminTextField.getText());
						ps1.setString(2, passwordBox.getText());
						ps1.execute();
					} catch (Exception a) {
						a.printStackTrace();
					}
					Stage1.setTitle("HOME PAGE");
					BorderPane rootNode = new BorderPane();
					Scene myScene = new Scene(rootNode, 400, 400);

					MenuBar MB = new MenuBar();
					Menu home = new Menu("HOME");
					MenuItem INSDATA = new MenuItem("BOOK A TICKET");
					MenuItem DELDATA = new MenuItem("CANCEL");
					MenuItem RETDATA = new MenuItem("UPDATE");
					MenuItem LOGOUT = new MenuItem("LOG OUT");

					home.getItems().addAll(INSDATA, new SeparatorMenuItem(), RETDATA, new SeparatorMenuItem(), DELDATA,
							new SeparatorMenuItem(), LOGOUT);
					MB.getMenus().add(home);
					Image ig = new Image("C:\\Users\\parin\\eclipse-workspace\\dbms\\travelagency.png", 400, 400, false,
							false);
					rootNode.getChildren().add(new ImageView(ig));
					rootNode.setTop(MB);
					Stage1.setScene(myScene);
					Stage1.show();

					INSDATA.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Stage Stage2 = new Stage();
							Stage2.setTitle("BOOK A TRIP");
							GridPane gp2 = newgrid();
							Scene scene2 = new Scene(gp2, 400, 400);

							Label hl = new Label("TRIP FORM");
							GridPane.setHalignment(hl, HPos.RIGHT);
							hl.setFont(Font.font("Arial", FontWeight.BOLD, 24));
							gp2.add(hl, 0, 0);

							Label nameLabel = new Label("NAME : ");
							TextField name = new TextField();
							name.setPrefHeight(40);

							Label aadharLabel = new Label("AADHAR NO. : ");
							TextField aadhar = new TextField();
							aadhar.setPrefHeight(40);

							Label fromLabel = new Label("FROM :");
							ObservableList<String> places = FXCollections.observableArrayList("BANGALORE", "CHENNAI",
									"HYDERABAD", "TIRUVANANTHAPURAM");
							ComboBox<String> F = new ComboBox<String>(places);
							ComboBox<String> T = new ComboBox<String>(places);
							F.setValue("");
							T.setValue("");

							Label toLabel = new Label("TO :");

							Label transportlabel = new Label("TRANSPORT :");
							ObservableList<String> transportTypes = FXCollections.observableArrayList("CAR", "TRAIN",
									"BUS", "FLIGHT");
							ComboBox<String> tt = new ComboBox<String>(transportTypes);
							tt.setValue("");

							Label genderlabel = new Label("GENDER : ");
							RadioButton male = new RadioButton("male");
							RadioButton female = new RadioButton("female");
							ToggleGroup gender = new ToggleGroup();
							male.setToggleGroup(gender);
							female.setToggleGroup(gender);

							Label phoneLabel = new Label("PHONE NUMBER : ");
							TextField phone = new TextField();
							phone.setPrefHeight(40);

							Label DOJlabel = new Label("DATE OF JOURNEY : ");
							DatePicker DOJ = new DatePicker();
							DOJ.setDayCellFactory(picker -> new DateCell() {
								@Override
								public void updateItem(LocalDate date, boolean empty) {
									super.updateItem(date, empty);
									LocalDate today = LocalDate.now();
									setDisable(empty || date.isBefore(today)); // Disable dates before today
								}
							});

							gp2.add(nameLabel, 0, 1);
							gp2.add(name, 1, 1);

							gp2.add(aadharLabel, 0, 2);
							gp2.add(aadhar, 1, 2);

							gp2.add(fromLabel, 0, 3);
							gp2.add(F, 1, 3);

							gp2.add(toLabel, 0, 4);
							gp2.add(T, 1, 4);

							gp2.add(transportlabel, 0, 5);
							gp2.add(tt, 1, 5);

							gp2.add(genderlabel, 0, 6);
							gp2.add(male, 1, 6);
							gp2.add(female, 2, 6);

							gp2.add(phone, 1, 7);
							gp2.add(phoneLabel, 0, 7);

							gp2.add(DOJlabel, 0, 8);
							gp2.add(DOJ, 1, 8);

							Button But1 = button();
							gp2.add(But1, 0, 9, 2, 1);
							Stage2.setScene(scene2);
							Stage2.show();
							Stage1.close();

							But1.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {

									if (name.getText().isEmpty() || ((TextField) DOJ.getEditor()).getText().isEmpty()
											|| aadhar.getText().isEmpty() || F.getValue().isEmpty()
											|| T.getValue() == null || tt.getValue().isEmpty()
											|| phone.getText().isEmpty()) {
										showAlert(Alert.AlertType.ERROR, gp2.getScene().getWindow(), "Error!",
												"enter ALL your DETAILS");
										return;
									} else if (!aadhar.getText().matches("\\d{12}")) {
										showAlert(Alert.AlertType.ERROR, gp2.getScene().getWindow(), "Validation Error",
												"Invalid Aadhar number.");
										return;
									} else if (!name.getText().matches("^[a-zA-Z]+$")) {
										showAlert(Alert.AlertType.ERROR, gp2.getScene().getWindow(), "Validation Error",
												"Invad name.");
										return;
									} else if (!phone.getText().matches("\\d{10}")) {
										showAlert(Alert.AlertType.ERROR, gp2.getScene().getWindow(), "Validation Error",
												"Invalid phone number.");
										return;
									} else if (F.getValue().matches(T.getValue())) {
										showAlert(Alert.AlertType.ERROR, gp2.getScene().getWindow(), "Validation Error",
												"ORIGIN AND DESTINATION CANNOT BE SAME.");
										return;
									}

									else {
										try {

											String q1 = "insert into booktrip(name,aadhar,from1,to1,transport,gender,phone,doj)values(?,?,?,?,?,?,?,?)";
											PreparedStatement p1 = connectDb.prepareStatement(q1);
											p1.setString(1, name.getText());
											p1.setString(2, aadhar.getText());
											p1.setString(3, F.getValue());
											p1.setString(4, T.getValue());
											p1.setString(5, tt.getValue());
											p1.setString(6, ((RadioButton) gender.getSelectedToggle()).getText()); // ((RadioButton)gender.getSelectedToggle()).getText());
											p1.setString(7, phone.getText());
											p1.setString(8, ((TextField) DOJ.getEditor()).getText());
											p1.execute();

										} catch (Exception a) {
											showAlert(Alert.AlertType.CONFIRMATION, gp2.getScene().getWindow(),
													"Unsuccessful!", "FAILED TO BOOK");
											a.printStackTrace();
											return;
										}
										showAlert(Alert.AlertType.CONFIRMATION, gp2.getScene().getWindow(),
												"Successful!", "TICKET BOOKED");

									}
								}
							});
						}
					});

					LOGOUT.setOnAction(ae -> Stage1.close());

					DELDATA.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Stage Stage3 = new Stage();
							Stage3.setTitle("cancel a ticket");
							GridPane gp3 = newgrid();
							Scene scene3 = new Scene(gp3, 800, 550);

							Label h3 = new Label("Enter the details to cancel the trip.");
							GridPane.setHalignment(h3, HPos.RIGHT);
							h3.setFont(Font.font("Arial", FontWeight.BOLD, 24));
							gp3.add(h3, 0, 0);

							Label aadharLabel = new Label("AADHAR NO. : ");
							TextField aadhar = new TextField();
							aadhar.setPrefHeight(40);

							Label fromLabel = new Label("FROM :");
							ObservableList<String> places = FXCollections.observableArrayList("BANGALORE", "CHENNAI",
									"HYDERABAD", "TIRUVANANTHAPURAM");
							ComboBox<String> F = new ComboBox<String>(places);
							ComboBox<String> T = new ComboBox<String>(places);
							F.setValue("");
							T.setValue("");

							Label toLabel = new Label("TO :");

							Label DOJlabel = new Label("DATE OF JOURNEY : ");
							DatePicker DOJ = new DatePicker();
							DOJ.setDayCellFactory(picker -> new DateCell() {
								@Override
								public void updateItem(LocalDate date, boolean empty) {
									super.updateItem(date, empty);
									LocalDate today = LocalDate.now();
									setDisable(empty || date.isBefore(today)); // Disable dates before today
								}
							});

							gp3.add(aadharLabel, 0, 2);
							gp3.add(aadhar, 1, 2);

							gp3.add(fromLabel, 0, 3);
							gp3.add(F, 1, 3);

							gp3.add(toLabel, 0, 4);
							gp3.add(T, 1, 4);

							gp3.add(DOJlabel, 0, 5);
							gp3.add(DOJ, 1, 5);

							Button But3 = button();
							gp3.add(But3, 0, 9, 2, 1);
							// gp3.setHalignment(But3, HPos.CENTER);
							Stage3.setScene(scene3);
							Stage3.show();
							Stage1.close();

							But3.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {

									if (aadhar.getText().isEmpty() || F.getValue().isEmpty() || T.getValue().isEmpty()
											|| ((TextField) DOJ.getEditor()).getText().isEmpty()) {
										showAlert(Alert.AlertType.ERROR, gp3.getScene().getWindow(), "Error!",
												" enter  ALL your DETAILS");
										return;
									} else if (!aadhar.getText().matches("\\d{12}")) {
										showAlert(Alert.AlertType.ERROR, gp3.getScene().getWindow(), "Validation Error",
												"Invalid Aadhar number.");
										return;
									} else if (F.getValue().matches(T.getValue())) {
										showAlert(Alert.AlertType.ERROR, gp3.getScene().getWindow(), "Validation Error",
												"ORIGIN AND DESTINATION CANNOT BE SAME.");
										return;
									} else {
										try {
											String q12 = "SELECT COUNT(*) FROM booktrip WHERE from1 = ? AND to1=? AND aadhar = ? AND doj=?";
											PreparedStatement preparedStatement = connectDb.prepareStatement(q12);
											preparedStatement.setString(1, F.getValue());
											preparedStatement.setString(2, T.getValue());
											preparedStatement.setString(3, aadhar.getText());
											preparedStatement.setString(4, ((TextField) DOJ.getEditor()).getText());

											ResultSet resultSet = preparedStatement.executeQuery();
											resultSet.next(); // Move the cursor to the first row

											int count = resultSet.getInt(1);

											if (count > 0) {
												String q2 = "delete from booktrip where aadhar=? and from1=? and to1=? and doj=?";
												PreparedStatement p2 = connectDb.prepareStatement(q2);
												p2.setString(1, aadhar.getText());
												p2.setString(2, F.getValue());
												p2.setString(3, T.getValue());
												p2.setString(4, ((TextField) DOJ.getEditor()).getText());
												p2.execute();
												showAlert(Alert.AlertType.CONFIRMATION, gp3.getScene().getWindow(),
														"deletion Successful!", "TICKET CANCELED");
											}
											else {
												showAlert(Alert.AlertType.CONFIRMATION, gp3.getScene().getWindow(),
														"error!", "ticket not booked CANCELED");
												
											}

										} catch (Exception a) {
											a.printStackTrace();
											showAlert(Alert.AlertType.CONFIRMATION, gp3.getScene().getWindow(),
													"deletion unSuccessful!",
													"ticket not canceled as ticket with such details not booked");
										}
									}
								}
							});
						}
					});

					RETDATA.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Stage Stage4 = new Stage();
							Stage4.setTitle("show");
							GridPane gp4 = newgrid();
							Scene scene4 = new Scene(gp4, 800, 800);

							Label h4 = new Label("enter the aadhar number to see the details.");
							GridPane.setHalignment(h4, HPos.RIGHT);
							h4.setFont(Font.font("Arial", FontWeight.BOLD, 24));
							gp4.add(h4, 0, 0);

							Label aadharLabel = new Label("AADHAR NO: ");
							TextField aadhar = new TextField();
							aadhar.setPrefHeight(40);
							gp4.add(aadharLabel, 0, 2);
							gp4.add(aadhar, 1, 2);

							Label fromLabel = new Label("from :");
							Label toLabel = new Label("to :");
							Label transportlabel = new Label("TRANSPORT :");
							Label DOJlabel = new Label("DATE OF JOURNEY : ");
							Label namelabel = new Label("NAME : ");
							Label genderlabel = new Label("GENDER : ");
							Label phonelabel = new Label("Phone : ");

							TextField from = new TextField();
							from.setPrefHeight(40);

							TextField to = new TextField();
							to.setPrefHeight(40);

							TextField transport = new TextField();
							transport.setPrefHeight(40);

							TextField DOJ = new TextField();
							DOJ.setPrefHeight(40);

							TextField name = new TextField();
							name.setPrefHeight(40);

							TextField gender = new TextField();
							gender.setPrefHeight(40);

							TextField phone = new TextField();
							phone.setPrefHeight(40);

							gp4.add(fromLabel, 0, 3);
							gp4.add(from, 1, 3);

							gp4.add(toLabel, 0, 4);
							gp4.add(to, 1, 4);

							gp4.add(transportlabel, 0, 5);
							gp4.add(transport, 1, 5);

							gp4.add(DOJlabel, 0, 6);
							gp4.add(DOJ, 1, 6);

							gp4.add(namelabel, 0, 7);
							gp4.add(name, 1, 7);

							gp4.add(genderlabel, 0, 8);
							gp4.add(gender, 1, 8);

							gp4.add(phonelabel, 0, 9);
							gp4.add(phone, 1, 9);

							Button But4 = button();
							gp4.add(But4, 0, 10, 2, 1);
							// gp4.setHalignment(But4, HPos.CENTER);
							But4.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {

									if (!aadhar.getText().matches("\\d{12}")) {
										showAlert(Alert.AlertType.ERROR, gp4.getScene().getWindow(), "Validation Error",
												"Invalid Aadhar number.");
										return;
									} else {
										try {
											String q10 = "SELECT count(*) FROM booktrip WHERE aadhar=?";
											String q11 = "SELECT name,FROM1,to1,transport,gender,phone,doj FROM booktrip WHERE aadhar=?";
											PreparedStatement preparedStatement = connectDb.prepareStatement(q10);
											preparedStatement.setString(1, aadhar.getText());
											ResultSet resultSet = preparedStatement.executeQuery();
											resultSet.next(); // Move the cursor to the first row
											int count = resultSet.getInt(1);

											if (count > 0) {
												try {
													PreparedStatement pt = connectDb.prepareStatement(q11);
													pt.setString(1, aadhar.getText());
													ResultSet resultSet1 = pt.executeQuery();

													if (resultSet1.next()) {
														String namestr = resultSet1.getString("name");
														String fromstr = resultSet1.getString("FROM1");
														String tostr = resultSet1.getString("to1");
														String transstr = resultSet1.getString("transport");
														String genderstr = resultSet1.getString("gender");
														String phstr = resultSet1.getString("phone");
														String jourstr = resultSet1.getString("doj");

														name.setText(namestr);
														from.setText(fromstr);
														to.setText(tostr);
														gender.setText(genderstr);
														transport.setText(transstr);
														DOJ.setText(jourstr);
														phone.setText(phstr);

													}
													
												} catch (Exception a) {
													a.printStackTrace();

												}
											}
											else {
												showAlert(Alert.AlertType.CONFIRMATION, gp4.getScene().getWindow(),
														"ERROR!","NO TICKET BOOKED FOR THIS NUMBER");
												
											}

										} catch (Exception a) {
											a.printStackTrace();
										}
									}
								}
							});
							Stage4.setScene(scene4);
							Stage4.show();
							Stage1.close();
						}
					});
				} // ELSE CLOSING
			}// EVENT HANDLER CLOSING
		});// btn.set on action closing
		Stage1.setScene(scene1);
		Stage1.show();
	}// void start closing
}// class body closing
