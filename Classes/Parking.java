import java.util.concurrent.Semaphore;

public class Parking {
    private final Semaphore parkingSpots;
    private int currentCarsInParking;
    private int totalCarsServed;

    public Parking(int totalSpots) {
        this.parkingSpots = new Semaphore(totalSpots, true);
        this.currentCarsInParking = 0;
        this.totalCarsServed = 0;
    }

    public void parkCar(int carId, String gateName, int arrivalTime, int parkingDuration) {
        try {
            System.out.printf("Car %d from %s arrived at time %d%n", carId, gateName, arrivalTime);

            if (parkingSpots.tryAcquire()) {
                synchronized (this) {
                    currentCarsInParking++;
                    totalCarsServed++;
                }
                System.out.printf("Car %d from %s parked. (Parking Status: %d spots occupied)%n",
                        carId, gateName, currentCarsInParking);

                Thread.sleep(parkingDuration * 1000L);
                leaveParking(carId, gateName, parkingDuration);
            } else {
                System.out.printf("Car %d from %s waiting for a spot.%n", carId, gateName);
                parkingSpots.acquire();

                synchronized (this) {
                    currentCarsInParking++;
                    totalCarsServed++;
                }
                System.out.printf("Car %d from %s parked after waiting. (Parking Status: %d spots occupied)%n",
                        carId, gateName, currentCarsInParking);

                Thread.sleep(parkingDuration * 1000L);
                leaveParking(carId, gateName, parkingDuration);
            }
        } catch (InterruptedException e) {
            System.err.printf("Car %d from %s encountered an error: %s%n", carId, gateName, e.getMessage());
        }
    }

    public synchronized void leaveParking(int carId, String gateName, int parkingDuration) {
        currentCarsInParking--;
        parkingSpots.release();
        System.out.printf("Car %d from %s left after %d units of time. (Parking Status: %d spots occupied)%n",
                carId, gateName, parkingDuration, currentCarsInParking);
    }

    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    public int getCurrentCarsInParking() {
        return currentCarsInParking;
    }
}
