package com.inmobiliaria.opinionesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inmobiliaria.opinionesservice.model.Opinion;
import com.inmobiliaria.opinionesservice.repository.OpinionRepository;
import com.inmobiliaria.opinionesservice.client.UsuarioServiceClient;
import com.inmobiliaria.opinionesservice.client.PropiedadServiceClient;
import java.util.List;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private UsuarioServiceClient usuarioServiceClient;

    @Autowired
    private PropiedadServiceClient propiedadServiceClient;

    public Opinion crearOpinion(Opinion opinion) {
        // Verificar que el usuario existe
        usuarioServiceClient.getUsuario(opinion.getIdUsuario());

        // Verificar que la propiedad existe
        propiedadServiceClient.getPropiedad(opinion.getIdPropiedad());

        return opinionRepository.save(opinion);
    }

    public Opinion obtenerOpinion(Long id) {
        return opinionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opinión no encontrada"));
    }

    public List<Opinion> obtenerTodasLasOpiniones() {
        return opinionRepository.findAll();
    }

    public List<Opinion> obtenerOpinionesPorPropiedad(Long idPropiedad) {
        return opinionRepository.findByIdPropiedad(idPropiedad);
    }

    public List<Opinion> obtenerOpinionesPorUsuario(Long idUsuario) {
        return opinionRepository.findByIdUsuario(idUsuario);
    }

    public Opinion actualizarOpinion(Long id, Opinion opinionActualizada) {
        Opinion opinion = obtenerOpinion(id);

        if (opinionActualizada.getCalificacion() != null) {
            opinion.setCalificacion(opinionActualizada.getCalificacion());
        }

        if (opinionActualizada.getComentario() != null) {
            opinion.setComentario(opinionActualizada.getComentario());
        }

        return opinionRepository.save(opinion);
    }

    public void eliminarOpinion(Long id) {
        opinionRepository.deleteById(id);
    }
}