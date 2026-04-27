package com.inmobiliaria.opinionesservice.service;

import com.inmobiliaria.opinionesservice.client.IPropiedadClient;
import com.inmobiliaria.opinionesservice.client.IUsuarioClient;
import com.inmobiliaria.opinionesservice.model.Opinion;
import com.inmobiliaria.opinionesservice.repository.IOpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpinionService {

    @Autowired
    private IOpinionRepository opinionRepo;

    @Autowired
    private IUsuarioClient usuarioClient;

    @Autowired
    private IPropiedadClient propiedadClient;

    public Opinion guardarOpinion(Opinion opinion) {
        try {
            // 1. Llamar a usuario-service (Si falla, lanza excepción)
            usuarioClient.obtenerUsuarioPorId(opinion.getIdUsuario());

            // 2. Llamar a propiedad-service (Si falla, lanza excepción)
            propiedadClient.obtenerPropiedadPorId(opinion.getIdPropiedad());

            // 3. Si llega aquí, es porque ambos existen. Guardamos la opinión.
            return opinionRepo.save(opinion);

        } catch (Exception e) {
            // Si Feign recibe un 404 o el servicio está caído, atrapamos el error
            throw new RuntimeException("Validación fallida: El Usuario o la Propiedad no existen en el sistema.");
        }
    }
}