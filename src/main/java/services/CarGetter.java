package services;

import models.Car;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.Transmission;

import java.util.List;

public class CarGetter {

    private final StoreDB store = StoreDB.getInstance();

    /**
     * Метод для поиска в БД объекта Car удовлетворяющего условиям,
     * если таковой есть.
     * Т.к. при создании заявки формируется объект Car без id
     * и что бы не плодить в таблице одиннаковые записи.
     * @param car
     * @return
     */
    public Car getCarFromDB(Car car) {
        Car result = null;
        List<Car> bufferCar = null;
        if (car.getEngine() != null) {
            bufferCar = store.sessionFunc(store.getCarDAO().getCarEngine(car));
        } else if (car.getCarBody() != null) {
            bufferCar = store.sessionFunc(store.getCarDAO().getCarBody(car));
        } else if (car.getTransmission() != null) {
            bufferCar = store.sessionFunc(store.getCarDAO().getCarTransmission(car));
        }
        if (bufferCar != null) {
            for (Car c : bufferCar) {
                if (car.equals(c)) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Используя полученные строки создаём объект Car
     * для дальнейших манипуляций.
     * @param engineName
     * @param carbodyName
     * @param transmissionName
     * @return
     */
    public Car fillTheCar(String engineName, String carbodyName, String transmissionName) {
        Car result = new Car();
        int count = 0;
        if (engineName != null && !engineName.equals("empty")) {
            result.setEngine(new Engine(engineName));
            count++;
        }
        if (carbodyName != null && !carbodyName.equals("empty")) {
            result.setCarBody(new CarBody(carbodyName));
            count++;
        }
        if (transmissionName != null && !transmissionName.equals("empty")) {
            result.setTransmission(new Transmission(transmissionName));
            count++;
        }
        if (count == 0) {
            result = null;
        }
        return result;
    }

}