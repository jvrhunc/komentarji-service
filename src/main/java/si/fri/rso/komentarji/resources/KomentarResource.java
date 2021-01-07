package si.fri.rso.komentarji.resources;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.fri.rso.komentarji.models.Komentar;
import si.fri.rso.komentarji.services.KomentarService;

import java.util.List;

@RestController
@RequestMapping("/v1/komentarji")
@CrossOrigin("*")
public class KomentarResource {

    @Autowired
    private KomentarService komentarService;

    @Timed(
            value = "Komentarji.getAll",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @GetMapping
    public ResponseEntity<Object> getKomentarji() {
        return ResponseEntity.status(HttpStatus.OK).body(komentarService.getKomentarji());
    }

    @Timed(
            value = "Komentarji.getById",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @GetMapping("/{komentarId}")
    public ResponseEntity<Object> getKomentarById(@PathVariable("komentarId") Integer komentarId) {
        if (komentarId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Komentar id is required!");
        }

        Komentar komentar = komentarService.getKomentarById(komentarId);

        if(komentar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(komentar);
    }

    @Timed(
            value = "Komentarji.getByReceptId",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @GetMapping("/recept/{receptId}")
    public ResponseEntity<Object> getKomentarjiByReceptId(@PathVariable("receptId") Integer receptId) {
        if (receptId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Recept id is required!");
        }

        List<Komentar> komentarList = komentarService.getKomentarByReceptId(receptId);

        if(komentarList == null || komentarList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(komentarList);
    }

    @Timed(
            value = "Komentarji.save",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @PostMapping("/add/{receptId}/uporabnik/{uporabnikId}")
    public ResponseEntity<Object> saveKomentar(@PathVariable("receptId")Integer receptId,
                                               @PathVariable("uporabnikId")Integer uporabnikId,
                                               @RequestBody Komentar komentar) {
        if(komentar == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Komentar is required!");
        }

        Komentar komentarAdded = komentarService.saveKomentar(receptId, komentar, uporabnikId);

        if(komentarAdded == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while saving Komentar!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(komentarAdded);
    }

    @Timed(
            value = "Komentarji.update",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @PutMapping("/update/{komentarId}")
    public ResponseEntity<Object> updateKomentar(@PathVariable("komentarId") Integer komentarId,
                                                 @RequestBody Komentar komentar) {

        if(komentar == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Komentar is required!");
        }

        if(komentarId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Komentar id is required!");
        }

        Komentar komentarUpdated = komentarService.updateKomentar(komentarId, komentar);

        if(komentarUpdated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while updating Komentar!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(komentarUpdated);
    }

    @Timed(
            value = "Komentarji.delete",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "v1"}
    )
    @DeleteMapping("/delete/{komentarId}")
    public ResponseEntity<Object> deleteKomentar(@PathVariable("komentarId") Integer komentarId) {

        if (komentarId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Komentar Id is required!");
        }

        Boolean uspesno = komentarService.deleteKomentar(komentarId);

        if(uspesno) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Komentar with id: " + komentarId + "deleted!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while deleting Komentar with Id: " + komentarId);
    }

    @DeleteMapping("/delete/recept/{receptId}")
    public ResponseEntity<Object> deleteByReceptId(@PathVariable("receptId") Integer receptId) {

        if (receptId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Recept Id is required!");
        }

        Boolean uspesno = komentarService.deleteByReceptId(receptId);

        if(uspesno) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(true);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error while deleting Komentars with Recept Id: " + receptId);
    }
}
