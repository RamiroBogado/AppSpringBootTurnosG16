package com.unla.tp_oo2_g16.dtos;

import org.springframework.web.multipart.MultipartFile;

public record EmailFileDTO(String[] toUser, String subject, String message, MultipartFile file) {

}
