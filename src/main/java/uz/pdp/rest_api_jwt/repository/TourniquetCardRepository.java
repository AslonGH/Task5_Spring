package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.TourniquetCard;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TourniquetCardRepository extends JpaRepository<TourniquetCard, UUID> {

    Optional<TourniquetCard> findByEmployeeId(UUID uuid);
}
