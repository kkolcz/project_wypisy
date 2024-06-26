package wypisy.example.wypisy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wypisy.example.wypisy.model.Line.ProcessLineE;
import wypisy.example.wypisy.model.Line.ProcessLineM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ManufacturingProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;

    @ManyToOne()
    @JoinColumn(name = "CATEGORY")
    @OrderBy("id")
    private ProcessCategory category;


    @NotNull
    @Column(name = "time" ,columnDefinition = "DECIMAL(7,2)")
    @Digits(integer=9, fraction=2)
    private BigDecimal time;


    @ManyToMany
    @JoinTable(name="PROCESS_TOOL",
            joinColumns={@JoinColumn(name="PROCESS_ID")},
            inverseJoinColumns={@JoinColumn(name="TOOL_ID")}
    )
    @OrderBy("id")
    private List<Tool> toolList=new ArrayList<>();

    @ManyToMany
    @JoinTable(name="PROCESS_PROGRAM",
            joinColumns={@JoinColumn(name="PROCESS_ID")},
            inverseJoinColumns={@JoinColumn(name="PROGRAM_ID")}
    )
    @OrderBy("id")
    private List<MachineProgram> machinePrograms=new ArrayList<>();







    @OneToMany(mappedBy = "process")
    @JsonIgnore
    @OrderBy("id")
    private List<ProcessLineM> processLineMS =new ArrayList<>();

    @OneToMany(mappedBy = "process")
    @JsonIgnore
    @OrderBy("id")
    private List<ProcessLineE> processLineES =new ArrayList<>();


    @ManyToOne()
    @JoinColumn(name = "LOCATION")
    private Location location;








}
