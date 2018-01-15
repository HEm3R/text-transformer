package cz.chalupa.examples.texttransformer.controller;

import cz.chalupa.examples.texttransformer.model.TransformationRequest;
import cz.chalupa.examples.texttransformer.model.TransformationResponse;
import cz.chalupa.examples.texttransformer.services.TransformationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TransformationController {

    private final TransformationService service;

    // RPC, not REST API
    @PostMapping(value = "/transform")//, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransformationResponse transform(@Valid @RequestBody TransformationRequest request) {
        return new TransformationResponse(service.transform(request.getValue()));
    }
}
