package si.fri.rso.komentarji.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import si.fri.rso.komentarji.models.Komentar;
import si.fri.rso.komentarji.repositories.KomentarRepository;

public class Query implements GraphQLQueryResolver {

    private KomentarRepository komentarRepository;

    public Query(KomentarRepository komentarRepository) {
        this.komentarRepository = komentarRepository;
    }

    public Iterable<Komentar> findAllKomentars() {
        return komentarRepository.findAll();
    }

    public Komentar findReceptById(Integer id) {
        return komentarRepository.findById(id).orElse(null);
    }

    public Iterable<Komentar> findReceptByReceptId(Integer id) {
        return komentarRepository.getByReceptId(id);
    }
}
