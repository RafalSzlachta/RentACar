package pl.sda.rentacar.develop.DBInitializer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.rentacar.domain.car.BodyType;
import pl.sda.rentacar.domain.car.CarCreateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class CarMockData {

    private static Random RANDOM = new Random();

    private final static List<String> MAKES = List.of("Ford", "Fiat", "Hyundai", "Skoda", "Opel");
    private final static List<String> MODELS = List.of("Escort", "Scorpio", "Meriva", "Tipo", "i30");
    private final static List<BigDecimal> PRICES = List.of(
        new BigDecimal(100),
        new BigDecimal(200),
        new BigDecimal(150),
        new BigDecimal(300),
        new BigDecimal(500));

    static CarCreateRequest generateMockCar() {
        return new CarCreateRequest(
            MAKES.get(RANDOM.nextInt(MAKES.size() - 1)),
            MODELS.get(RANDOM.nextInt(MODELS.size() - 1)),
            generateProductionYear(),
            PRICES.get(RANDOM.nextInt(PRICES.size() - 1)),
            BodyType.values()[RANDOM.nextInt(BodyType.values().length)],
            (long) RANDOM.nextInt(2) + 1
        );
    }

    private static Integer generateProductionYear() {
        return 1920 + RANDOM.nextInt(100);
    }
}
