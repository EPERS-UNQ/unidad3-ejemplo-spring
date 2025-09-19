package ar.edu.unq.spring.configuration;

import ar.edu.unq.spring.service.impl.FakeRandomizer;
import ar.edu.unq.spring.service.impl.RealRandomizer;
import ar.edu.unq.spring.service.interfaces.Randomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class AppConfiguration {

    @Primary
    @Bean(name = "RealRandomizer")
    public Randomizer Randomizer() {
        return new RealRandomizer();
    }

    @Bean(name = "FakeRandomizer")
    public Randomizer FakeRandomizer() {
        return new FakeRandomizer();
    }

    @Bean(name = "RandomizerMagico")
    public Randomizer AnotherFakeRandomizer() {
        return () -> Math.random() * 100 > 50 ? 100 : 0;
    }
}