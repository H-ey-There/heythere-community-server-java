package com.heythere.community.post.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface FileCompressionService {
    File uploadFileToS3Bucket(final Long id, final String email, final MultipartFile file) throws IOException;
}
