package pl.sda.rentacar.domain.income;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "revenues")
public class Income {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private BigDecimal revenue;
}
