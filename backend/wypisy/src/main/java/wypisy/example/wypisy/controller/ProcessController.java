package wypisy.example.wypisy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wypisy.example.wypisy.model.ManufacturingProcess;
import wypisy.example.wypisy.model.Material;
import wypisy.example.wypisy.model.Response;
import wypisy.example.wypisy.services.ProcessService;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/process")
@Slf4j
public class ProcessController {

    private final ProcessService processService;


    @GetMapping("/all")
    public ResponseEntity<Response> getAll(){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Processes",processService.getAll()))
                            .message("Returned all Processes")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("all Processes", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }

    @GetMapping("/")
    public ResponseEntity<Response>getById(@RequestParam Long processId) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Process", processService.getById(processId)))
                            .message("Returned Process")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Process", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PostMapping("/add")
    public ResponseEntity<Response> addProcess(@RequestBody ManufacturingProcess process){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("add Process",processService.create(process) ))
                            .message("addition Process was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("add Process", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @DeleteMapping("/")
    public ResponseEntity<Response>delateMaterail(@RequestParam Long processId){

        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("delete Process",processService.deleteById(processId)))
                            .message("Process was deleted")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("delete Process", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @PutMapping("/")
    public ResponseEntity<Response> putProcess(@RequestBody ManufacturingProcess process){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Change Process",processService.changeById(process)))
                            .message("changed Process was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Change Process", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @PatchMapping("/loc/add")
    public ResponseEntity<Response> addLocaton(@RequestParam Long locationId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("AddNewLocationToProcess",processService.addNewLocation(locationId,processId)))
                            .message("Add Location was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Add Location", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/loc/delete")
    public ResponseEntity<Response> removeLocation(@RequestParam Long locationId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("RemoveNewLocationToProcess",processService.deleteLocationFromProcess(locationId,processId)))
                            .message("Remove Location was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Remove Location", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/category/delete")
    public ResponseEntity<Response> removeCategory(@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("RemoveCategoryProcessFromProcess",processService.deleteCategory(processId)))
                            .message("RemoveCategoryProcessFromProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("RemoveCategoryProcessFromProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/category/add")
    public ResponseEntity<Response> changeAddCategory(@RequestParam Long categoryId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("AddCategoryProcessToProcess",processService.changeAddCategory(categoryId,processId)))
                            .message("AddCategoryProcessToProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("AddCategoryProcessToProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/tool/add")
    public ResponseEntity<Response> addTool(@RequestParam Long toolId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("AddToolToProcess",processService.addTool(toolId,processId)))
                            .message("AddToolToProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("AddToolToProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/tool/delete")
    public ResponseEntity<Response> deleteTool(@RequestParam Long toolId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteToolToProcess",processService.deleteTool(toolId,processId)))
                            .message("deleteToolToProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteToolToProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/program/add")
    public ResponseEntity<Response> addProgram(@RequestParam Long programId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addProgramToProcess",processService.addProgram(programId,processId)))
                            .message("addProgramToProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addProgramToProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/program/delete")
    public ResponseEntity<Response> deleteProgram(@RequestParam Long programId,@RequestParam Long processId){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteProgramToProcess",processService.deleteProgram(programId,processId)))
                            .message("deleteProgramToProcess")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteProgramToProcess", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }







}
