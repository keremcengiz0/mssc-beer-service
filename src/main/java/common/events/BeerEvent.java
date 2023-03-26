package common.events;

import com.keremcengiz0.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -5781516597148163111L;
    private BeerDto beerDto;
}
