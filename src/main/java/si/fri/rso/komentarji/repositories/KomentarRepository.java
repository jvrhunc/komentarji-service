package si.fri.rso.komentarji.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import si.fri.rso.komentarji.models.Komentar;

import java.util.List;

public interface KomentarRepository extends JpaRepository<Komentar, Integer> {
    List<Komentar> getByUporabnikId(Integer uporabnikId);
    List<Komentar> getByReceptId(Integer receptId);
}
