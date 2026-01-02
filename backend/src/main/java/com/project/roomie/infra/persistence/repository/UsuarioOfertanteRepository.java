package com.project.roomie.infra.persistence.repository;

import com.project.roomie.infra.persistence.entity.UsuarioOfertanteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioOfertanteRepository extends JpaRepository<UsuarioOfertanteJpaEntity, Integer> {

        // METODO PARA QUANDO EXISTIR O ACEITAR MATCH
//    @Query("""
//    SELECT a FROM usuarios u
//    WHERE role = 'OFERTANTE'
//      AND u.id NOT IN (
//          SELECT i.anuncio.id FROM Interesse i // REESCREVER ESSA PARTE
//          WHERE i.interessado.id = :interessadoId
//      )
//""")
//    List<UsuarioOfertanteJpaEntity> buscarCandidatosMatch(Integer id_usuario);
}