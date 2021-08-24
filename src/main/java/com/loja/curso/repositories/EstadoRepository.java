package com.loja.curso.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loja.curso.domain.Estado;


@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
