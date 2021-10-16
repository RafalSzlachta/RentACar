package pl.sda.rentacar.develop.DBInitializer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.sda.rentacar.domain.client.ClientCreateRequest;

import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class ClientMockData {

    private static final Random RANDOM = new Random();

    private final static List<String> FIRST_NAMES = List.of("Paweł", "Marek", "Janina", "Marlena", "Krystian");
    private final static List<String> LAST_NAMES = List.of("Leszcz", "Karp", "Sum", "Sandacz", "Flądra");

    private static String generateEmail() {
        return LAST_NAMES.get(3).charAt(RANDOM.nextInt(6)+1) + "@wykop.pl";
    }

    private static String generatePhoneNumber() {
        return Integer.toString(100000000 + RANDOM.nextInt(899999999));
    }

    static ClientCreateRequest generateMockClient() {
        return new ClientCreateRequest(
                    FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()-1)),
                    LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()-1)),
                    generateEmail(),
                    generatePhoneNumber());

    }
}
