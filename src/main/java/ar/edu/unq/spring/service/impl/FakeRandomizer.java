package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.service.interfaces.Randomizer;

public class FakeRandomizer implements Randomizer {
    @Override
    public Integer getRandom() {
        return 50;
    }
}
