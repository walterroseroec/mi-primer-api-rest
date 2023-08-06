package com.std.ec.controller;

import com.std.ec.model.dto.ClienteDto;
import com.std.ec.model.entity.Cliente;
import com.std.ec.model.payload.MensajeResponse;
import com.std.ec.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("clientes")
    public ResponseEntity<?> showAll() {
        List<Cliente> getList = clienteService.listAlll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mnesaje("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mnesaje("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }


    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mnesaje("Guardado correctamente")
                    .object(ClienteDto.builder()
                            .idCliente(clienteSave.getIdCliente())
                            .nombre(clienteSave.getNombre() )
                            .apellido(clienteSave.getApellido())
                            .correo(clienteSave.getCorreo())
                            .fechaRegistro(clienteSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mnesaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        Cliente clienteUpdate = null;
        try {
            if (clienteService.existsById(id)) {
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mnesaje("Guardado correctamente")
                        .object(ClienteDto.builder()
                                .idCliente(clienteUpdate.getIdCliente())
                                .nombre(clienteUpdate.getNombre())
                                .apellido(clienteUpdate.getApellido())
                                .correo(clienteUpdate.getCorreo())
                                .fechaRegistro(clienteUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mnesaje("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mnesaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mnesaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mnesaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mnesaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

}
