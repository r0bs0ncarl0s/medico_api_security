package med.voll.api.infra.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CifradorDeSenhaBcrypt implements ICifradorDeSenha{

    public String cifrarSenha(String senha) {

        // Gera um sal aleatório
        String salGerado = BCrypt.gensalt();
        //System.out.println("O sal gerado foi $" + salGerado + "$");

        // Gera a senha hasheada utilizando o sal gerado
        String senhaHasheada = BCrypt.hashpw(senha, salGerado);

        return senhaHasheada;
    }

    public boolean validarSenhaCrifrada(String senhaCifrada, String senha) {
        return BCrypt.checkpw(senhaCifrada, senha);
    }

}
