package com.std.ec.model.dao;

import com.std.ec.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Integer> {
}
