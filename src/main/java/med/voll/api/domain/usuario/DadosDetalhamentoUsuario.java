package med.voll.api.domain.usuario;

public record DadosDetalhamentoUsuario(Long id, String login, String senha) {

    public DadosDetalhamentoUsuario(Usuario usu) {
        this(usu.getId(), usu.getLogin(), usu.getSenha());
    }
}