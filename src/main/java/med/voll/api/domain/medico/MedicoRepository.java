package med.voll.api.domain.medico;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	@Query("SELECT m FROM Medico m WHERE m.ativo = 'S'")
	List<DadosListagemMedico> findAllAtivos();

	@Query("SELECT m FROM Medico m WHERE m.ativo = 'N'")
	List<DadosListagemMedico> findAllInativos();
}
