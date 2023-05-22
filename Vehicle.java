import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vehicle {
	private int currentId;
	private int pasCap;
	private ImageView logo;
	
	public Vehicle(int currentId,int pasCap) {
		this.currentId = currentId;
		this.pasCap = pasCap;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public int getPasCap() {
		return pasCap;
	}

	public void setPasCap(int pasCap) {
		this.pasCap = pasCap;
	}
	public ImageView getLogo() {
		if (pasCap < 6) {
			this.logo = new ImageView(new Image("logos\\car_logo.png"));
		}
		else if (pasCap < 14) {
			this.logo = new ImageView(new Image("logos\\minibus_logo.png"));
		}
		else {
			this.logo = new ImageView(new Image("logos\\bus_logo.png"));
		}
		return logo;
	}
}