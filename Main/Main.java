import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ---------------- Read From File ---------------------- //

        List<Car> gate1 = new ArrayList<>();
        List<Car> gate2 = new ArrayList<>();
        List<Car> gate3 = new ArrayList<>();

        ParkingLot p = new ParkingLot(4);

        try(BufferedReader r = new BufferedReader(new FileReader("input.txt"))) {
            String line = r.readLine();
            while (line != null){
                ArrayList<String> list  = new ArrayList<>(Arrays.asList(line.split(",")));
                String gate = list.get(0);

                String[] car = list.get(1).trim().split(" ") ;
                String[] arrive = list.get(2).trim().split(" ");
                String[] parking = list.get(3).trim().split(" ");

                int carID = Integer.parseInt(car[1]);
                int arriveTime = Integer.parseInt(arrive[1]);
                int parkingTime = Integer.parseInt(parking[1]);

                if (gate.equals("Gate 1"))
                    gate1.add(new Car(carID , gate , arriveTime , parkingTime , p));
                else if (gate.equals("Gate 2"))
                    gate2.add(new Car(carID , gate , arriveTime , parkingTime , p));
                else
                    gate3.add(new Car(carID , gate , arriveTime , parkingTime , p));


                line = r.readLine();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }


        // ---------------- Run Threads ---------------------- //

        Thread g1 = new Thread(new Gate(gate1));
        Thread g2 = new Thread(new Gate(gate2));
        Thread g3 = new Thread(new Gate(gate3));

        g1.start();
        g2.start();
        g3.start();

        try {
            g1.join();
            g2.join();
            g3.join();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Total Cars Served: " + p.getTotalCarsServed());
        System.out.println("Current Cars in Parking: " + p.getCurrentCarsInParking());
        System.out.println("Details:");
        System.out.println("-Gate 1 served " + gate1.size() + " cars");
        System.out.println("-Gate 2 served " + gate2.size() + " cars");
        System.out.println("-Gate 3 served " + gate2.size() + " cars");

    }
}
