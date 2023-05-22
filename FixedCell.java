import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FixedCell {
	private int cellRow;
	private int cellCol;
	private ImageView logo = new ImageView(new Image("logos\\fixed_cell_logo.png"));
	
	public FixedCell(int cellRow,int cellCol) {
		this.cellRow = cellRow;
		this.cellCol = cellCol;
	}
	
	public int getCellRow() {
		return cellRow;
	}

	public void setCellRow(int cellRow) {
		this.cellRow = cellRow;
	}

	public int getCellCol() {
		return cellCol;
	}

	public void setCellCol(int cellCol) {
		this.cellCol = cellCol;
	}
	
	public ImageView getLogo() {
		return logo;
	}
}
