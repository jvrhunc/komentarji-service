package si.fri.rso.komentarji.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.fri.rso.komentarji.models.Komentar;
import si.fri.rso.komentarji.repositories.KomentarRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KomentarService {

    @Autowired
    private KomentarRepository komentarRepository;

    public List<Komentar> getKomentarji() {
        return komentarRepository.findAll();
    }

    public Komentar getKomentarById(Integer komentarId) {
        return komentarRepository.findById(komentarId).orElse(null);
    }

    public List<Komentar> getKomentarByReceptId(Integer receptId) {
        return komentarRepository.getByReceptId(receptId);
    }

    public Komentar saveKomentar(Integer receptId, Komentar komentar) {
        komentar.setCreated(LocalDate.now());
        komentar.setReceptId(receptId);

        return komentarRepository.save(komentar);
    }

    public Komentar updateKomentar (Integer komentarId, Komentar komentar) {

        Optional<Komentar> optionalKomentar = komentarRepository.findById(komentarId);

        if(optionalKomentar.isPresent()) {
            Komentar updated = optionalKomentar.get();
            updated.setOcena(komentar.getOcena());
            updated.setKomentar(komentar.getKomentar());
            updated.setCreated(LocalDate.now());
            return komentarRepository.save(updated);
        } else {
            return null;
        }
    }

    public Boolean deleteKomentar(Integer komentarId) {
        komentarRepository.deleteById(komentarId);
        return true;
    }

}
