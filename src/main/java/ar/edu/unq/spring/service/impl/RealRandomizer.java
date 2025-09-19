package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.service.interfaces.Randomizer;

public class RealRandomizer implements Randomizer {
    @Override
    public Integer getRandom() {
        return (int) (Math.random() * 100);
    }
}
