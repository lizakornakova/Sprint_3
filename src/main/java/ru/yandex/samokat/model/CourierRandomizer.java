package ru.yandex.samokat.model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandomizer {
    String login = "";
    String password = "";
    String firstName = "";


    public Courier getRandomFields(boolean needLogin, boolean needPassword, boolean needFirstname) {

        if (needLogin) {
            this.login = RandomStringUtils.randomAlphabetic(10);
        }
        if (needPassword) {
            this.password = RandomStringUtils.randomAlphabetic(10);
        }
        if (needFirstname) {
            this.firstName = RandomStringUtils.randomAlphabetic(10);
        }

        return new Courier(login, password, firstName);
    }
}
