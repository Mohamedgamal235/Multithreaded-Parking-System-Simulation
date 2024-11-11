import java.util.concurrent.Semaphore;

public class ParkingLot {
    private final Semaphore parkingSpots;
    private int currentCarsInParking;
    private int totalCarsServed;

    public ParkingLot(int totalSpots) {
        this.parkingSpots = new Semaphore(totalSpots);
        this.currentCarsInParking = 0;
        this.totalCarsServed = 0;
    }

    public void parkCar(int carId, String gateName, int arrivalTime, int parkingDuration) {
        try {
            System.out.println("Car " + carId + " from " + gateName + " arrived at time " + arrivalTime);

            if (parkingSpots.tryAcquire()) {
                synchronized (this) {
                    currentCarsInParking++;
                    totalCarsServed++;
                }
                System.out.println("Car " + carId + " from " + gateName + " parked. (Parking Status: " + currentCarsInParking + " spots occupied)");

                Thread.sleep(parkingDuration * 1000L);
                leaveParking(carId, gateName, parkingDuration);
            } else {
                System.out.println("Car " + carId + " from " + gateName + " waiting for a spot.");
                long startTime = System.currentTimeMillis();
                parkingSpots.acquire();
                long endTime = System.currentTimeMillis();
                long timeTaken = endTime - startTime;


                synchronized (this) {
                    currentCarsInParking++;
                    totalCarsServed++;
                }


                System.out.println("Car " + carId + " from " + gateName + " parked after waiting for "+ (timeTaken / 1000 )+ " units of time (Parking Status: " + currentCarsInParking + " spots occupied)");

                Thread.sleep(parkingDuration * 1000L);
                leaveParking(carId, gateName, parkingDuration);
            }
        } catch (InterruptedException e) {
            System.err.printf(e.getMessage());
        }
    }

    public synchronized void leaveParking(int carId, String gateName, int parkingDuration) {
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            System.err.printf(e.getMessage());
        }
        currentCarsInParking--;
        parkingSpots.release();
        System.out.println("Car " + carId + " from " + gateName + " left after "+ parkingDuration + " units of time. (Parking Status: " + currentCarsInParking + " spots occupied)");
    }

    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    public int getCurrentCarsInParking() {
        return currentCarsInParking;
    }



}
