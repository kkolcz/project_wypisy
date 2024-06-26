package wypisy.example.wypisy.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wypisy.example.wypisy.model.DTO.WypisLineDTO;
import wypisy.example.wypisy.model.Response;
import wypisy.example.wypisy.model.Wypis;
import wypisy.example.wypisy.services.WypisService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/wypis")
@Slf4j
public class WypisController {

private final WypisService wypisService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAll(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("allWypis",wypisService.getAll()))
                            .message("Returned all WYPIS")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("allWypis", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @GetMapping("/")
    public ResponseEntity<Response>getById(@RequestParam Long wypisId) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("WYPIS",wypisService.getById(wypisId)))
                            .message("Returned WYPIS")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("WYPIS", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PostMapping("/add")
    public ResponseEntity<Response>addWypis(@RequestBody List<WypisLineDTO> wypisLineDTOs) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("CREATE NEW WYPIS",wypisService.create(wypisLineDTOs)))
                            .message("CREATE NEW WYPIS")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("CREATE NEW WYPIS", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @DeleteMapping("/line/")
    public ResponseEntity<Response>delateProductFromWypis(@RequestParam Long lineId) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteProductFromWypis",wypisService.delateProductFromWypis(lineId)))
                            .message("deleteProductFromWypis")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteProductFromWypis", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/line/")
    public ResponseEntity<Response>changeProductFromWypis(@RequestParam Long lineId, @RequestParam BigDecimal unit) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("changeProductFromWypis",wypisService.changeWypisLineUnit(lineId,unit)))
                            .message("changeProductFromWypis")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("changeProductFromWypis", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @GetMapping("/PDF/")
    public  void changeProductFromWypis(HttpServletResponse response ,@RequestParam Long wypisId) {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.wypisService.export(response,wypisId);

    }

    @PostMapping("/addProducts")
    public ResponseEntity<Response>addProductsToWypis(@RequestParam Long wypisId,@RequestBody List<WypisLineDTO> wypisLineDTOs ) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addProductsToWypis",wypisService.addProducts(wypisId,wypisLineDTOs)))
                            .message("addProductsToWypis")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addProductsToWypis", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }






}
