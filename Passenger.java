public class Passenger {
	private int numOfPes;
	private int startCityId;
	private int destCityId;
	
	public Passenger(int numOfPes,int startCityId,int destCityId){
		this.numOfPes = numOfPes;
		this.startCityId = startCityId;
		this.destCityId = destCityId;
	}

	public int getNumOfPes() {
		return numOfPes;
	}

	public void setNumOfPes(int numOfPes) {
		this.numOfPes = numOfPes;
	}

	public int getStartCityId() {
		return startCityId;
	}

	public void setStartCityId(int startCityId) {
		this.startCityId = startCityId;
	}

	public int getDestCityId() {
		return destCityId;
	}

	public void setDestCityId(int destCityId) {
		this.destCityId = destCityId;
	}
}
