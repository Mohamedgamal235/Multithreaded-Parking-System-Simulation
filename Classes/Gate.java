import java.util.List;

public class Gate implements Runnable {
    private List<Car> cars ;

    Gate(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public void run(){
        for (Car car : cars)
            car.start();

        try {
            for (Car car : cars)
                car.join();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
