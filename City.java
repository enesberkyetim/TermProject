import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class City {
	private int cityId;
	private String cityName;
	private int locationCell;
	private int locCol;
	private int locRow;
	private ImageView logo = new ImageView(new Image("logos\\city_logo_" + ((int)(Math.random() * 6)) + ".png")); 
	
	public City(String cityName,int cityId,int locationCell) {
		this.cityName = cityName;
		this.cityId = cityId;
		this.locationCell = locationCell;
		this.locCol = locationCell/10;
		this.locRow = locationCell%10;		
	}
	
	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getLocationCell() {
		return locationCell;
	}

	public void setLocationCell(int locationCell) {
		this.locationCell = locationCell;
	}

	public int getLocCol() {
		return locCol;
	}

	public void setLocCol(int locCol) {
		this.locCol = locCol;
	}

	public int getLocRow() {
		return locRow;
	}

	public void setLocRow(int locRow) {
		this.locRow = locRow;
	}
	public ImageView getLogo() {
		return logo;
	}
}
