package services;

import models.Car;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.Transmission;

/**
 * Содержит несколько вспомогательных методов
 * для создания Car или GeneralPart`ов.
 */
public class CarGetter {

    /**
     * Используя полученные строки создаём объект Car
     * для дальнейших манипуляций.
     *
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