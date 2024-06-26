package wypisy.example.wypisy.model.Line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wypisy.example.wypisy.model.ManufacturingElement;
import wypisy.example.wypisy.model.ManufacturingProcess;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ProcessLineM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne()
    @JoinColumn(name = "MELEMENT")
    @JsonIgnore
    @OrderBy("id")
    private ManufacturingElement manufacturingElement;

    @ManyToOne()
    @JoinColumn(name = "PROCESS")
    private ManufacturingProcess process;



}
