package services;

import models.Advertisement;
import models.Car;
import models.Owner;
import models.Role;
import models.cars_parts.CarBody;
import models.cars_parts.Engine;

import models.cars_parts.Transmission;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class StoreDBTest {

    private final StoreDB testStore = StoreDB.getInstance();

    @Test
    public void testAddGetEngine() {
        Engine engine = new Engine();
        engine.setPartName("Engine_test");
        testStore.sessionFunc(testStore.getEngineDAO().add(engine));
        List<Engine> engines = testStore.sessionFunc(testStore.getEngineDAO().getParts());

        Assert.assertEquals(engines.size(), 1);
        Assert.assertEquals(engines.get(0).getPartName(), "Engine_test");

        engine = testStore.sessionFunc(testStore.getEngineDAO().getPart("Engine_test"));
        Assert.assertEquals(engine.getPartName(), "Engine_test");
    }

    @Test
    public void testAddGetCarBody() {
        CarBody body = new CarBody();
        body.setPartName("Body_test");
        testStore.sessionFunc(testStore.getCarBodyDAO().add(body));
        List<CarBody> bodies = testStore.sessionFunc(testStore.getCarBodyDAO().getParts());

        Assert.assertEquals(bodies.size(), 1);
        Assert.assertEquals(bodies.get(0).getPartName(), "Body_test");

        body = testStore.sessionFunc(testStore.getCarBodyDAO().getPart("Body_test"));
        Assert.assertEquals(body.getPartName(), "Body_test");
    }

    @Test
    public void testAddGetTransmission() {
        Transmission transmission = new Transmission();
        transmission.setPartName("Transmission_test");
        testStore.sessionFunc(testStore.getTransmissionDAO().add(transmission));
        List<Transmission> bodies = testStore.sessionFunc(testStore.getTransmissionDAO().getParts());

        Assert.assertEquals(bodies.size(), 1);
        Assert.assertEquals(bodies.get(0).getPartName(), "Transmission_test");

        transmission = testStore.sessionFunc(testStore.getTransmissionDAO().getPart("Transmission_test"));
        Assert.assertEquals(transmission.getPartName(), "Transmission_test");
    }

    @Test
    public void testAddGetRole() {
        Role role = new Role();
        role.setName("Role_test");
        role.setMainPermission(false);
        role.setAdminPermission(false);
        testStore.sessionFunc(testStore.getRoleDAO().add(role));
        List<Role> roles = testStore.sessionFunc(testStore.getRoleDAO().getParts());

        Assert.assertEquals(roles.size(), 1);
        Assert.assertEquals(roles.get(0).getName(), "Role_test");

        role = testStore.sessionFunc(testStore.getRoleDAO().getPart("Role_test"));
        Assert.assertEquals(role.getName(), "Role_test");
    }

    @Test
    public void testAddGetUser() {
        Owner owner = new Owner();
        owner.setOwnerName("Owner_test");
        testStore.sessionFunc(testStore.getOwnerDAO().add(owner));
        owner = testStore.sessionFunc(testStore.getOwnerDAO().getPart("Owner_test"));
        Assert.assertEquals(owner.getOwnerName(), "Owner_test");
    }

    @Test
    public void testAddGetAdvertisement() {
        LocalDateTime time = LocalDateTime.now();
        Car car = new Car();

        Owner owner = new Owner();
        owner.setOwnerName("Owner_test");
        testStore.sessionFunc(testStore.getOwnerDAO().add(owner));

        Advertisement advertisement = new Advertisement();
        advertisement.setAdCreator(owner);
        advertisement.setAdStatus(true);
        advertisement.setAdShortName("Short_test");
        advertisement.setAdDescription("Desc_test");
        advertisement.setAdTime(time);
        advertisement.setAdCar(car);

        testStore.sessionFunc(testStore.getAdvertisementDAO().add(advertisement));

        List<Advertisement> advertisements = testStore.sessionFunc(testStore.getAdvertisementDAO().getParts());
        advertisement = advertisements.get(0);
        Assert.assertEquals(advertisement.getAdShortName(), "Short_test");
        Assert.assertEquals(advertisement.getAdDescription(), "Desc_test");

        int id = advertisement.getAdId();
        advertisement = testStore.sessionFunc(testStore.getAdvertisementDAO().getPart(id));
        Assert.assertEquals(advertisement.getAdShortName(), "Short_test");
        Assert.assertEquals(advertisement.getAdDescription(), "Desc_test");
    }

}