package ru.yandex.samokat.model;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderRandomizer {
    public Order getRandomOrderData(){

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String metroStation = faker.address().state();
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = faker.number().randomDigit();
        String deliveryDate =  new SimpleDateFormat("d.MM.yyyy").format(Calendar.getInstance().getTime());
        String comment = faker.name().title();
        String[] color = null;

        return  new Order(firstName,lastName,address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
    }

    public Order getOrderWithoutColor(){

        Order order = getRandomOrderData();
        order.setColor(null);

        return order;
    }
    public Order getOrderWithColor(String[] color){

        Order order = getRandomOrderData();
        order.setColor(color);

        return order;
    }
}
