package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;
import models.cars_parts.Transmission;

import java.util.Objects;

public class Car {

    @JsonProperty("carId")
    private Integer carId;
    @JsonProperty("carEng")
    private Engine engine;
    @JsonProperty("carBody")
    private CarBody carBody;
    @JsonProperty("carTrs")
    private Transmission transmission;

    public Car() {
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    /**
     * Метод для проверки соответствия условий-
     * используется для фильтрации исходящего списка объявлений в AdsServlet.
     * @param input
     * @return
     */
    public boolean carsAccord(Car input) {
        boolean result = true;
        if (this.engine != null && input.getEngine() != null && result) {
            result = input.getEngine().equals(this.engine);
        } else if (this.engine != null && input.getEngine() == null) {
            result = false;
        }
        if (this.carBody != null && input.getCarBody() != null && result) {
            result = input.getCarBody().equals(this.carBody);
        } else if (this.carBody != null && input.getCarBody() == null) {
            result = false;
        }
        if (this.transmission != null && input.getTransmission() != null && result) {
            result = input.getTransmission().equals(this.transmission);
        } else if (this.transmission != null && input.getTransmission() == null) {
            result = false;
        }
        return result;
    }

    /**
     * Переопределяем таким образом, что бы сравнивались без id.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getEngine(), car.getEngine()) &&
                Objects.equals(getCarBody(), car.getCarBody()) &&
                Objects.equals(getTransmission(), car.getTransmission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(engine, carBody, transmission);
    }
}