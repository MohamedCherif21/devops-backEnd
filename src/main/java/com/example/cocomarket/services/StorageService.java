package com.example.cocomarket.services;

import com.example.cocomarket.entity.ImageData;
import com.example.cocomarket.entity.Produit;
import com.example.cocomarket.repository.ProduitRepository;
import com.example.cocomarket.repository.StorageRepository;
import com.example.cocomarket.util.Imageutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;



    @Autowired
    private ProduitRepository repoproduit ;

    public String uploadImage(MultipartFile file , Integer id ) throws IOException {

        Produit p = repoproduit.findById(id).orElse(null) ;
        ImageData imageData= ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imagdata(Imageutils.compressImage(file.getBytes())).build();
        if (imageData != null) {
            imageData.setProduit(p) ;
            repository.save(imageData) ;
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null ;
    }



    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        if (dbImageData.isPresent()) {
            return Imageutils.decompressImage(dbImageData.get().getImagdata());
        } else {
            throw new NullPointerException("Image data not found for file name: " + fileName);
        }
    }



    public String upload(MultipartFile file )throws IllegalStateException, IOException {
        file.transferTo(new File("C:\\uploads\\"+ file.getOriginalFilename()));
        return ("file uploaded with succes");
    }



}