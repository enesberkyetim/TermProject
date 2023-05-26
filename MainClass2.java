import javafx.event.EventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainClass2 extends Application {
	
	
	static int lvlCounter = 1;
	Label lblNextLvl = new Label("   Next Level>>");
	Label lblDrive = new Label("DRIVE ");
	static int score = 0;
	Label lblScore = new Label("Score: " + score);
	int currentCityId = 0;
	int selectedCityId = 0;
	GridPane currentGP;
	StackPane currentSPOut;
	
	
	
	
	
	// ArrayLists of objects in the level files
	ArrayList<Passenger> passengerList = new ArrayList<>();
	ArrayList<FixedCell> fixedCellList = new ArrayList<>();
	ArrayList<Vehicle> vehicleList = new ArrayList<>();
	ArrayList<City> cityList = new ArrayList<>();
	
	
	
	// To change level screens
	Stage stage;
	// Scenes for each level
	Scene scene;
	Scene scene2;
	Scene scene3;
	Scene scene4;
	Scene scene5;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Regulate scene levels according to stage
		stage = primaryStage;
		
		

		// Event Handler for Next Level label
		NextLevelHandler hndNL = new NextLevelHandler();
		lblNextLvl.setOnMouseClicked(hndNL);
		
		// Event Handler for Drive label
		DriveHandler hndDrive = new DriveHandler();
		lblDrive.setOnMouseClicked(hndDrive);
		
		// Set the resizable option of the stage
		stage.setResizable(true);

		if (lvlCounter == 1) {
			stage.setScene(levelPattern("levels\\level1.txt", scene));
			stage.show();
		} else if (lvlCounter == 2) {
			stage.setScene(levelPattern("levels\\level2.txt", scene2));
			stage.show();
		} else if (lvlCounter == 3) {
			stage.setScene(levelPattern("levels\\level3.txt", scene3));
			stage.show();
		} else if (lvlCounter == 4) {
			stage.setScene(levelPattern("levels\\level4.txt", scene4));
			stage.show();
		} else if (lvlCounter == 5) {
			stage.setScene(levelPattern("levels\\level5.txt", scene5));
			stage.show();
		}
		
		stage.show();
	}

	public Scene levelPattern(String levelScript, Scene scene) throws FileNotFoundException {

		BorderPane mainBP = new BorderPane();
		scene = new Scene(mainBP, 750, 970);
		
		// Clear the fixed cell arrayList to pass the new level
		fixedCellList.clear();
		
		// Clear the vehicle arrayList to pass the new level
		vehicleList.clear();
		
		// Clear the city arrayList to pass the new level
		cityList.clear();
		
		// Clear the passenger arrayList to pass the new level
		passengerList.clear();
		
		// Center Grid Pane
		
		StackPane spOutCenter = new StackPane();
		
		GridPane gpCenter = new GridPane();
		gpCenter.setVgap(0);
		gpCenter.setHgap(0);

		// Regulate the 10x10 grids in pane
		for (int i = 0; i < 100; i++) {
			Rectangle rectMid = new Rectangle(72, 72);
			rectMid.setDisable(false);
			rectMid.setFill(Color.TRANSPARENT);
			rectMid.setStroke(Color.BLACK);
			rectMid.setStrokeWidth(0.1);

			StackPane spCenter = new StackPane();
			spCenter.getChildren().addAll(rectMid, new Label(String.valueOf(i + 1)));
			spCenter.setAlignment(Pos.CENTER);

			gpCenter.add(spCenter, i % 10, i / 10);
			gpCenter.setId(String.valueOf(i + 1));
			//gpCenter.setDisable(true);
			gpCenter.setAlignment(Pos.CENTER);
			gpCenter.setVgap(0);
			gpCenter.setHgap(0);
		}
		spOutCenter.getChildren().add(gpCenter);
		spOutCenter.setAlignment(Pos.CENTER);
		mainBP.setCenter(spOutCenter);
		currentGP = gpCenter;
		currentSPOut = spOutCenter;
		
		// stack pane for Bottom Part 
		StackPane spBottom = new StackPane();
		
		// Rectangle for bottom part
		Rectangle rectBottom = new Rectangle(scene.getWidth() - 2, scene.getHeight() / 5);
		rectBottom.setFill(Color.TRANSPARENT);
		rectBottom.setStroke(Color.BLACK);
		rectBottom.setStrokeWidth(2);

		//// FlowPane for Drive
		FlowPane fpBottom = new FlowPane();
		fpBottom.setAlignment(Pos.CENTER_RIGHT);
		lblDrive.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		fpBottom.getChildren().add(lblDrive);
		
		// Coordinate the Drive Label Position
		VBox vboxDriveLbl = new VBox();
		vboxDriveLbl.setAlignment(Pos.TOP_RIGHT);
		vboxDriveLbl.setSpacing(60);
		vboxDriveLbl.getChildren().addAll(new Label(""),lblDrive);
		

		// FlowPane for the instructions
		VBox vBoxIns = new VBox();
		vBoxIns.setAlignment(Pos.TOP_LEFT);

		// Scanner for the instructions
		
		File file = new File(levelScript);
		Scanner outputOfIns = new Scanner(file);
		
		
		//
		///
		////
		/////
		// Take the data from the level text documents
		while (outputOfIns.hasNext()) {
			String x = outputOfIns.nextLine();
			//vBoxIns.getChildren().add(new Label(x));
			
			// Finding fixed cells part 
			if(x.startsWith("Fixed,")) {
				
				
				
				String fixed = x.substring("Fixed,".length(), x.length());
				//System.out.println(fixed);

				int fixedInt = Integer.valueOf(fixed);
				int digitNo = 0;

				int col = 0;
				int row = 0;

				int fixedIntTurn = fixedInt;
				// Find the number of digits

				while (fixedIntTurn > 0) {
					fixedIntTurn = fixedIntTurn / 10;
					digitNo++;
				}
				
				// Define the column and row of the fixed cells 
				if (digitNo == 3) {
					col = 9;
					row = 9;
				} else if (digitNo == 2) {
					col = (fixedInt - 1) % 10;
					row = (fixedInt - 1) / 10;
				} else {
					row = 0;
					col = fixedInt;
				}
				
				FixedCell fixCell = new FixedCell(row, col);
				((StackPane)(gpCenter.getChildren().get((row * 10) + col))).getChildren().add(fixCell.getLogo());
				fixedCellList.add(fixCell);
				
			// Finding vehicle properties
			}else if(x.startsWith("Vehicle,")) {
				
				char c;
				String data = "";
				int counter = 0;
				int cityIdForVehicle = 0;
				int passengerCapacity = 0;

				for (int i = "Vehicle,".length(); i < x.length(); i++) {

					c = x.charAt(i);

					if (c != ',') {
						data += c;
					} else if (c == ',') {
						counter++;
						cityIdForVehicle = Integer.valueOf(data);
						// System.out.println("City Id for Vehicle is "+ cityIdForVehicle);
						data = "";
						continue;
					} else if (c != ',' && counter == 1) {
						data += c;
					}
				}

				passengerCapacity = Integer.valueOf(data);
				// System.out.println("Passenger capacity is "+passengerCapacity);
				
				Vehicle vehicleMain = new Vehicle(cityIdForVehicle, passengerCapacity);
				int vehicleRow = 0;
				int vehicleColumn = 0;
				
				for (City city : cityList) {
					if (city.getCityId() == cityIdForVehicle) {
						vehicleRow = city.getLocRow();
						vehicleColumn = city.getLocCol();
						break;
					}
				}
				
				ImageView imageVehicle = vehicleMain.getLogo();
				((BorderPane)((StackPane)(gpCenter.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setTop(imageVehicle);
				((BorderPane)((StackPane)(gpCenter.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setAlignment(imageVehicle, Pos.CENTER_RIGHT);
				
				for (City city : cityList) {
					if (city.getCityId() == cityIdForVehicle) {
						city.getLogo().setOpacity(0.6);
					}
					
				}
				
				vehicleList.add(vehicleMain);
				currentCityId = cityIdForVehicle;
				data = "";
				
			}
			// Finding city properties
			else if(x.startsWith("City,")) {
				
				char c;
				
				int counter = 0;
				String provinceName = "";
				String locationIdStr = "";
				String cityIdStr = "";

				int cityId = 0;
				int cityRow = 0;
				int cityColumn = 0;
				int locationCity = 0;

				for (int i = "City,".length(); i < x.length(); i++) {

					c = x.charAt(i);

					if (c != ',' && counter == 0) {
						provinceName += c;
					} else if (c == ',' && counter == 0) {
						counter = 1;
						continue;
					} else if (c != ',' && counter == 1) {
						locationIdStr += c;
					} else if (c == ',' && counter == 1) {
						counter = 2;
						continue;
					} else if (c != ',' && counter == 2) {
						cityIdStr += c;
					}
				}
				
				//System.out.println(provinceName);

				cityId = Integer.valueOf(cityIdStr);
				//System.out.println("City id is " + cityId);

				locationCity = Integer.valueOf(locationIdStr);
				cityRow = (locationCity - 1) / 10;
				cityColumn = (locationCity - 1) % 10;
				
				City city = new City(provinceName, cityId, locationCity);
				city.setLocRow(cityRow);
				city.setLocCol(cityColumn);
				BorderPane cityInfoPane = new BorderPane();
				Label provinceInfo = new Label(provinceName);
				provinceInfo.setFont(new Font(14));
				provinceInfo.setOpacity(1);
				cityInfoPane.setBottom(provinceInfo);
				cityInfoPane.setAlignment(provinceInfo, Pos.BOTTOM_CENTER);
				
				((StackPane)(gpCenter.getChildren().get((cityRow * 10) + cityColumn))).getChildren().addAll(city.getLogo(), cityInfoPane);
				
				if (cityList.size() != 0) {
					cityInfoPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
						
						private City selectedCity = city;
						@Override
						public void handle(MouseEvent arg0) {
							selectedCityId = this.getCity().getCityId();
							int r =0;
							int c=0;
							String currentCity="";
							for(City city:cityList) {
								if(city.getCityId()==currentCityId) {
									r=city.getLocRow();
									c=city.getLocCol();
									currentCity=city.getCityName();
									
								}
							}
							int distance=(int) Math.ceil((Math.sqrt(Math.pow(Math.abs(r-selectedCity.getLocRow()), 2)+ Math.pow(Math.abs(c - selectedCity.getLocCol()), 2))));
							Label x =new Label(selectedCity+" (City ID : "+selectedCityId+",Distance : "+distance+",Vehicle Capacity : "+vehicleList.get(0).getPasCap());
							vBoxIns.getChildren().add(x);
							for(Passenger passenger:passengerList) {
								if(passenger.getStartCityId()==currentCityId && passenger.getDestCityId()==selectedCityId) {
									Label a =new Label(currentCity+" > "+selectedCity.getCityName());
									vBoxIns.getChildren().add(a);
								}
								if(passenger.getStartCityId()==selectedCityId) {
									String desCityName="";
									for(City city:cityList) {
										if (city.getCityId()==passenger.getDestCityId()) {
										desCityName=city.getCityName();
										}
									}
								
									Label b=new Label(selectedCity.getCityName()+" > "+desCityName);
									vBoxIns.getChildren().add(b);
								}
							}
						}
						public City getCity() {
							return selectedCity;
						}
					});
				}
				
				cityList.add(city);
				
			}else if(x.startsWith("Passenger")) {
				
				char c;
				
				int number_of_passengers = 0;
				String number_of_passengersStr = "";
				int starting_city_id = 0;
				String starting_city_idStr = "";
				int destination_city_id = 0;
				String destination_city_idStr = "";
				
				int counter = 0;
				
				for (int i = "Passenger,".length(); i < x.length(); i++) {
					
					c = x.charAt(i);
					
					if (c != ',' && counter == 0) {
						number_of_passengersStr += c;
					} else if (c == ',' && counter == 0) {
						counter = 1;
						continue;
					} else if (c != ',' && counter == 1) {
						starting_city_idStr += c;
					} else if (c == ',' && counter == 1) {
						counter = 2;
						continue;
					} else if (c != ',' && counter == 2) {
						destination_city_idStr += c;
					}
				}
				
				number_of_passengers = Integer.valueOf(number_of_passengersStr);
				starting_city_id = Integer.valueOf(starting_city_idStr);
				destination_city_id = Integer.valueOf(destination_city_idStr);
				
				Passenger passenger = new Passenger(number_of_passengers,starting_city_id,destination_city_id);
				passengerList.add(passenger);
				
				// Kontrol için yapıldı çalışıyor mu diye
//				for (int i = 0; i < passengerList.size(); i++) {
//					System.out.println(passengerList.get(i).getNumOfPes());
//					System.out.println(passengerList.get(i).getStartCityId());
//					System.out.println(passengerList.get(i).getDestCityId());
//				}
				
				
//				System.out.println(number_of_passengers);
//				System.out.println(starting_city_id);
//				System.out.println(destination_city_id);
			}
			
			
		}
		
		/////
		////
		///
		//
		
		// Ortada fpBottom var
		spBottom.getChildren().addAll(rectBottom, vBoxIns,vboxDriveLbl);
		spBottom.setAlignment(Pos.TOP_CENTER);
		mainBP.setBottom(spBottom);

		// Top Part of the scene
		StackPane spTop = new StackPane();
		
		// Rectangle of top part
		Rectangle rectTop = new Rectangle(scene.getWidth() - 2, scene.getHeight() / 50);
		rectTop.setFill(Color.TRANSPARENT);
		rectTop.setStroke(Color.BLACK);
		rectTop.setStrokeWidth(2);

		// FlowPane to regulate the labels at the top part
		FlowPane fpTop = new FlowPane();

		// Create the labels of the top part
		Label lblLevel = new Label(" Level#" + lvlCounter);
		
		
		fpTop.setAlignment(Pos.CENTER_LEFT);
		fpTop.setHgap(274);

		fpTop.getChildren().addAll(lblLevel, lblScore, lblNextLvl);
		
		// Add the fpTop to spTop
		spTop.getChildren().addAll(rectTop, fpTop);
		spTop.setAlignment(Pos.TOP_CENTER);
		mainBP.setTop(spTop);
		
		return scene;
	}
	
	// Take data from level texts
	public void takeDataFromLevelTexts(String levelPath){
//		File file = new File(levelScript);
//		Scanner outputOfIns = new Scanner(file);
//		
//		
//		
//		// Take the data from the level text documents
//		while (outputOfIns.hasNext()) {
//			String x = outputOfIns.nextLine();
//			vBoxIns.getChildren().add(new Label(x));
//			
//			
//		}
	}

	class NextLevelHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0){

			if (!lblNextLvl.isDisable()) {
				MainClass2.lvlCounter++;
				System.out.println(MainClass2.lvlCounter);
				if (lvlCounter == 2) {
					try {
						stage.setScene(levelPattern("levels\\level2.txt", scene2));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					stage.show();
				} else if (lvlCounter == 3) {
					try {
						stage.setScene(levelPattern("levels\\level3.txt", scene3));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					stage.show();
				} else if (lvlCounter == 4) {
					try {
						stage.setScene(levelPattern("levels\\level4.txt", scene4));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					stage.show();
				} else if (lvlCounter == 5) {
					try {
						stage.setScene(levelPattern("levels\\level5.txt", scene5));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					stage.show();
				}
			}
		}
	}
	
class DriveHandler implements EventHandler<MouseEvent> {
		
		ArrayList<String> indexList = new ArrayList<String>();
		int startRowIndex, startColIndex, finishRowIndex, finishColIndex;
		int indexNum = 1;
		int s_id = 0;
		int f_id = 0;

		public void startingProcess() {
			for (City city : cityList) {
				if (city.getCityId() == currentCityId) {
					s_id = cityList.indexOf(city);
				} else if (city.getCityId() == selectedCityId) {
					f_id = cityList.indexOf(city);
				}
			}
			
			
			startRowIndex = (cityList.get(s_id)).getLocRow();
			startColIndex = (cityList.get(s_id)).getLocCol();
			indexList.add(0, startRowIndex + " " + startColIndex);
			finishRowIndex = (cityList.get(f_id)).getLocRow();
			finishColIndex = (cityList.get(f_id)).getLocCol();
		}

		@Override
		public void handle(MouseEvent arg0) {
			this.startingProcess();
			int[] startArray = new int[] { startRowIndex, startColIndex };

			if (finishColIndex - startColIndex > 0 && finishRowIndex - startRowIndex < 0) {

				int[] controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
				
				startArray[0] = controlArray[0];
				startArray[1] = controlArray[1];
				
				int switch_ = controlArray[2];
				
				while (true) {
					if (startArray[0] == finishRowIndex && startArray[1] == finishColIndex) {
						break;
					} else if (switch_ == 0) {
						controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 1) {
						controlArray = verticalDown(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 2) {
						controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 3) {
						controlArray = verticalUp(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					}

				}

			}

			else if (finishColIndex - startColIndex < 0 && finishRowIndex - startRowIndex < 0) {

				int[] controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
				startArray[0] = controlArray[0];
				startArray[1] = controlArray[1];
				
				int switch_ = controlArray[2];
				
				while (true) {
					if (startArray[0] == finishRowIndex && startArray[1] == finishColIndex) {
						break;
					} else if (switch_ == 0) {
						controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 1) {
						controlArray = verticalDown(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 2) {
						controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 3) {
						controlArray = verticalUp(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					}

				}

			} else if (finishColIndex - startColIndex < 0 && finishRowIndex - startRowIndex > 0) {

				int[] controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
				startArray[0] = controlArray[0];
				startArray[1] = controlArray[1];
				
				int switch_ = controlArray[2];
				
				while (true) {
					if (startArray[0] == finishRowIndex && startArray[1] == finishColIndex) {
						break;
					} else if (switch_ == 0) {
						controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 1) {
						controlArray = verticalDown(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 2) {
						controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 3) {
						controlArray = verticalUp(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					}

				}

			} else if (finishColIndex - startColIndex > 0 && finishRowIndex - startRowIndex > 0) {

				int[] controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
				startArray[0] = controlArray[0];
				startArray[1] = controlArray[1];
				
				int switch_ = controlArray[2];
				
				while (true) {
					if (startArray[0] == finishRowIndex && startArray[1] == finishColIndex) {
						break;
					} else if (switch_ == 0) {
						controlArray = horizontalRight(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 1) {
						controlArray = verticalDown(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 2) {
						controlArray = horizontalLeft(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					} else if (switch_ == 3) {
						controlArray = verticalUp(startArray, finishColIndex, finishRowIndex);
						
						startArray[0] = controlArray[0];
						startArray[1] = controlArray[1];
						
						switch_ = controlArray[2];

					}

				}

			}
			
			scoreCalculator();
			makeAnimation(indexList);
			
			int vehicleRow = 0;
			int vehicleColumn = 0;
			
			for (City city : cityList) {
				if (city.getCityId() == currentCityId) {
					vehicleRow = city.getLocRow();
					vehicleColumn = city.getLocCol();
				}
			}
			
			if (((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().size() >= 3){
				((BorderPane)((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setTop(null);
			}
			
			for (City city : cityList) {
				if (city.getCityId() == selectedCityId) {
					city.getLogo().setOpacity(1);
				}
			}
			
			currentCityId = selectedCityId;
			selectedCityId = 0;
			
			vehicleList.get(0).setCurrentId(currentCityId);
			
			for (City city : cityList) {
				if (city.getCityId() == currentCityId) {
					city.getLogo().setOpacity(0.6);
					vehicleRow = city.getLocRow();
					vehicleColumn = city.getLocCol();
				}
			}
			
			ImageView imageVehicle = vehicleList.get(0).getLogo();
			if (((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().size() >= 3){
				((BorderPane)((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setTop(imageVehicle);
				((BorderPane)((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setAlignment(imageVehicle, Pos.CENTER_RIGHT);
			}
			else {
				BorderPane newBP = new BorderPane();
				((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().add(newBP);
				((BorderPane)((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setTop(imageVehicle);
				((BorderPane)((StackPane)(currentGP.getChildren().get((vehicleRow * 10) + vehicleColumn))).getChildren().get(3)).setAlignment(imageVehicle, Pos.CENTER_RIGHT);
			}
			
			indexList.clear();
			

		}

		public int[] horizontalRight(int[] x, int finishColIndex, int finishRowIndex) {
			int[] indexxy = new int[3];
			int i, a;
			for (i = x[1]; i <= 9; i++) {
				for (a = 0; a < fixedCellList.size(); a++) {
					if ((fixedCellList.get(a)).getCellRow() == x[0] && (fixedCellList.get(a)).getCellCol() == i) {
						indexList.add(x[0] + " " + (i - 1));
						indexxy[0] = x[0];
						indexxy[1] = i - 1;
						indexNum++;
						
						if (finishRowIndex == indexxy[0] && indexxy[0] != 9) {
							indexxy[2] = 1;
						}
						else if (finishRowIndex == indexxy[0] && indexxy[0] == 9) {
							indexxy[2] = 3;
						}
						
						return indexxy;
					}
				}

			}
			
			for (FixedCell fix : fixedCellList) {
				if ((x[1] == fix.getCellCol()) && ((x[0] == fix.getCellRow() + 1) || (x[0] == fix.getCellRow() - 1))) {
					indexxy[0] = x[0];
					indexxy[1] = x[1] + 1;
					indexList.add(x[0] + " " + (x[1] + 1));
					indexNum++;
					
					for (FixedCell fix2 : fixedCellList) {
						if ((indexxy[1] > finishColIndex) && (indexxy[1] == fix2.getCellCol())) {
							indexxy[2] = 0;
							break;
						}
						else {
							if (indexxy[0] < finishRowIndex) {
								indexxy[2] = 1;
							}
							else if (indexxy[0] > finishRowIndex) {
								indexxy[2] = 3;
							}
						}
					}
					return indexxy;
				}
			}
			
			if (x[0] == finishRowIndex) {
				indexxy[0] = finishRowIndex;
				indexxy[1] = finishColIndex;
				indexList.add(finishRowIndex + " " + finishColIndex);
				indexNum++;
				return indexxy;
			}
			
			for (FixedCell fix : fixedCellList) {
				if (x[0] == fix.getCellRow()) {
					indexxy[0] = x[0];
					indexxy[1] = x[1];
					indexList.add(x[0] + " " + x[1]);
					indexNum++;
					
					if (indexxy[0] < finishRowIndex) {
						indexxy[2] = 1;
					}
					else if (indexxy[0] > finishRowIndex) {
						indexxy[2] = 3;
					}
					return indexxy;
				}
			}
			
			indexxy[0] = x[0];
			indexxy[1] = finishColIndex;
			indexList.add(x[0] + " " + finishColIndex);
			indexNum++;
			
			if (indexxy[0] < finishRowIndex) {
				indexxy[2] = 1;
			}
			else if (indexxy[0] > finishRowIndex) {
				indexxy[2] = 3;
			}
			return indexxy;
		}

		public int[] horizontalLeft(int[] x, int finishColIndex, int finishRowIndex) {
			int[] indexxy = new int[3];
			int i, a;
			for (i = x[1]; i >= 0; i--) {
				for (a = 0; a < fixedCellList.size(); a++) {
					if ((fixedCellList.get(a)).getCellRow() == x[0] && (fixedCellList.get(a)).getCellCol() == i) {
						indexList.add(x[0] + " " + (i + 1));
						indexxy[0] = x[0];
						indexxy[1] = i + 1;
						indexNum++;
						
						if (finishRowIndex == indexxy[0] && indexxy[0] != 9) {
							indexxy[2] = 1;
						}
						else if (finishRowIndex == indexxy[0] && indexxy[0] == 9) {
							indexxy[2] = 3;
						}
						
						return indexxy;
					}
				}

			}
			

			for (FixedCell fix : fixedCellList) {
				if ((x[1] == fix.getCellCol()) && ((x[0] == fix.getCellRow() + 1) || (x[0] == fix.getCellRow() - 1))) {
					indexxy[0] = x[0];
					indexxy[1] = x[1] - 1;
					indexList.add(x[0] + " " + (x[1] - 1));
					indexNum++;
					
					for (FixedCell fix2 : fixedCellList) {
						if ((indexxy[1] < finishColIndex) && (indexxy[1] == fix2.getCellCol())) {
							indexxy[2] = 2;
							break;
						}
						else {
							if (indexxy[0] < finishRowIndex) {
								indexxy[2] = 1;
							}
							else if (indexxy[0] > finishRowIndex) {
								indexxy[2] = 3;
							}
						}
					}
					
					return indexxy;
				}
			}
			
			if (x[0] == finishRowIndex) {
				indexxy[0] = finishRowIndex;
				indexxy[1] = finishColIndex;
				indexList.add(finishRowIndex + " " + finishColIndex);
				indexNum++;
				return indexxy;
			}
			
			for (FixedCell fix : fixedCellList) {
				if (x[0] == fix.getCellRow()) {
					indexxy[0] = x[0];
					indexxy[1] = x[1];
					indexList.add(x[0] + " " + x[1]);
					indexNum++;
					
					if (indexxy[0] < finishRowIndex) {
						indexxy[2] = 1;
					}
					else if (indexxy[0] > finishRowIndex) {
						indexxy[2] = 3;
					}
					
					return indexxy;
				}
			}
				
			indexxy[0] = x[0];
			indexxy[1] = finishColIndex;
			indexList.add(x[0] + " " + finishColIndex);
			indexNum++;
			
			if (indexxy[0] < finishRowIndex) {
				indexxy[2] = 1;
			}
			else if (indexxy[0] > finishRowIndex) {
				indexxy[2] = 3;
			}
			
			return indexxy;
		}

		public int[] verticalUp(int[] y, int finishColIndex, int finishRowIndex) {
			int[] indexxy = new int[3];
			int j, b;
			for (j = y[0]; j >= 0; j--) {
				for (b = 0; b < fixedCellList.size(); b++) {
					if ((fixedCellList.get(b)).getCellCol() == y[1] && (fixedCellList.get(b)).getCellRow() == j) {
						indexList.add((j + 1) + " " + y[1]);
						indexxy[0] = j + 1;
						indexxy[1] = y[1];
						indexNum++;
						y[0] = j + 1;
						
						if (finishColIndex == indexxy[1] && indexxy[1] != 9) {
							indexxy[2] = 0;
						}
						else if (finishColIndex == indexxy[1] && indexxy[1] == 9) {
							indexxy[2] = 2;
						}
						
						return indexxy;
					}

				}

			}
			
			for (FixedCell fix : fixedCellList) {
				if ((y[0] == fix.getCellRow()) && ((y[1] == fix.getCellCol() + 1) || (y[1] == fix.getCellCol() - 1))) {
					indexxy[0] = y[0] - 1;
					indexxy[1] = y[1];
					indexList.add((y[0] - 1) + " " + y[1]);
					indexNum++;
					
					for (FixedCell fix2 : fixedCellList) {
						if ((indexxy[0] < finishRowIndex) && (indexxy[0] == fix2.getCellCol())) {
							indexxy[2] = 3;
							break;
						}
						else {
							if (indexxy[1] < finishColIndex) {
								indexxy[2] = 0;
							}
							else if (indexxy[1] > finishColIndex) {
								indexxy[2] = 2;
							}
						}
					}
					
					return indexxy;
				}
			}
			
			if (y[1] == finishColIndex) {
				indexxy[0] = finishRowIndex;
				indexxy[1] = finishColIndex;
				indexList.add(finishRowIndex + " " + finishColIndex);
				indexNum++;
				return indexxy;
			}
			
			for (FixedCell fix : fixedCellList) {
				if (y[1] == fix.getCellCol()) {
					indexxy[0] = y[0];
					indexxy[1] = y[1];
					indexList.add(y[0] + " " + y[1]);
					indexNum++;
					
					if (indexxy[1] < finishColIndex) {
						indexxy[2] = 0;
					}
					else if (indexxy[1] > finishColIndex) {
						indexxy[2] = 2;
					}
					
					return indexxy;
				}
			}
			
			indexxy[0] = finishRowIndex;
			indexxy[1] = y[1];
			indexList.add(finishRowIndex + " " + y[1]);
			indexNum++;
			
			if (indexxy[1] < finishColIndex) {
				indexxy[2] = 0;
			}
			else if (indexxy[1] > finishColIndex) {
				indexxy[2] = 2;
			}
			
			return indexxy;
			
			
			
		}

		public int[] verticalDown(int[] y, int finishColIndex, int finishRowIndex) {
			int[] indexxy = new int[3];
			int j, b;
			for (j = y[0]; j <= 9; j++) {
				for (b = 0; b < fixedCellList.size(); b++) {
					if ((fixedCellList.get(b)).getCellCol() == y[1] && (fixedCellList.get(b)).getCellRow() == j) {
						indexList.add(j - 1 + " " + y[1]);
						indexxy[0] = j - 1;
						indexxy[1] = y[1];
						indexNum++;
						
						if (finishColIndex == indexxy[1] && indexxy[1] != 9) {
							indexxy[2] = 0;
						}
						else if (finishColIndex == indexxy[1] && indexxy[1] == 9) {
							indexxy[2] = 2;
						}
						
						return indexxy;
					}

				}

			}
			
			for (FixedCell fix : fixedCellList) {
				if ((y[0] == fix.getCellRow()) && ((y[1] == fix.getCellCol() + 1) || (y[1] == fix.getCellCol() - 1))) {
					indexxy[0] = y[0] + 1;
					indexxy[1] = y[1];
					indexList.add((y[0] + 1) + " " + y[1]);
					indexNum++;
					
					for (FixedCell fix2 : fixedCellList) {
						if ((indexxy[0] > finishRowIndex) && (indexxy[0] == fix2.getCellCol())) {
							indexxy[2] = 3;
							break;
						}
						else {
							if (indexxy[1] < finishColIndex) {
								indexxy[2] = 0;
							}
							else if (indexxy[1] > finishColIndex) {
								indexxy[2] = 2;
							}
						}
					}
					return indexxy;
				}
			}
			
			if (y[1] == finishColIndex) {
				indexxy[0] = finishRowIndex;
				indexxy[1] = finishColIndex;
				indexList.add(finishRowIndex + " " + finishColIndex);
				indexNum++;
				return indexxy;
			}
			
			for (FixedCell fix : fixedCellList) {
				if (y[1] == fix.getCellCol()) {
					indexxy[0] = y[0];
					indexxy[1] = y[1];
					indexList.add(y[0] + " " + y[1]);
					indexNum++;
					
					if (indexxy[1] < finishColIndex) {
						indexxy[2] = 0;
					}
					else if (indexxy[1] > finishColIndex) {
						indexxy[2] = 2;
					}
					
					return indexxy;
				}
			}
			
			indexxy[0] = finishRowIndex;
			indexxy[1] = y[1];
			indexList.add(finishRowIndex + " " + y[1]);
			indexNum++;
			
			if (indexxy[1] < finishColIndex) {
				indexxy[2] = 0;
			}
			else if (indexxy[1] > finishColIndex) {
				indexxy[2] = 2;
			}
			
			return indexxy;
				
			
		}
		
		public void scoreCalculator () {
			int distance = 0;
			int income = 0;
			int passengerNum = 0;
			double scoreCalc = 0;
			
			for (Passenger passengers : passengerList) {
				if (passengers.getStartCityId() == currentCityId && passengers.getDestCityId() == selectedCityId) {
					passengerNum = passengers.getNumOfPes();
					passengers.setNumOfPes(passengers.getNumOfPes() - vehicleList.get(0).getPasCap());
					
				}
				
			}
			
			
			scoreCalc = (Math.sqrt(Math.pow(Math.abs(startRowIndex-finishRowIndex), 2)+ Math.pow(Math.abs(startRowIndex - finishRowIndex), 2)));
			
			distance += Math.ceil(scoreCalc);
			
			income = (int)(passengerNum * (distance * 0.2));
			score = income - distance;
			
			lblScore.setText("Score: " + score);
		}
		
		
		
		public void makeAnimation(ArrayList<String> pointsInfo) {
			ArrayList<String> allPoints = new ArrayList<String>();
			
			for (int i = 1; i < pointsInfo.size(); i++) {
				if (pointsInfo.get(i).equals(pointsInfo.get(i - 1))) {
					pointsInfo.set(i - 1, "duplicate");
				}
			}
			
			for (int i = 0; i < pointsInfo.size(); i++) {
				if (pointsInfo.get(i).equals("duplicate")) {
					pointsInfo.remove(i);
				}
			}
			
			for (int i = 0; i < pointsInfo.size() - 1; i++) {
				int[] startPoint = new int[2];
				int[] endPoint = new int[2];
				
				startPoint[0] = pointsInfo.get(i).charAt(0) - 48;
				startPoint[1] = pointsInfo.get(i).charAt(2) - 48;
				
				endPoint[0] = pointsInfo.get(i + 1).charAt(0) - 48;
				endPoint[1] = pointsInfo.get(i + 1).charAt(2) - 48;
				
				int firstRow = startPoint[0];
				int firstCol = startPoint[1];
				int secondRow = endPoint[0];
				int secondCol = endPoint[1];
				
				if (firstRow == secondRow) {
					int gap = 0;
					
					if (secondCol > firstCol) {
						gap = secondCol - firstCol;
						
						for (int j = 0; j < gap; j++) {
							allPoints.add(firstRow + " " + (firstCol + j));
						}
					}
					if (firstCol > secondCol) {
						gap = firstCol - secondCol;
						
						for (int j = 0; j < gap; j++) {
							allPoints.add(firstRow + " " + (firstCol - j));
						}
					}
				}
				if (firstCol == secondCol) {
					int gap = 0;
					
					if (secondRow > firstRow) {
						gap = secondRow - firstRow;
						
						for (int j = 0; j < gap; j++) {
							allPoints.add((firstRow + j) + " " + firstCol);
						}
					}
					if (firstRow > secondRow) {
						gap = firstRow - secondRow;
						
						for (int j = 0; j < gap; j++) {
							allPoints.add((firstRow - j) + " " + firstCol);
						}
					}
				}
				
			}
			
			allPoints.add(pointsInfo.get(pointsInfo.size() - 1));
			
			for (int i = 1; i < allPoints.size() - 1; i++) {
				int beforeRow = allPoints.get(i - 1).charAt(0) - 48;
				int beforeCol = allPoints.get(i - 1).charAt(2) - 48;
				int currentRow = allPoints.get(i).charAt(0) - 48;
				int currentCol = allPoints.get(i).charAt(2) - 48;
				int afterRow = allPoints.get(i + 1).charAt(0) - 48;
				int afterCol = allPoints.get(i + 1).charAt(2) - 48;
				
				if (beforeRow == afterRow || beforeCol == afterCol) {
					if (currentRow == afterRow) {
						ImageView path = new ImageView("logos\\horizontal_line.png");
						((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
					}
					if (currentCol == afterCol) {
						ImageView path = new ImageView("logos\\vertical_line.png");
						((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
					}
				}
				else {
					if (beforeCol == currentCol || afterCol == currentCol) {
						if ((afterCol > beforeCol) && (afterCol > currentCol) && (afterRow > beforeRow)) {
							ImageView path = new ImageView("logos\\pos_x_pos_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((afterCol > beforeCol) && (afterCol > currentCol) && (beforeRow > afterRow)) {
							ImageView path = new ImageView("logos\\pos_x_neg_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((beforeCol > afterCol) && (beforeCol > currentCol) && (beforeRow > afterRow)) {
							ImageView path = new ImageView("logos\\pos_x_pos_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((beforeCol > afterCol) && (beforeCol > currentCol) && (afterRow > beforeRow)) {
							ImageView path = new ImageView("logos\\pos_x_neg_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((beforeCol > afterCol) && (currentCol > afterCol) && (beforeRow > afterRow)) {
							ImageView path = new ImageView("logos\\neg_x_neg_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((beforeCol > afterCol) && (currentCol > afterCol) && (afterRow > beforeRow)) {
							ImageView path = new ImageView("logos\\neg_x_pos_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((afterCol > beforeCol) && (currentCol > beforeCol) && (afterRow > beforeRow)) {
							ImageView path = new ImageView("logos\\neg_x_neg_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
						if ((afterCol > beforeCol) && (currentCol > beforeCol) && (beforeRow > afterRow)) {
							ImageView path = new ImageView("logos\\neg_x_pos_y_line.png");
							((StackPane)(currentGP.getChildren().get((currentRow * 10) + currentCol))).getChildren().add(path);
						}
					}
				}
			}
			
			int b = 0;
			
		}

	}
}
