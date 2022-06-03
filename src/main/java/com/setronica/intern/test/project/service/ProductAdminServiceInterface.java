package com.setronica.intern.test.project.service;

import com.setronica.intern.test.project.dto.request.AdminRequestProductDTO;
import com.setronica.intern.test.project.dto.response.AdminResponseProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductAdminServiceInterface {
    List<AdminResponseProductDTO> findAll();

    void create(AdminRequestProductDTO productDTO);

    AdminResponseProductDTO findByID(long id);

    void update(long id, AdminRequestProductDTO productDTO);

    void delete(long id);
}
