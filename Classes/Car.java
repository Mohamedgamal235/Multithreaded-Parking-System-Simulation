public class Car extends Thread {
    private final int  carId ;
    private final String gateName ;
    private final int arrivalTime ;
    private final int parkingDuration ;
    private ParkingLot parkingLot ;

    public Car(int carId, String gateName, int arrivalTime, int parkingDuration , ParkingLot parkingLot) {
        this.carId = carId;
        this.gateName = gateName;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
        this.parkingLot = parkingLot;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(arrivalTime * 1000L);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        parkingLot.parkCar(carId , gateName , arrivalTime , parkingDuration);

    }
}
